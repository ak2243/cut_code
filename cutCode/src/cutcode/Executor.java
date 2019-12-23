package cutcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import graphics.GraphicalBlock;
import graphics.Sequence;
import logicalBlocks.Block;

public class Executor {
	
	public static final String ERROR = "An error occured, please try again later.";

	/**
	 * 
	 * Converts graphical block to string code
	 * 
	 * @param sequences; the List of sequences of graphical blocks
	 * @return a String object, containing the full code efficiency this method is
	 *         O(n^3)
	 * @author Arjun Khanna
	 */
	public String getCode(List<Sequence<GraphicalBlock>> sequences) {
		String output = "";
		for (Sequence<GraphicalBlock> s : sequences) {
			output += sequenceToJava(s);
		}
		return "public class Program {" + System.lineSeparator() + "public static void main (String[] args) {"
				+ System.lineSeparator() + output + "}" + System.lineSeparator() + "}";
	}

	/**
	 * 
	 * @param s - the sequence of GraphicalBlocks that should be converted to java code
	 * @return the java code (with new lines but no indents) of the graphical blocks
	 * @author Arjun Khanna
	 */
	private String sequenceToJava(Sequence<GraphicalBlock> s) {
		String output = "";
		for (GraphicalBlock block : s) { // Need to go through all the blocks
			output += block.getLogicalBlock().toString(); // calls the Block.toString() method to get the java code
		}
		return output;
	}

	/**
	 * @param filename the .java file to be run
	 * @return the console output from running the file. efficiency unknown
	 * @author Arjun Khanna
	 */
	public String run(String filename) { //TODO: find efficiency of this method
		String output = "";
		Process p;
		Process p2;
		try {
			p = Runtime.getRuntime().exec("javac Program.java"); // this will compile the program
			p.waitFor();
			p2 = Runtime.getRuntime().exec("java Program"); // this will run the program

			BufferedReader stream = new BufferedReader(new InputStreamReader(p.getErrorStream())); // this is where the
																									// program will
																									// output error
																									// messages
			boolean error = false;
			String inn = stream.readLine(); //takes the error statement. null if no errors
			while (inn != null) { //gets all the console error output
				inn = stream.readLine();
				error = true;
			}
			if (error) { 
				stream.close();
				return ERROR; //TODO add specific error reporting here
			}
			BufferedReader inStream = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			p2.getOutputStream().flush();
			String in = inStream.readLine(); //this variable will hold console output from the program
			while (in != null) { //gets all the console output
				output = output + in + System.lineSeparator();
				in = inStream.readLine();
			}
			inStream.close();

		} catch (InterruptedException | IOException e) {
			return ERROR; //No way to forsee this
		}
		return output;
	}

}
