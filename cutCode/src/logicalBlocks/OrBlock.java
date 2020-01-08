package logicalBlocks;

public class OrBlock extends OperatorBlock{
	/**
	 * @return the java code to test if either the right or left operands evaluates to true
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		String leftOperand = (getLeftOperand() == null) ? "" : getLeftOperand().toString();
		String rightOperand = (getRightOperand() == null) ? "" : getRightOperand().toString();
		return leftOperand + " || " + rightOperand;
	}
}
