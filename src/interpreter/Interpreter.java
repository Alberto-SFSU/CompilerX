package interpreter;

import java.io.*;

/**
 * <pre>
 *     Interpreter class runs the interpreter:
 *     1. Perform all initializations
 *     2. Load the bytecodes from file
 *     3. Run the virtual machine
 * </pre>
 */
public class Interpreter {

	ByteCodeLoader bcl;

	public Interpreter(String codeFile) {
		try {
			CodeTable.init();
			bcl = new ByteCodeLoader(codeFile);
		} catch (IOException e) {
			System.out.println("**** " + e);
		}
	}

	void run() {
		Program program = null;
		try {
			program = bcl.loadCodes();
		} catch (Exception e) { 
			System.out.println("**** " + e);
		}
		
		VirtualMachine vm = new VirtualMachine(program);
		vm.executeProgram();
	}

	public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
			System.exit(1);
		}
		
		//check for debug flag
		if(args[0] == "-d") {
			
		}
		
		(new Interpreter(args[0])).run();
	}
}