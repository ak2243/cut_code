package logicalBlocks;

import cutcode.LList;

public class FunctionBlock implements Block { //THIS FILE IS NOT BEING USED IN THE CURRENT VERSION OF CUT_CODE
	private String signature;
	public LList<Block> commands;
	/**
	 * @deprecated
	 */
	public static final String MAIN = "public static void main (String[] args)";

	/**
	 * @deprecated
	 */
	public FunctionBlock() {
		commands = new LList<Block>();
	}

	/**
	 * @returns the method signature followed by an open brace, a line separator,
	 *          the java code of the blocks to be executed in the function, and a
	 *          close brace
	 * @apiNote O(infinity)
	 * @author Arjun Khanna
	 * @deprecated
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
	 *          parameters. do not include braces or line separators
	 * @author Arjun Khanna
	 * @deprecated
	 */
	public void setSignature(String s) {
		signature = s;
	}

}
