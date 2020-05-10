package Java;

import cutcode.LogicalBlock;

public class LogicalPrintBlock extends LogicalBlock {
	private String print;

	/**
	 *
	 * @return the statement this block will print, with quotes (if applicable)
	 */
	public String getPrint() {return print;}

	/**
	 *
	 * @param p the statement this block will print. Quotes should be used for strings but not for variables
	 */
	public void setPrint(String p) {print = p;}

	@Override
	public String toString() {
		String indents = "";
		for(int i = 0; i < getIndentFactor(); i++)
			indents += "	";

		return indents + "System.out.println(" + getPrint() + ");" + System.lineSeparator();
	}


}
