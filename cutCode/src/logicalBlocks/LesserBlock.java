package logicalBlocks;

public class LesserBlock extends OperatorBlock {
	/**
	 * @return the java code to see if the left operand is less than the right operand
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		return getLeftOperand().toString() + "<" + getRightOperand().toString();
	} 
}
