package interpreter.debugger;

import java.util.Iterator;
import java.util.Set;

public class FunctionEnvironmentRecord {
	
	private String name;
	private int start;
	private int end;
	private int lineNum;
	private Table table;
	
	public FunctionEnvironmentRecord() {
		table = new Table();
	}

	public void beginScope() {
		table.beginScope();
	}
	
	public void setFunctionInfo(String name, int start, int end) {
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setCurrentLineNumber(int lineNum) {
		this.lineNum = lineNum;
	}
	
	public int getLineNumber() {
		return lineNum;
	}
	
	public void setVarVal(String var, int val) {
		table.put(var, val);
	}
	
	public Set<String> getVariables() {
		return table.keys();
	}
	
	public Object getVarVal(String key) {
		return table.get(key);
	}
	
	public void doPop(int n) {
		table.doPop(n);
	}
	
	public void dump() {
		if(name == null) {
			System.out.println("(<>,-,-,-,-)"); //empty environment record
		}
		else {
			System.out.print("(<");
			if(!table.keys().isEmpty()) {
				Iterator<String> iter = table.keys().iterator(); //environment record iterator
				while(iter.hasNext()) {
					String var = iter.next().toString();
					System.out.print(var + "/" + table.get(var));
					if(iter.hasNext()) {
						System.out.print(",");
					}
				}
			}
			
			//***style for remaining dump printout***
			String printout = ">,";
			if(name == null) printout+="-,"; else printout+=name+",";
			if(start == 0) printout+="-,"; else printout+=start+",";
			if(end == 0) printout+="-,"; else printout+=end+",";
			if(lineNum == 0) printout+="-)"; else printout+=lineNum+")";
			System.out.println(printout);

		}
	}
	
	/**
	 * Unit test for FunctionEnvironmentRecord symbol table mechanism.
	 * @param args
	 */
	public static void main(String args[]) {
		FunctionEnvironmentRecord f = new FunctionEnvironmentRecord();
		System.out.format("%-15s", "BS");
		f.beginScope(); //BS
		f.dump();
		
		System.out.format("%-15s", "Function g 1 20");
		f.setFunctionInfo("g", 1, 20); //Function g 1 20
		f.dump(); 
		
		System.out.format("%-15s", "Line 5");
		f.setCurrentLineNumber(5); //Line 5
		f.dump();
		
		System.out.format("%-15s", "Enter a 4");
		f.setVarVal("a", 4); //Enter a 4
		f.dump();
		
		System.out.format("%-15s", "Enter b 2");
		f.setVarVal("b", 2); //Enter b 2
		f.dump();
		
		System.out.format("%-15s", "Enter c 7");
		f.setVarVal("c", 7); //Enter c 7
		f.dump();
		
		System.out.format("%-15s", "Enter a 1");
		f.setVarVal("a", 1); //Enter a 1
		f.dump();
		
		System.out.format("%-15s", "Pop 2");
		f.doPop(2); //Pop 2
		f.dump();
		
		System.out.format("%-15s", "Pop 1");
		f.doPop(1); //Pop 1
		f.dump();
	}
}


/** <pre>
 *  Binder objects group 3 fields
 *  1. a value
 *  2. the next link in the chain of symbols in the current scope
 *  3. the next link of a previous Binder for the same identifier
 *     in a previous scope
 *  </pre>
*/
class Binder {
  private Object value;
  private String prevtop;   // prior symbol in same scope
  private Binder tail;      // prior binder for same symbol
                            // restore this when closing scope
  Binder(Object v, String p, Binder t) {
	value=v; prevtop=p; tail=t;
  }

  Object getValue() { return value; }
  String getPrevtop() { return prevtop; }
  Binder getTail() { return tail; }
}


/** <pre>
 * MODIFIED FROM CONSTRAINER -
 * The Table class is similar to java.util.Dictionary, except that
 * each key must be a Symbol and there is a scope mechanism.
 *
 * Consider the following sequence of events for table t:
 * t.put(Symbol("a"),5)
 * t.beginScope()
 * t.put(Symbol("b"),7)
 * t.put(Symbol("a"),9)
 * 
 * symbols will have the key/value pairs for Symbols "a" and "b" as:
 * 
 * Symbol("a") ->
 *     Binder(9, Symbol("b") , Binder(5, null, null) )
 * (the second field has a reference to the prior Symbol added in this
 * scope; the third field refers to the Binder for the Symbol("a")
 * included in the prior scope)
 * Binder has 2 linked lists - the second field contains list of symbols
 * added to the current scope; the third field contains the list of
 * Binders for the Symbols with the same string id - in this case, "a"
 * 
 * Symbol("b") ->
 *     Binder(7, null, null)
 * (the second field is null since there are no other symbols to link
 * in this scope; the third field is null since there is no Symbol("b")
 * in prior scopes)
 * 
 * top has a reference to Symbol("a") which was the last symbol added
 * to current scope
 * 
 * Note: What happens if a symbol is defined twice in the same scope??
 * </pre>
*/
class Table {

  private java.util.HashMap<String,Binder> symbols = new java.util.HashMap<String,Binder>();
  private String top;    // reference to last symbol added to
                         // current scope; this essentially is the
                         // start of a linked list of symbols in scope
  private Binder marks;  // scope mark; essentially we have a stack of
                         // marks - push for new scope; pop when closing
                         // scope

  public Table(){}


 /**
  * Gets the object associated with the specified symbol in the Table.
  */
  public Object get(String key) {
	Binder e = symbols.get(key);
	return e.getValue();
  }

 /**
  * Puts the specified value into the Table, bound to the specified Symbol.<br>
  * Maintain the list of symbols in the current scope (top);<br>
  * Add to list of symbols in prior scope with the same string identifier
  */
  public void put(String key, Object value) {
	symbols.put(key, new Binder(value, top, symbols.get(key)));
	top = key;
  }

 /**
  * Remembers the current state of the Table; push new mark on mark stack
  */
  public void beginScope() {
    marks = new Binder(null, top, marks);
    top = null;
  }

 /**
  * Restores the table to what it was at the most recent beginScope
  *	that has not already been ended.
  */
  public void endScope() {
	while (top!=null) {
	   Binder e = symbols.get(top);
	   if (e.getTail()!=null) symbols.put(top,e.getTail());
	   else symbols.remove(top);
	   top = e.getPrevtop();
	}
	top = marks.getPrevtop();
	marks = marks.getTail();
  }
  
  /**
   * Removes the last n sym/val pairs entered
   * @param n the number of sym/val pairs to remove
   */
  public void doPop(int n) {
	  for(int i = 0; i < n; i++) {
		   Binder e = symbols.get(top);
		   if (e.getTail()!=null) symbols.put(top,e.getTail());
		   else symbols.remove(top);
		   top = e.getPrevtop();
		}
  }

  /**
   * @return a set of the Table's symbols.
   */
  public java.util.Set<String> keys() {return symbols.keySet();}
}
