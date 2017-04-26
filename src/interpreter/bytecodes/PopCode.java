package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * POP n
 * Pop the top n levels of the runtime stack
 * @author Alberto Mancini
 */
public class PopCode extends ByteCode {
	private String val;

	public void init(Vector<String> args) {
		val = args.get(0);
	}

	public void execute(VirtualMachine vm) {
		int n = Integer.parseInt(val);
		for(int i = 0; i < n; i++) {
			vm.popRunStack();
		}
	}
	
	public String toString() {
		return "POP " + val;
	}
	
	public int getPopVal() {
		return Integer.parseInt(val);
	}

}
