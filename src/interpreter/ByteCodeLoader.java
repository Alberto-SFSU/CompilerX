package interpreter;

import java.io.*;
import interpreter.bytecodes.ByteCode;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class handles the loading of code from a source file.
 * It loads Bytecode objects into a Program instance.
 * 
 * @author Alberto Mancini
 */
public class ByteCodeLoader {
	private BufferedReader source;

	public ByteCodeLoader(String filename) throws IOException {
		source = new BufferedReader(new FileReader(filename));
	}
	
	/**
	 * Reads the code file given to the ByteCodeLoader and instantiates the codes and resolves any 
	 * symbolic addresses found. The codes are then stored in a Program for execution by the VM.
	 * @return the program object containing instances of all its bytecodes
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Program loadCodes() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Program program = new Program();
		String nextLine = source.readLine();
		
		while(nextLine != null) {
			StringTokenizer codeTokens = new StringTokenizer(nextLine);
			String codeClass =  CodeTable.get(codeTokens.nextToken());
			
			Vector<String> args = new Vector<>();
			while(codeTokens.hasMoreTokens()) {
				args.add(codeTokens.nextToken());
			}
			
			program.addCode(genCode(codeClass, args));
			nextLine = source.readLine();
		}
		
		program.resolveAddress();
		return program;
	}
	
	private ByteCode genCode(String codeClass, Vector<String> args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ByteCode bytecode = (ByteCode)Class.forName("interpreter.bytecodes." + codeClass).newInstance();
		bytecode.init(args);
		return bytecode;
	}
}
