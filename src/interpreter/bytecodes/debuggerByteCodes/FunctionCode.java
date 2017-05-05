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
		DebugVM dvm = (DebugVM) vm;
		dvm.setEnvRecFunction(name, start, end);
		
		//Function Trace
		if(dvm.getTrace()) {
			String tracer = "";
			for(int i = 0; i < dvm.getEnvStackSize(); i++) {
				tracer += " ";
			}
			ByteCode bc = dvm.getProgram().getCode(dvm.getPc() + 1);
            if (bc instanceof FormalCode) { //has args
                tracer += name + "(" + dvm.peekRunStack() + ")";
            } 
            else {
                tracer += name + "()"; //no args
            }
            System.out.println(tracer);
		}
	}

}
