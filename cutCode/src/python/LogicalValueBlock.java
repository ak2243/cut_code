package python;

import cutcode.LogicalBlock;

/**
 * @author Arjun Khanna
 * @apiNote This block is not to be used independently, it should always be nested inside an operator
 */
public class LogicalValueBlock extends LogicalBlock {
	public String value;

	/**
	 *
	 * @param v the value that this block refers to
	 */
	public void setValue(String v) {value = v;}

	/**
	 *
	 * @return the value that this block refers to
	 */
	public String getValue() {return value;}

	@Override
	public String toString() {
		return getValue();
	}
}
