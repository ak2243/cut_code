package cutcode;

public class IfBlock implements Block {
	private String condition;
	public LList<Block> commands;

	public void setCondition(String condition) {
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
		return "if (" + condition + ") {" + System.lineSeparator() + command + "}" + System.lineSeparator();
	}

}
