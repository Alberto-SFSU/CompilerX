package interpreter.debugger;

import java.util.Scanner;

/**
 * This class handles user interaction when in Debug Mode.
 * @author Alberto Mancini
 */
public class UserInterface {
	
	private DebugVM dvm;
	
	public UserInterface(DebugVM dvm) {
		this.dvm = dvm;
		debugUI();
	}
	
	/**
	 * Debugger User Interface
	 */
	public void debugUI() {
		Scanner sc = new Scanner(System.in);
		boolean done = false;
		String [] input;
		displaySourceFile();
		
		while(!done) {
			System.out.println("\nType ? for help");
			System.out.print(">");
		
			input = sc.nextLine().split("\\s");
			switch(input[0]) {
				case "?": displayHelp(); break; //help
				case "b": setBreakpoint(input); break; //set breakpoints
				case "clr": clearBreakpoint(input); //clear breakpoints
				case "f": displayFunction(); break; //current function
				case "c": continueExecution(); break; //continue
				case "v": displayVariables(); break;//variables
				case "src": displaySourceFile(); break;
				case "q": done = true; quit(); break; //quit
				default: System.out.println("\n*****invalid command*****");
			}
		}
		
		sc.close();
	}

	/**
	 * Prints the Help Menu to the console.
	 * <pre>
	 * command     description     example
	 * </pre>
	 */
	private void displayHelp() {
		System.out.println("Command List");
		System.out.println("------------");
		System.out.format("%-10s%-20s%-s","b [args]","Set Breakpoint(s)", "b 3 5\nBreakpoints set at lines 3 and 5");
	}
	
	/**
	 * Prints the source code of the current function.
	 */
	private void displayFunction() {
		int start = dvm.getFunctionStart();
		int end = dvm.getFunctionEnd();
		for(int i = start-1; i < end; i++) {
			System.out.format("%4s%-1s%n", i+". ", dvm.getSourceLine(i));
		}
	}
	
	/**
	 * Prints the local variables and their values to the console.
	 * <pre>
	 * var = value
	 * </pre>
	 */
	private void displayVariables() {
		String [] vars = dvm.getVariables();
		for(String var : vars) {
			System.out.println(var);
		}
	}
	
	/**
	 * Sets breakpoints on the specified line numbers.
	 * @param args command with arguments consisting of line numbers
	 */
	private void setBreakpoint(String [] args) {
		
	}
	
	/**
	 * Clears breakpoints on the specified line numbers. 
	 * If no line numbers are specified, all breakpoints are cleared.
	 * @param args command with arguments consisting of line numbers
	 */
	private void clearBreakpoint(String [] args) {
		
	}
	
	/**
	 * Continue execution through next breakpoint, if any.
	 */
	private void continueExecution() {
		dvm.executeProgram();
		displaySourceFile();
	}
	
	/**
	 * Halts execution and exits debugger.
	 */
	private void quit() {
		
	}
	
	/**
	 * Prints the source code to the console.
	 * Displays current line and breakpoints, if any.
	 */
	private void displaySourceFile() {
		String bp;
		String lineMarker = "<=====";
		for(int i = 0; i < dvm.getEntryCount(); i++) {
			bp = "";
			if(dvm.getBreakptFlag(i)) { bp = "*"; }
			System.out.format("%-2s%4s%-1s", bp, i+1 +". ", dvm.getSourceLine(i));
			if(i+1 == dvm.getLineNumber()) { System.out.print("  " + lineMarker); }
			System.out.println();
		}
	}
}
