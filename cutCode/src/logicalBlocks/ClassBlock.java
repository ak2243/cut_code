package logicalBlocks;

import cutcode.LList;

public class ClassBlock implements Block {
	private String declaration;
	public LList<Block> commands;
	public static final String STANDARD = "public class Program";

	/**
	 * @deprecated
	 * @apiNote this class is not currently in use
	 * @author Arjun Khanna
	 */
	public ClassBlock() {
		commands = new LList<Block>();
	}

	/**
	 * @deprecated
	 * @apiNote O(infinty)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		String command = "";
		for (Block b : commands) {
			command = command + b.toString();
		}
		return declaration + " {" + System.lineSeparator() + command + "}" + System.lineSeparator();
	}

	/**
	 * @deprecated
	 * @param str - the class declaration
	 * @author Arjun Khanna
	 */
	public void setDeclaration(String str) {
		declaration = str;
	}

}
