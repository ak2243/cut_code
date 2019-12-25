package logicalBlocks;

public class EqualBlock extends OperatorBlock {
	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		return getLeftOperand().toString() + ".equals(" + getRightOperand().toString() + ");" + System.lineSeparator();
	}
}
