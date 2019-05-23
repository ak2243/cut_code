package application;

import java.util.ArrayList;

public class IfBlock extends Block<String> {

	private ArrayList<Block<?>> contents;
	private ArrayList<Block<?>> elseContents;
	private boolean condition;
	private int id;

	public IfBlock(boolean condition) {
		this.condition = condition; // the condition, to the IfBlock object, is just a boolean
		contents = new ArrayList<Block<?>>();// what to run if true
		elseContents = new ArrayList<Block<?>>(); // what to run if false. We never used this but I had it here in case
													// we wanted to later

	}

	public boolean getCondition() { // returns the condition
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
		if (!condition) // By this, it only continues if the condition is true
			return "";
		String console = "";
		String newLine = System.getProperty("line.separator");

		for (Block<?> b : contents) {
			if (b instanceof PrintBlock || b instanceof IfBlock) {
				console = console + b.execute() + newLine; // that way we get the correct things on the console
			} else {
				b.execute(); // All the other blocks still need to be executed
			}
		}

		return console;

	}

	public int getId() { // Special id for if statements
		return id;
	}

	public void setId(int id) { // Special id for if statements
		this.id = id;
	}

}
