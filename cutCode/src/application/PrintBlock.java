package application;

public class PrintBlock extends Block<String> {
	private String print;
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return print;
	}
	
	/**
	 * @param print the print to set
	 */
	public void setPrint(String print) {
		this.print = print;
	}

}
