package logicalBlocks;

public class EqualBlock extends OperatorBlock {
	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		String leftOperand = (getLeftOperand() == null) ? "" : getLeftOperand().toString();
		String rightOperand = (getRightOperand() == null) ? "" : getRightOperand().toString();
		return leftOperand + ".equals(" + rightOperand + ") ";
	}
}
