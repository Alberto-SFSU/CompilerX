package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.bytecodes.ReturnCode;
import interpreter.debugger.DebugVM;
import interpreter.debugger.FunctionEnvironmentRecord;

public class DebugReturnCode extends ReturnCode {
	
	public void execute(VirtualMachine vm) {
		super.execute(vm);
		DebugVM dvm = (DebugVM) vm;
		FunctionEnvironmentRecord rec = dvm.popEnvStack();
		
		//Step Out
		if(dvm.stepOut()) {
			if(dvm.getEnvStackSize() < dvm.getStepSize()) {
				dvm.setStepOut(false);
				dvm.setInterupt(true);
				ByteCode code = dvm.getProgram().getCode(dvm.getPc() + 1);
				if(code instanceof FunctionCode) {
					code.execute(dvm);
				}
			}
		}
		
		//Function Trace
		if(dvm.getTrace()) {
			String tracer = "";
			for(int i = 0; i < dvm.getEnvStackSize(); i++) {
				tracer += " ";
			}
            tracer += "exit " + rec.getName() + ": " + dvm.peekRunStack();
            System.out.println(tracer);
		}
	}
}
