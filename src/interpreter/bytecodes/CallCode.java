package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * CALL <funcname>
 * Transfer control to the indicated function.
 * 
 * @author Alberto Mancini
 */
public class CallCode extends BranchingByteCode {

	public void init(Vector<String> args) {
		label = args.get(0);
	}

	public void execute(VirtualMachine vm) {
		vm.pushAddr(vm.getPc());
		vm.setPc(branchAddress);
	}
	
	public String toString() {
		return "CALL " + label;
	}

}
