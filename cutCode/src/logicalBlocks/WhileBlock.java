package logicalBlocks;

import cutcode.LList;

public class WhileBlock implements Block{
	public LList<Block> commands;
	private OperatorBlock condition;

	/**
	 * 
	 * @param condition - the OperatorBlock condition
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public void setCondition(OperatorBlock condition) {
		this.condition = condition;
	}

	public WhileBlock() {
		commands = new LList<Block>();
	}

	/**
	 * @return the Java code for a while loop with the given condition and statements
	 * @apiNote O(n)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		String command = "";
		for (Block b : commands) {
			command = command + b.toString();
		}
		return "while (" + condition.toString() + ") {" + System.lineSeparator() + command + "}" + System.lineSeparator();
	}
}
