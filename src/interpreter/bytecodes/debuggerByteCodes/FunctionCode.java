package interpreter.bytecodes.debuggerByteCodes;

import java.util.Vector;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVM;

public class FunctionCode extends ByteCode{
	String name;
	private int start;
	private int end;

	public void init(Vector<String> args) {
		name = args.get(0);
		start = Integer.parseInt(args.get(1));
		end = Integer.parseInt(args.get(2));
		
	}

	public void execute(VirtualMachine vm) {
		((DebugVM) vm).setEnvRecFunction(name, start, end); //DEBUG
	}

}
