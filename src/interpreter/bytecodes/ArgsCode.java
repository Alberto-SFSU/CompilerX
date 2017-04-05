package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * ARGS n
 * n = #args
 * 
 * This instruction is immediately followed by the CALL instruction; the function has n args
 * so ARGS n instructs the interpreter to set up a new frame n down from the top.
 * 
 * @author Alberto Mancini
 */
public class ArgsCode extends ByteCode {
	private String numArgs;

	public void init(Vector<String> args) {
		numArgs = args.get(0);
	}

	public void execute(VirtualMachine vm) {
		vm.newFrame(Integer.parseInt(numArgs));
	}

	public String toString() {
		return "ARGS " + numArgs;
	}
}
