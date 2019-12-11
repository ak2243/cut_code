package logicalBlocks;

import cutcode.LList;

public class ClassBlock implements Block {
	private String declaration;
	public LList<Block> commands;
	public static final String STANDARD = "public class Program";

	public ClassBlock()
	{
		commands = new LList<Block>();
	}
	
	@Override
	public String toString() {
		String command = "";
		for (Block b : commands) {
			command = command + b.toString();
		}
		return declaration + " {" + System.lineSeparator() + command + "}" + System.lineSeparator();
	}

	public void setDeclaration(String str) {
		declaration = str;
	}

}
