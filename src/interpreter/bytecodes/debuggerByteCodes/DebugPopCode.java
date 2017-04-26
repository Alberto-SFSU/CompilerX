package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.PopCode;
import interpreter.debugger.DebugVM;

public class DebugPopCode extends PopCode{
	
	public void execute(VirtualMachine vm) {
		super.execute(vm);
		((DebugVM) vm).doEnvRecPop(getPopVal()); //DEBUG
	}
	
}
