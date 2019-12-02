package cutcode;

public class IfBlock extends Block {
	private String condition;
	public LList<Block> commands;

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public IfBlock() {
		commands = new LList<Block>();
	}

	@Override
	public String execute() {
		String command = "";
		for (Block b : commands) {
			command = command + b.execute();
		}
		return "if (" + condition + ") {" + System.lineSeparator() + command + "}" + System.lineSeparator();
	}

}
