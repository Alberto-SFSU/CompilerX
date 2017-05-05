package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.LitCode;
import interpreter.debugger.DebugVM;

public class DebugLitCode extends LitCode {

	public void execute(VirtualMachine vm) {
		super.execute(vm);
		if(!getId().equals("")) {
			((DebugVM) vm).peekEnvStack().setVarVal(getId(), getValue());
		}//DEBUG
	}
}
