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
	private boolean isPaused;
	
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
		isPaused = false;
		initSource(filename);
	}
	
	@Override
	public void executeProgram() {
		while(getRunStatus()) {
			if(isPaused) {
				terminate();
			}
			else {
				ByteCode code = getProgram().getCode(getPc());
				code.execute(this);
				setPc(getPc()+1);
			}
		}
	}
	
	public FunctionEnvironmentRecord peekEnvStack() {
		return environmentStack.peek();
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
		for(String key : vars) {
			key += " = " + environmentStack.peek().getVarVal(key);
		}
		return vars;
	}
	
	public String getSourceLine(int i) {
		return entries.get(i).sourceLine;
	}
	
	public boolean getBreakptFlag(int i) {
		return entries.get(i).isBreakptSet;
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
