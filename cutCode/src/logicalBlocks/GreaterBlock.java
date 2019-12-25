package logicalBlocks;

public class GreaterBlock extends OperatorBlock{
	/**
	 * @return the java code to see if the left operand is greater than the right operand
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		return getLeftOperand().toString() + ">" + getRightOperand().toString() + System.lineSeparator();
	}
}
