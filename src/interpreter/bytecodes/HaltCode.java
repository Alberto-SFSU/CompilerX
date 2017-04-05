package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * Halt execution.
 * 
 * @author Alberto Mancini
 */
public class HaltCode extends ByteCode {

	public void init(Vector<String> args) {}

	public void execute(VirtualMachine vm) {
		vm.terminate();
	}
	
	public String toString() {
		return "HALT";
	}
	
}
