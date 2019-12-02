package cutcode;

public class ClassBlock extends Block {
	private String declaration;
	public LList<Block> commands;
	public static final String STANDARD = "public class Program";

	@Override
	public String execute() {
		String command = "";
		for (Block b : commands) {
			command = command + b.execute();
		}
		return declaration + " {" + System.lineSeparator() + command + " }" + System.lineSeparator();
	}

	public void setDeclaration(String str) {
		declaration = str;
	}

}
