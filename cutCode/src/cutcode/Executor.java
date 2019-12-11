package cutcode;

import java.util.List;

import graphics.GraphicalBlock;

public class Executor {
	/**
	 * 
	 * @param sequences; the List of sequences of graphical blocks
	 * @return a String object, containing the full code
	 * @efficiency this method is O(n^3)
	 */
	public String run(List<Sequence<GraphicalBlock>> sequences) {
		String output = "";
		for (Sequence<GraphicalBlock> s : sequences) {
			output += execute(s);
		}
		return output;
	}

	private String execute(Sequence<GraphicalBlock> s) {
		String output = "";
		for (GraphicalBlock block : s) { //Need to go through all the blocks
			output += block.getLogicalBlock().toString(); //cals the Block.toString() method to get the java code
		}
		return output;
	}
}
