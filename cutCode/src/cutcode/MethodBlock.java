package cutcode;

public class MethodBlock extends Block{
	private String signature;
	public static final String MAIN = "public static void main (String[] args)";
	@Override
	public String execute() {
		return signature + " {" + System.lineSeparator();
	}
	
	public void setSignature(String s)
	{
		signature = s;
	}
	
}
