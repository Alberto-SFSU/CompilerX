package interpreter.bytecodes;

/**
 * ByteCode abstraction for handing branching codes
 * 
 * @author Alberto Mancini
 */
public abstract class BranchingByteCode extends ByteCode {
	protected int branchAddress;
	protected String label;
	
	public void jumpToBranch(int addr) {
		branchAddress = addr;
	}
	
	public String getLabel() {
		return label;
	}
}
