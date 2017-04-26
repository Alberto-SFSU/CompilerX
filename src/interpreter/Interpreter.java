package interpreter;

import java.io.*;

import interpreter.debugger.DebugVM;
import interpreter.debugger.UserInterface;

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
	
	void runDebug(String sourceFile) throws IOException {
		CodeTable.debug();
		Program program = null;
		try {
			program = bcl.loadCodes();
		} catch (Exception e) { 
			System.out.println("**** " + e);
		}
		
		DebugVM dvm = new DebugVM(program, sourceFile);
		UserInterface ui = new UserInterface(dvm);
		//dvm.executeProgram();
	}

	public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
			System.exit(1);
		}
		
		//check for debug flag
		if(args[0].equals("-d")) {
			String codeFile = args[1]+".x.cod";
			String sourceFile = args[1]+".x";
			Interpreter interp = new Interpreter(codeFile);
			try{
				interp.runDebug(sourceFile);
			} catch(IOException e) {
				System.out.println("**** " + e);
			}
		}
		else { (new Interpreter(args[0])).run(); }
	}
}