package python;

import cutcode.LogicalBlock;

public class LogicalVariableBlock extends LogicalBlock {
	private String name;
	private LogicalBlock value;

	/**
	 *
	 * @return the name of the variable that this block creates
	 */
	public String getName() {return name;}

	/**
	 *
	 * @param name the value of the variable defined in this block
	 */
	public void setName(String name) {this.name = name;}

	/**
	 *
	 * @return the variable that this block creates
	 */
	public LogicalBlock getValue() {return value;}

	/**
	 *
	 * @param value the value of the variable defined in this block
	 */
	public void setValue(LogicalBlock value) {this.value = value;}

	@Override
	public String toString() {
		String indents = "";
		for(int i = 0; i < getIndentFactor(); i++)
			indents += "	";
		return indents + name + " = " + getValue().toString() + System.lineSeparator();
	}
}
