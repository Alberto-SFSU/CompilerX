package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * bop <binary op>
 * 
 * Pop the top 2 levels of the stack and perform the indicated operation:
 *  + - / * ==  !=  <=  > >= < | & 
 *  
 * @author Alberto Mancini
 */
public class BopCode extends ByteCode {
	private String op;

	public void init(Vector<String> args) {
		op = args.get(0);
	}

	public void execute(VirtualMachine vm) {
		int x = vm.popRunStack();
		int y = vm.popRunStack();
		switch(op) {
			case "+":
				vm.pushRunStack(y+x);
				break;
			case "-":
				vm.pushRunStack(y-x);
				break;
			case "/":
				vm.pushRunStack(y/x);
				break;
			case "*":
				vm.pushRunStack(y*x);
				break;
			case "==":
				if(y==x) {
					vm.pushRunStack(1);
				}
				else {
					vm.pushRunStack(0);
				}
				break;
			case "!=":
				if(y!=x) {
					vm.pushRunStack(1);
				}
				else {
					vm.pushRunStack(0);
				}
				break;
			case "<":
				if(y<x) {
					vm.pushRunStack(1);
				}
				else {
					vm.pushRunStack(0);
				}
				break;
			case "<=":
				if(y<=x) {
					vm.pushRunStack(1);
				}
				else {
					vm.pushRunStack(0);
				}
				break;
			case ">":
				if(y>x) {
					vm.pushRunStack(1);
				}
				else {
					vm.pushRunStack(0);
				}
				break;
			case ">=":
				if(y>=x) {
					vm.pushRunStack(1);
				}
				else {
					vm.pushRunStack(0);
				}
				break;
			case "|":
				if((y==1) || (x==1)) {
					vm.pushRunStack(1);
				}
				else {
					vm.pushRunStack(0);
				}
				break;
			case "&":
				if((y==1) && (x==1)) {
					vm.pushRunStack(1);
				}
				else {
					vm.pushRunStack(0);
				}
				break;
		}
	}
	
	public String toString() {
		return "BOB " + op;
	}

}
