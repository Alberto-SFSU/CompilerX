package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * LOAD n <id>
 * Push the value in the slot which is offset n from the start of the frame onto the top of the stack;
 * <id> is used as a comment, it’s the variable name from which the data is loaded 
 * 
 * @author Alberto Mancini
 */
public class LoadCode extends ByteCode {
	private String val;
	private String id;

	public void init(Vector<String> args) {
		val = args.get(0);
		id = args.get(1);
	}

	public void execute(VirtualMachine vm) {
		vm.load(Integer.parseInt(val));
	}
	
	public String toString() {
		return "LOAD " + val + " " + id;
	}

}
