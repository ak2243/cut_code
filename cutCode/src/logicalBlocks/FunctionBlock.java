package logicalBlocks;

import cutcode.LList;

public class FunctionBlock implements Block {
	private String signature;
	public LList<Block> commands;
	public static final String MAIN = "public static void main (String[] args)";

	public FunctionBlock() {
		commands = new LList<Block>();
	}

	/**
	 * @returns the method signature followed by an open brace, a line separator,
	 *          the java code of the blocks to be executed in the function, and a
	 *          close brace
	 * @apiNote O(infinity)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		String command = "";
		for (Block b : commands) {
			command = command + b.toString();
		}
		return signature + " {" + System.lineSeparator() + command + "}" + System.lineSeparator();
	}

	/**
	 * 
	 * @param s the method signature, including keywords, return type, and
	 *          parameters
	 * @author Arjun Khanna
	 */
	public void setSignature(String s) {
		signature = s;
	}

}
