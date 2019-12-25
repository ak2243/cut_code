package logicalBlocks;

public class PrintBlock implements Block {
	private String print;
	/**
	 * @return the java code to print the statement
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		return "System.out.println(" + print + ");" + System.lineSeparator(); // This is displayed on the console by the run mechanism
	}

	/**
	 * @param print - the statement to be printed
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public void setPrint(String print) { // sets the output from this block
		this.print = print;
	}

}
