package cutcode;

public class test {

	public static void main(String[] args) {
		Block b = new IfBlock();
		PrintBlock p = new PrintBlock();
		p.setPrint("hello world");
		((IfBlock) b).commands.add(p);
		System.err.println(b.execute());
	}

}
