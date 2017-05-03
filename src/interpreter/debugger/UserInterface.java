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
	private void debugUI() {
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
				case "cb": clearBreakpoint(input); break;//clear breakpoints
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
		System.out.println("*****Command List*****");
		System.out.println("------------------------------------------------------------------------");
		
		System.out.format("%-15s%-28s%-1s%n","b [args]","Set Breakpoint(s)", "b 5");
		System.out.format("%-15s%-28s%-1s%n%n", "","","Breakpoint set at line 5.");
		System.out.format("%-15s%-28s%-1s%n", "","","b 2 6");
		System.out.format("%-15s%-28s%-1s%n%n", "","","Breakpoint set on lines 2 and 6.");
		
		System.out.format("%-15s%-28s%-1s%n","cb [args]","Clear Breakpoint(s)", "cb");
		System.out.format("%-15s%-28s%-1s%n%n", "","", "Clear all breakpoints.");
		System.out.format("%-15s%-28s%-1s%n", "","","cb 2");
		System.out.format("%-15s%-28s%-1s%n%n", "","","Breakpoint on line 2 cleared.");
		
		System.out.format("%-15s%-28s%n%n","f","Display current function");
		
		System.out.format("%-15s%-28s%n%n","c","Continue execution");
		
		System.out.format("%-15s%-28s%n%n","v","Display local variables");
		
		System.out.format("%-15s%-28s%n%n","src","Display source code");
		
		System.out.format("%-15s%-28s%n%n","q","Quit debugging and exit");
	}
	
	/**
	 * Prints the source code of the current function.
	 */
	private void displayFunction() {
		int start = dvm.getFunctionStart();
		int end = dvm.getFunctionEnd();
		if(start < 1) { displaySourceFile(); return; }; //main
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
		if(args.length > 1) {
			String bps = "";
			for(int i = 1; i < args.length; i++) {
				int arg = Integer.parseInt(args[i]);
				dvm.setBreakptFlag(arg-1, true);
				dvm.addBp(arg);
				bps += " " + args[i];
			}
			System.out.println("Breakpoint(s) set on line(s)" + bps + ".");
		}
		else {
			System.out.println("No breakpoints set. Try b [args].");
		}
	}
	
	/**
	 * Clears breakpoints on the specified line numbers. 
	 * If no line numbers are specified, all breakpoints are cleared.
	 * @param args command with arguments consisting of line numbers
	 */
	private void clearBreakpoint(String [] args) {
		if(args.length > 1) {
			String bps = "";
			for(int i = 1; i < args.length; i++) {
				int arg = Integer.parseInt(args[i]);
				dvm.setBreakptFlag(arg-1, false);
				dvm.rmBp(arg);
				bps += " " + args[i];
			}
			System.out.println("Breakpoint(s) on lines " + bps + " cleared.");
		}
		else {
			dvm.clearBps();
			System.out.println("All breakpoints cleared.");
		}
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
		System.out.println("Debug session terminated.");
		dvm.terminate();
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
			if(i+1 == dvm.getLineNumber()) { System.out.print("  " + lineMarker); } //???
			System.out.println();
		}
	}
}
