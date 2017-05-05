package interpreter.debugger;

import interpreter.Program;
import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
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
	private boolean interupted;
	private int breakLine; //current line stopped
	
	private boolean stepOut; //step out command flag
	private int stepSize;
	
	private boolean trace; //trace flag
	
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
		interupted = false;
		breakLine = -1;
		stepOut = false;
		trace = false;
		initSource(filename);
	}
	
	public void executeProgram() {
		interupted = false;
		while(getRunStatus()) {
			int lineNum = getLineNumber();
			
			if((!getBreakptFlag(getLineNumber()-1) || breakLine == lineNum) && !interupted) {
				ByteCode code = getProgram().getCode(getPc());
				code.execute(this);
				setPc(getPc()+1);
				if(breakLine != lineNum) { breakLine = -1; }
			}
			else {
				interupted = true;
				breakLine = getLineNumber();
				return;
			}
		}
		
		//Halt reached - reset dvm
		System.out.println("*****Debugging complete*****");
		setPc(0);
		breakLine = -1;
		environmentStack.clear();
		environmentStack.push(new FunctionEnvironmentRecord());
		run();
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
	
	public int getEnvStackSize() {
		return environmentStack.size();
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
		Set<String> vars = environmentStack.peek().getVariables();
		String [] tmp = new String [vars.size()];
		int i = 0;
		for(String str: vars) {
			tmp[i] = str + ": " + getVal(peekFrame()+i);
			i++;
		}
		return tmp;
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
	
	public boolean isInterupted() {
		return interupted;
	}
	
	public void setInterupt(boolean flag) {
		interupted = flag;
	}
	
	public boolean stepOut() {
		return stepOut;
	}
	
	public void setStepOut(boolean flag) {
		stepOut = flag;
	}
	
	public void setStepSize(int i) {
		stepSize = i;
	}
	
	public int getStepSize() {
		return stepSize;
	}
	
	public void setTrace(boolean flag) {
		trace = flag;
	}
	
	public boolean getTrace() {
		return trace;
	}
	
	public boolean isBreakable(int lineNumber) {
		String line = entries.get(lineNumber-1).sourceLine;
		if(line.contains("{") || //blocks
		   line.contains("int") || //decl
		   line.contains("boolean") ||
		   line.contains("if") || //if
		   line.contains("while") || //while
		   line.contains("return") || //return
		   line.contains("=")) { //assign
			return true;
		}
		return false;
	}
	
	public String[] callStack() {
		Iterator<FunctionEnvironmentRecord> iter = environmentStack.iterator();
		String [] callStack = new String[environmentStack.size()];
		
		int i = 0;
		while(iter.hasNext()) {
			FunctionEnvironmentRecord rec = iter.next();
			callStack[i] = rec.getName() + " : " + rec.getLineNumber();
			i++;
		}
		return callStack;
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
