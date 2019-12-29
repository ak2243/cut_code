package cutcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import graphics.GraphicalBlock;
import graphics.Sequence;

public class Executor {
	private int lineLoc = 2;
	private HashMap<Integer, logicalBlocks.Block> allBlocks;
	public static final String ERROR = "An error occured, please try again later.";

	/**
	 * 
	 * Converts graphical block to string code
	 * 
	 * @param sequences; the List of sequences of graphical blocks
	 * @return a String of the Java code for the sequence of GraphicalBlocks
	 * @apiNote O(n^3)
	 * @author Arjun Khanna
	 */
	public String getCode(List<Sequence<logicalBlocks.Block>> sequences) {
		allBlocks = new HashMap<Integer, logicalBlocks.Block>();
		String output = "";
		for (Sequence<logicalBlocks.Block> s : sequences) {
			output += sequenceToJava(s);
		}
		return "public class Program {" + System.lineSeparator() + output + "}";
	}

	/**
	 * 
	 * @param s - the sequence of GraphicalBlocks that should be converted to java
	 *          code
	 * @return the java code (with new lines but no indents) of the graphical blocks
	 * @author Arjun Khanna
	 */
	private String sequenceToJava(Sequence<logicalBlocks.Block> s) {
		String output = "";
		for (logicalBlocks.Block block : s) { // Need to go through all the blocks
			putInHashMap(block);
			output += block.toString(); // calls the Block.toString() method to get the java code
		}
		return output;
	}

	private void putInHashMap(logicalBlocks.Block block) {
		allBlocks.put(lineLoc, block);
		lineLoc++;
		if (block instanceof logicalBlocks.FunctionBlock) {
			for (logicalBlocks.Block b : ((logicalBlocks.FunctionBlock) block).commands) {
				putInHashMap(b);
			}
			lineLoc++;
		} else if (block instanceof logicalBlocks.IfBlock) {
			for (logicalBlocks.Block b : ((logicalBlocks.IfBlock) block).commands) {
				putInHashMap(b);
			}
			lineLoc++;
		}
	}

	/**
	 * @param filename the .java file to be run
	 * @return the console output from running the file
	 * @apiNote O(?)
	 * @author Arjun Khanna
	 */
	public String run(String filename) { // TODO: find efficiency of this method
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
				return parseErrorMessage(errorOutput); // TODO add specific error reporting here
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
	 * @return
	 */
	private String parseErrorMessage(String errorOutput) {
		String[] things = errorOutput.split(":");
		logicalBlocks.Block block = allBlocks.get(Integer.parseInt(things[1]));
		return block.toString();
	}

}
