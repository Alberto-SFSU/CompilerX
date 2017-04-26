package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ReturnCode;
import interpreter.debugger.DebugVM;

public class DebugReturnCode extends ReturnCode {
	
	public void execute(VirtualMachine vm) {
		super.execute(vm);
		((DebugVM) vm).popEnvStack(); //DEBUG
	}
}
