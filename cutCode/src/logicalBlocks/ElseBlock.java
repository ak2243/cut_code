package logicalBlocks;

import cutcode.LList;

public class ElseBlock implements Block {
	public LList<Block> commands;

	/**
	 * @author Arjun Khanna
	 * @apiNote O(1)
	 */
	public ElseBlock() {
		commands = new LList<Block>();
	}

	/**
	 * @author Arjun Khanna
	 * @return the Java code for an else statement (should be after an if block)
	 * @apiNote O(?)
	 */
	@Override
	public String toString() {
		String command = "";
		for (Block b : commands) {
			command = command + b.toString();
		}
		return "else {" + System.lineSeparator() + command + "}" + System.lineSeparator();
	}
}
