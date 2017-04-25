package interpreter;

import java.util.HashMap;

/**
 * Holds and initializes the HashTable used by ByteCodeLoader to create instances of bytecode classes
 * 
 * @author Alberto Mancini
 */
public class CodeTable {
	private static HashMap<String, String> codes = new HashMap<>(); //<bytecode string, class name>
	
	public static void init() {
		codes.put("HALT", "HaltCode");
		codes.put("POP", "PopCode");
		codes.put("FALSEBRANCH", "FalsebranchCode");
		codes.put("GOTO", "GotoCode");
		codes.put("STORE", "StoreCode");
		codes.put("LOAD", "LoadCode");
		codes.put("LIT", "LitCode");
		codes.put("ARGS", "ArgsCode");
		codes.put("CALL", "CallCode");
		codes.put("RETURN", "ReturnCode");
		codes.put("BOP", "BopCode");
		codes.put("READ", "ReadCode");
		codes.put("WRITE", "WriteCode");
		codes.put("LABEL", "LabelCode");
		codes.put("DUMP", "DumpCode");
	}
	
	public static void debug() {
		codes.put("LINE", "debuggerByteCodes.LineCode");
		codes.put("FUNCTION", "debuggerByteCodes.FunctionCode");
		codes.put("FORMAL", "debuggerByteCodes.FormalCode");
		codes.put("CALL", "debuggerByteCodes.DebugCallCode");
		codes.put("LIT", "debuggerByteCodes.DebugLitCode");
		codes.put("POP", "debuggerByteCodes.DebugPopCode");
	}
	
	public static String get(String code) {
		return codes.get(code);
	}

}
