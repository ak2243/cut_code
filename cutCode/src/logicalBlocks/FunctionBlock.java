package logicalBlocks;

import cutcode.LList;

public class FunctionBlock implements Block {
	private String signature;
	public LList<Block> commands;
	public static final String MAIN = "public static void main (String[] args)";

	public FunctionBlock() {
		commands = new LList<Block>();
	}

	@Override
	/**
	 * @returns the method signature followed by " {" and a line separator
	 */
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
	 */
	public void setSignature(String s) {
		signature = s;
	}

}
