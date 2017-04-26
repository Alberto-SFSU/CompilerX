package interpreter.bytecodes.debuggerByteCodes;

import java.util.Vector;
import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVM;

public class LineCode extends ByteCode {
	private int lineNum;
	
	public void init(Vector<String> args) {
		lineNum = Integer.parseInt(args.get(0));
	}

	public void execute(VirtualMachine vm) {
		((DebugVM) vm).peekEnvStack().setCurrentLineNumber(lineNum);
	}
}