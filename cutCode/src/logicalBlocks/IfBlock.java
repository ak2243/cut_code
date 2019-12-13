package logicalBlocks;

import cutcode.LList;

public class IfBlock implements Block {
	public LList<Block> commands;
	private OperatorBlock condition;

	public void setCondition(OperatorBlock condition) {
		this.condition = condition;
	}

	public IfBlock() {
		commands = new LList<Block>();
	}

	@Override
	public String toString() {
		String command = "";
		for (Block b : commands) {
			command = command + b.toString();
		}
		return "if (" + condition.toString() + ") {" + System.lineSeparator() + command + "}" + System.lineSeparator();
	}

}
