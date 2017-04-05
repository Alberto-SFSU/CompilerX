package interpreter;

import java.util.Stack;
import interpreter.bytecodes.ByteCode;
import interpreter.bytecodes.DumpCode;

/**
 * The virtual machine executes each byte code that is loaded into the program. 
 * It keeps track of the current position in the program; also, it holds a reference to the runtime stack.
 * 
 * @author Alberto Mancini
 */
public class VirtualMachine {
	private RunTimeStack runStack;
	private int pc;
	private Stack<Integer> returnAddrs;
	private boolean isRunning;
	private Program program;
	private boolean dump;
	
	public VirtualMachine(Program program) {
		this.program = program;
	}
	
	public void executeProgram() {
		 pc = 0;         
		 runStack = new RunTimeStack();         
		 returnAddrs = new Stack<>();  
		 dump = false; //default
		 isRunning = true;         
		 while (isRunning) {             
			 ByteCode code = program.getCode(pc);             
			 code.execute(this);
			 if(dump && !(code instanceof DumpCode)) { //debug print format
				 System.out.format("%-20s", code.toString() + " "); 
				 runStack.dump();
			 }
			 pc++;
		 }
	}
	
	public void setPc(int i) {
		pc = i;
	}
	
	public int  getPc() {
		return pc;
	}
	
	public int pushAddr(int returnAddress) {
		return returnAddrs.push(returnAddress);
	}
	
	public Integer pushAddr(Integer returnAddress) {
		return returnAddrs.push(returnAddress);
	}
	
	public int popAddr() {
		return returnAddrs.pop();
	}
	
	public int pushRunStack(int i) {
		return runStack.push(i);
	}
	
	public int popRunStack() {
		return runStack.pop();
	}
	
	public int peekRunStack() {
		return runStack.peek();
	}
	
	public void newFrame(int offset) {
		runStack.newFrameAt(offset);
	}
	
	public void popFrame() {
		runStack.popFrame();
	}
	
	public int store(int offset) {
		return runStack.store(offset);
	}
	
	public int load(int offset) {
		return runStack.load(offset);
	}
	
	public void setDump(boolean state) {
		dump = state;
	}
	
	public void terminate() {
		isRunning = false;
	}

}
