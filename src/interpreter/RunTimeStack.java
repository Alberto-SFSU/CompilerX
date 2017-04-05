package interpreter;

import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

/**
 * Maintains and manages the runtime stack and frame pointers.
 * 
 * @author Alberto Mancini
 */
public class RunTimeStack {
	private Stack<Integer> framePointers;
	private Vector<Integer> runStack;
	
	public RunTimeStack() {
		framePointers = new Stack<>();
		runStack = new Vector<>();
	}

	/**
	 * Dumps the state of the runtime stack.
	 * Used for debugging when DUMP flag is raised by the VM
	 */
	public void dump() {
		//TODO debug
		int frameIndex = -1;
		Iterator<Integer> iter = framePointers.iterator();
		if(!framePointers.isEmpty()) {
			frameIndex = iter.next();
		}
		
		System.out.print("[");
		for(int i = 0; i < runStack.size(); i++) {
			System.out.print(runStack.get(i));
			if(i+1 == frameIndex) {
                System.out.print("] [");
                if(iter.hasNext()){
                    frameIndex= iter.next();
                }
            }
			else if(i+1 != runStack.size()){
				System.out.print(",");
			}
		}
		System.out.println("]");
		
	}
	
	public int peek() {
		return runStack.get(runStack.size()-1);
	}
	
	public int pop() {
		return runStack.remove(runStack.size()-1);
	}
	
	public int push(int i) {
		runStack.add(i);
		return i;
	}
	
	public void newFrameAt(int offset) {
		int top = runStack.size();
	    framePointers.push(top - offset);

	}
	
	public void popFrame() {
		int val = runStack.get(runStack.size()-1); //copy return value
		for(int i = runStack.size()-1; i >= framePointers.peek(); i--) {
			runStack.remove(i); //remove values of top frame from runtime stack
		}
		framePointers.pop();
		runStack.add(val);
	}
	
	public int store(int offset) {
		int val = pop();
		if(framePointers.isEmpty()) {
			runStack.set(offset, val);
		}
		else {
			runStack.set(framePointers.peek() + offset, val);
		}
		return val;
	}
	
	public int load(int offset) {
		int val;
		if(framePointers.isEmpty()) {
			val = runStack.get(offset);
		}
		else {
			val = runStack.get(framePointers.peek() + offset);
		}
		
        runStack.add(val);
        return val;
	}
	
	public Integer push(Integer i) {
		runStack.add(i);
		return i;
	}
}
