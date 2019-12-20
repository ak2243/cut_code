package cutcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import graphics.GraphicalBlock;
import logicalBlocks.Block;

public class Executor {
	public static final String ERROR = "An error occured, please try again later.";
	
	/**
	 * 
	 * Converts graphical block to string code
	 * 
	 * @param sequences; the List of sequences of graphical blocks
	 * @return a String object, containing the full code
	 * efficiency this method is O(n^3)
	 */
	public String getCode(List<Sequence<Block>> sequences) {
		String output = "";
		for (Sequence<Block> s : sequences) {
			output += sequenceToJava(s);
		}
		return "public class Program {" + System.lineSeparator() + "public static void main (String[] args) {" + System.lineSeparator() + 
				output + "}" + System.lineSeparator() + "}";
	}

	private String sequenceToJava(Sequence<Block> s) {
		String output = "";
		for (Block block : s) { //Need to go through all the blocks
			output += block.toString(); //calls the Block.toString() method to get the java code
		}
		return output;
	}
	
	
	/**
	 * @param filename the .java file to be run
	 * @return the output from the file
	 */
	public String run(String filename)
	{
		String output = "";
		Process p;
		Process p2;
		try
		{
			p = Runtime.getRuntime().exec("javac Program.java");
			p.waitFor();
			p2 = Runtime.getRuntime().exec("java Program");
			
			BufferedReader stream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			boolean error = false;
			String inn = stream.readLine();
			while (inn != null) {
				System.err.println(inn);
				inn = stream.readLine();
				error = true;
			}
			if(error)
			{
				stream.close();
				return "Error";
			}
			BufferedReader inStream = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			p2.getOutputStream().flush();
			String in = inStream.readLine();
			while (in != null) {
				output = output + in + System.lineSeparator();
				in = inStream.readLine();
			}
			inStream.close();
			
		}
		catch(InterruptedException | IOException e)
		{
			return ERROR;
		}
		return output;
	}
	
}
