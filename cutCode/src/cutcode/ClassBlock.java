package cutcode;

public class ClassBlock extends Block {
	private String declaration;
	public static final String STANDARD = "public class Program";

	@Override
	public String execute() {
		return declaration + " {" + System.lineSeparator();
	}

	public void setDeclaration(String str) {
		declaration = str;
	}

}
