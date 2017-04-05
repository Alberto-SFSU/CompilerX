package interpreter.bytecodes;

import java.util.Scanner;
import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * READ
 * Read an integer; prompt the user for input; put the value just read on top of the stack 
 * 
 * @author Alberto Mancini
 */
public class ReadCode extends ByteCode {
	private Scanner in = new Scanner(System.in);

	public void init(Vector<String> args) {}

	public void execute(VirtualMachine vm) {
		boolean done = false;
		int input = -1;
		while(!done) {
			System.out.print("Enter an integer value: ");
			try {
				input = Integer.parseInt(in.nextLine());
				done = true;
			} catch(NumberFormatException e) {
				System.out.println("**** ERROR: INVALID INPUT");
			}
		}
		vm.pushRunStack(input);
	}
	
	public String toString() {
		return "READ";
	}

}
