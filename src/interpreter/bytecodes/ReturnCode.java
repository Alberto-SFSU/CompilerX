package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * RETURN <funcname>
 * Return from the current function; <funcname> is used as a comment to indicate the current function 
 * 
 * RETURN  is generated for intrinsic functions 
 * @author Alberto Mancini
 */
public class ReturnCode extends ByteCode {
	private String label = "";

	public void init(Vector<String> args) {
		if(args.size() > 0) {
			label = args.get(0);
		}
	}

	public void execute(VirtualMachine vm) {
		vm.popFrame();
		vm.setPc(vm.popAddr());
	}

	public String toString() {
		return "RETURN " + label;
	}
}
