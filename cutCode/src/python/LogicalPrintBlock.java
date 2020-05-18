package python;

import cutcode.LogicalBlock;

public class LogicalPrintBlock extends LogicalBlock {
	private LogicalBlock print;



	/**
	 *
	 * @param print the statement this block will print. Quotes should be used for strings but not for variables
	 */
	public void setPrint(LogicalBlock print) {this.print = print;}

	@Override
	public String toString() {
		String indents = "";
		for(int i = 0; i < getIndentFactor(); i++)
			indents += "	";

		return indents + "print(" + print.toString() + ")" + System.lineSeparator();
	}


}
