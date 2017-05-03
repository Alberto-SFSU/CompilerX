package interpreter.debugger;

import interpreter.Program;
import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

/**
 * The Debug VM executes each byte code loaded into the Program in debug mode.
 * @author Alberto Mancini
 */
public class DebugVM extends VirtualMachine {

	private Stack<FunctionEnvironmentRecord> environmentStack;
	private Vector<Entry> entries;
	private Vector<Integer> bpTracker;
	
	/**
	 * Constructs a VM for debugging.
	 * @param program Program instance with loaded bytecodes
	 * @param source the name of the source file
	 * @throws IOException 
	 */
	public DebugVM(Program program, String filename) throws IOException {
		super(program);
		environmentStack = new Stack<>();
		environmentStack.push(new FunctionEnvironmentRecord()); //main
		entries = new Vector<>();
		bpTracker = new Vector<>();
		initSource(filename);
	}
	
	public void executeProgram() {
		
		while(getRunStatus()) {
			if(!getBreakptFlag(getLineNumber()-1)) {
				ByteCode code = getProgram().getCode(getPc());
				code.execute(this);
				setPc(getPc()+1);
			}
			else {
				return;
			}
		}
		
		//Halt reached - reset dvm
		System.out.println("*****Debugging complete*****\n");
		setPc(0);
		environmentStack.clear();
		environmentStack.push(new FunctionEnvironmentRecord());
	}
	
	public FunctionEnvironmentRecord peekEnvStack() {
		return environmentStack.peek();
	}
	
	public void pushEnvStack() {
		environmentStack.push(new FunctionEnvironmentRecord());
	}
	
	public FunctionEnvironmentRecord popEnvStack() {
		return environmentStack.pop();
	}
	
	public void doEnvRecPop(int pop) {
		environmentStack.peek().doPop(pop);
	}
	
	public void setEnvRecFunction(String name, int start, int end) {
		environmentStack.peek().setFunctionInfo(name, start, end);
	}
	
	public int getLineNumber() {
		return environmentStack.peek().getLineNumber();
	}
	
	public int getFunctionStart() {
		return environmentStack.peek().getStart();
	}
	
	public int getFunctionEnd() {
		return environmentStack.peek().getEnd();
	}
	
	public String [] getVariables() {
		String [] vars = (String[]) environmentStack.peek().getVariables().toArray();
		for(int i = 0; i < vars.length; i++) {
			vars[i] += " = " + environmentStack.peek().getVarVal(vars[i]);
		}
		return vars;
	}
	
	public String getSourceLine(int i) {
		return entries.get(i).sourceLine;
	}
	
	public boolean getBreakptFlag(int i) {
		if(i < 0) { return false; } //intrinsic fn call
		return entries.get(i).isBreakptSet;
	}
	
	public void setBreakptFlag(int i, boolean flag) {
		entries.get(i).isBreakptSet = flag;
	}
	
	public void addBp(int i) {
		bpTracker.add(i);
	}
	
	public void rmBp(Integer i) {
		bpTracker.remove(i);
	}
	
	public void clearBps() {
		for(int i : bpTracker) {
			entries.get(i-1).isBreakptSet = false;
		}
		bpTracker.clear();
	}
	
	public int getNumBps() {
		return bpTracker.size();
	}
	
	public int getEntryCount() {
		return entries.size();
	}
	
	/**
	 * Reads the specified source file to initialize the Vector of entries with source lines.
	 * Breakpoint flags for the lines are initialized to false.
	 * @param filename the source file
	 * @throws IOException
	 */
	private void initSource(String filename) throws IOException {
		BufferedReader sourceReader = new BufferedReader(new FileReader(filename));
		String line = sourceReader.readLine();
		while(line != null) {
			entries.add(new Entry(line, false));
			line = sourceReader.readLine();
		}
		sourceReader.close();
	}
	
	/**
	 * Component consisting of a program source line and breakpoint flag.
	 */
	private static class Entry {
		private String sourceLine;
		private boolean isBreakptSet;
		
		public Entry(String sourceLine, boolean isBreakptSet) {
			this.sourceLine = sourceLine;
			this.isBreakptSet = isBreakptSet;
		}
	}
}
