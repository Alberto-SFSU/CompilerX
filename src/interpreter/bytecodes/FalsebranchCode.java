package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * FALSEBRANCH <label>
 * Pop the top of the stack, if false then branch to <label> else continue.
 * 
 * @author Alberto Mancini
 */
public class FalsebranchCode extends BranchingByteCode {

	public void init(Vector<String> args) {
		label = args.get(0);
	}

	public void execute(VirtualMachine vm) {
		if(vm.popRunStack() == 0) {
			vm.setPc(branchAddress);
		}
	}

	public String toString() {
		return "FALSEBRANCH " + label;
	}
}
