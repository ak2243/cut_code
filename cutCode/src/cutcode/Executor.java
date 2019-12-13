package cutcode;

import java.util.List;

import graphics.GraphicalBlock;

public class Executor {
	/**
	 * 
	 * Converts graphical block to string code
	 * 
	 * @param sequences; the List of sequences of graphical blocks
	 * @return a String object, containing the full code
	 * efficiency this method is O(n^3)
	 */
	public String getCode(List<Sequence<GraphicalBlock>> sequences) {
		String output = "";
		for (Sequence<GraphicalBlock> s : sequences) {
			output += sequenceToJava(s);
		}
		return output;
	}

	private String sequenceToJava(Sequence<GraphicalBlock> s) {
		String output = "";
		for (GraphicalBlock block : s) { //Need to go through all the blocks
			output += block.getLogicalBlock().toString(); //calls the Block.toString() method to get the java code
		}
		return output;
	}
	
	
}
