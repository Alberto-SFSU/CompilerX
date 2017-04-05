package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * Enter debug mode and print the state of the runtime for each bytecode when DUMP flag is raised.
 * 
 * @author Alberto Mancini
 */
public class DumpCode extends ByteCode {
	private String dumpState;
	
	public void init(Vector<String> args) {
		dumpState = args.get(0);
	}

	public void execute(VirtualMachine vm) {
		if(dumpState.equals("ON")) {
			vm.setDump(true);
		}
		else {
			vm.setDump(false);
		}
	}
	
	public String toString() {
		return "DUMP " + dumpState;
	}

}
