package cutcode;

public class PrintBlock implements Block {
	private String print;

	@Override
	public String toString() {
		return "System.out.println(" + print + ");" + System.lineSeparator(); // This is displayed on the console by the run mechanism
	}

	/**
	 * @param print the print to set
	 */
	public void setPrint(String print) { // sets the output from this block
		this.print = print;
	}

}
