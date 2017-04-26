package interpreter.bytecodes.debuggerByteCodes;

import java.util.Vector;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVM;

public class FormalCode extends ByteCode {
	private String var;
	private int val;

	public void init(Vector<String> args) {
		var = args.get(0);
		val = Integer.parseInt(args.get(1));
	}

	public void execute(VirtualMachine vm) {
		((DebugVM) vm).peekEnvStack().setVarVal(var, val);	
	}

}
