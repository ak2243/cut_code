package cutcode;

import java.util.List;

public class Executor {
	public String run(List<Sequence<Block>> sequences) {
		String output = "";
		for (Sequence<Block> s : sequences) {
			output += execute(s);
		}
		return "";
	}

	private String execute(Sequence<Block> s) {
		String output = "";
		for (Block b : s)
		{
			output += b.toString();
		}
		return "";
	}
}
