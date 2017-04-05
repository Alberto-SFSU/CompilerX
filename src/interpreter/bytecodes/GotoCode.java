package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * GOTO <label>
 * Branch to <label>
 * 
 * @author Alberto Mancini
 */
public class GotoCode extends BranchingByteCode {

	public void init(Vector<String> args) {
		label = args.get(0);
		
	}

	public void execute(VirtualMachine vm) {
		vm.setPc(branchAddress);
	}
	
	public String toString() {
		return "GOTO " + label;
	}
}
