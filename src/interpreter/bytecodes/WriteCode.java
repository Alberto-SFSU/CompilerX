package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * WRITE - Write the value on top of the stack to output; leave the value on top of the stack 
 * 
 * @author Alberto Mancini
 */
public class WriteCode extends ByteCode {

	public void init(Vector<String> args) {}

	public void execute(VirtualMachine vm) {
		System.out.println(vm.peekRunStack());
	}
	
	public String toString() {
		return "WRITE";
	}

}
