package Java;

import cutcode.LogicalBlock;

public class LogicalVariableBlock extends LogicalBlock {
	private String name;
	private LogicalBlock value;
	private String type; //null if editing variable

	/**
	 *
	 * @param type the type of this variable. use null if editing
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 *
	 * @return the type of this variable
	 */
	public String getType() {
		return this.type;
	}

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
		return indents + type + " " + name + " = " + getValue().toString() + ";" + System.lineSeparator();
	}
}
