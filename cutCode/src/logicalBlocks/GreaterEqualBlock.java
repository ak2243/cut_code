package logicalBlocks;

public class GreaterEqualBlock extends OperatorBlock {
	/**
	 * @return the java code to see if the left operand is greater than or equal to the right operand
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		return getLeftOperand().toString() + " >= " + getRightOperand().toString();
	}
}
