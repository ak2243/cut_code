package logicalBlocks;

public class LesserEqualBlock extends OperatorBlock {
	/**
	 * @return the java code to see if the left operand is less than or equal to the right operand
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		return getLeftOperand().toString() + "<=" + getRightOperand().toString();
	}
}
