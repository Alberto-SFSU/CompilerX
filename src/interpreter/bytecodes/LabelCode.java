package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * LABEL <label>
 * Target for branches.
 * @author Alberto Mancini
 */
public class LabelCode extends ByteCode {
	private String label;

	public void init(Vector<String> args) {
		label = args.get(0);
	}

	public void execute(VirtualMachine vm) {}

	public String getLabel() {
		return label;
	}
	
	public String toString() {
		return "LABEL " + label;
	}
}
