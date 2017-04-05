package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * LIT n - load the literal value n
 * LIT 0 i - this form of the Lit was generated to load 0 on the stack in order to initialize the variable 
 * i to 0 and reserve space on the runtime stack for i 
 * 
 * @author Alberto Mancini
 */
public class LitCode extends ByteCode {
	private String val = "";
	private String id = "";

	public void init(Vector<String> args) {
		val = args.get(0);
		if(args.size() > 1) {
			id = args.get(1);
		}
	}

	public void execute(VirtualMachine vm) {
		vm.pushRunStack(Integer.parseInt(val));
	}

	public String toString() {
		return "LIT " + val + " " + id;
	}
	
}
