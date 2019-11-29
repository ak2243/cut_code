package cutcode;

import java.util.ArrayList;

public class IfBlock extends Block<String> {

	private ArrayList<Block<?>> contents;
	private ArrayList<Block<?>> elseContents;
	private String condition;
	private int id;

	public IfBlock(String condition) {
		this.condition = condition; // the condition, to the IfBlock object, is just a boolean
		contents = new ArrayList<Block<?>>();// what to run if true
		elseContents = new ArrayList<Block<?>>(); // what to run if false. We never used this but I had it here in case
													// we wanted to later

	}

	public String getCondition() { // returns the condition
		return condition;
	}

	public void addToContents(Block<?> b) { // blocks are added for what to do if true
		contents.add(b);
	}

	public void addToElse(Block<?> b) { // blocks are added for what to do if false
		elseContents.add(b);
	}

	@Override
	public String execute() {
		String s = "if (" + condition + ") {";
		for(Block<?> b : contents)
		{
			s = s + b.execute();
		}
		s = s + "} else {";
		for(Block<?> b : elseContents)
		{
			s = s + b.execute();
		}
		s = s + "}";
		return s;

	}

	public int getId() { // Special id for if statements
		return id;
	}

	public void setId(int id) { // Special id for if statements
		this.id = id;
	}

}
