package interpreter.bytecodes;

import java.util.Vector;

/**
 * ByteCode abstraction for concrete bytecodes
 * @author Alberto Mancini
 */
public abstract class ByteCode {
	public ByteCode() {}
	
	public abstract void init(Vector<String> args);
	public abstract void exec();
}
