package cutcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import graphics.FunctionBlock;
import graphics.GraphicalBlock;
import graphics.Sequence;

public class Executor {
	private int lineLoc = 2;
	private HashMap<Integer, GraphicalBlock> allBlocks;
	public static final String ERROR = "An error occured, please try again later.";

	/**
	 * 
	 * Converts graphical block to string code
	 * 
	 * @param sequences; the List of sequences of graphical blocks
	 * @return a String of the Java code for the sequence of GraphicalBlocks
	 * @apiNote O(infinity)
	 * @author Arjun Khanna
	 */
	public String getCode(List<FunctionBlock> sequences) {
		System.err.println("hi");
		allBlocks = new HashMap<Integer, GraphicalBlock>();
		String output = "";
		for(FunctionBlock block : sequences) {
			putInHashMap(block);
			output += block.getLogicalBlock().toString();
		}
		return "public class Program {" + System.lineSeparator() + output + "}";
	}

	/**
	 * 
	 * @param block - the block to be put in the HashMap
	 */
	private void putInHashMap(GraphicalBlock block) {
		allBlocks.put(lineLoc, block);
		lineLoc++;
		if (block instanceof graphics.FunctionBlock) {
			for (GraphicalBlock b : ((graphics.FunctionBlock) block).getCommands()) {
				putInHashMap(b);
				lineLoc++;
			}
			lineLoc++;
		} else if (block instanceof graphics.IfBlock) {
			for (GraphicalBlock b : ((graphics.IfBlock) block).commands) {
				putInHashMap(b);
				lineLoc++;
			}
			lineLoc++;
		} else if (block instanceof graphics.WhileBlock) {
			for (GraphicalBlock b : ((graphics.WhileBlock) block).commands) {
				putInHashMap(b);
				lineLoc++;
			}
			lineLoc++;
		}
	}

	/**
	 * @param filename the .java file to be run
	 * @return the console output from running the file
	 * @apiNote O(?)
	 * @author Arjun Khanna
	 * @throws BlockCodeCompilerErrorException 
	 */
	public String run(String filename) throws BlockCodeCompilerErrorException { // TODO: find efficiency of this method
		String output = "";
		Process p;
		Process p2;
		try {
			p = Runtime.getRuntime().exec("javac Program.java"); // this will compile the program
			p.waitFor();
			p2 = Runtime.getRuntime().exec("java Program"); // this will run the program

			BufferedReader stream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// this is where the process outputs error messages

			boolean error = false;
			String errorOutput = "";
			String errorLine = stream.readLine(); // takes the error statement. null if no errors
			while (errorLine != null) { // gets all the console error output
				errorOutput += errorLine;
				errorLine = stream.readLine();
				error = true;
			}
			if (error) {
				stream.close();
				try {
					handleCompilerError(errorOutput); // TODO add specific error reporting here
				} catch (NumberFormatException e) {
					return ERROR;
				}
				throw new BlockCodeCompilerErrorException();
			}
			BufferedReader inStream = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			p2.getOutputStream().flush();
			String in = inStream.readLine(); // this variable will hold console output from the program
			while (in != null) { // gets all the console output
				output = output + in + System.lineSeparator();
				in = inStream.readLine();
			}
			inStream.close();

		} catch (InterruptedException | IOException e) {
			return ERROR; // No way to forsee this
		}
		return output;
	}

	/**
	 * 
	 * @param errorOutput - the compiler error message
	 */
	private void handleCompilerError(String errorOutput) throws NumberFormatException {
		String[] things = errorOutput.split(":");
		GraphicalBlock block = allBlocks.get(Integer.parseInt(things[1]));
		block.tagErrorOnBlock();
	}

}
