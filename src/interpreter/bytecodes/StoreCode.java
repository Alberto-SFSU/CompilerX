package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * STORE n <id> - pop the top of the stack; store value into the offset n from the start of the frame; <id> is used as a comment, 
 * it’s the variable name where the data is stored 
 * @author Alberto Mancini
 *
 */
public class StoreCode extends ByteCode {
	private String val;
	private String id;

	public void init(Vector<String> args) {
		val = args.get(0);
		id = args.get(1);
	}

	public void execute(VirtualMachine vm) {
		vm.store(Integer.parseInt(val));
	}
	
	public String toString() {
		return "STORE " + val + " " + id;
	}
}
