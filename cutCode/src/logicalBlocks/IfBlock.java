package logicalBlocks;

import cutcode.LList;

public class IfBlock implements Block {
	public LList<Block> commands;
	private OperatorBlock condition;

	/**
	 * 
	 * @param condition the OperatorBlock that is the condition of the if statement
	 */
	public void setCondition(OperatorBlock condition) {
		this.condition = condition;
	}

	public IfBlock() {
		commands = new LList<Block>();
	}

	/**
	 * @return The Java code to make an if statement with the given condition,
	 *         including the commands that must be executed if the condition
	 *         evaluates to true
	 * @apiNote O(?)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		String command = "";
		for (Block b : commands) {
			command = command + b.toString();
		}
		return "if (" + condition.toString() + ") {" + System.lineSeparator() + command + "}" + System.lineSeparator();
	}

}
