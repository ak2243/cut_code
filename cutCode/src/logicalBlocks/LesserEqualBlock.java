package logicalBlocks;

public class LesserEqualBlock extends OperatorBlock {
	@Override
	public String toString() {
		return getLeftOperand().toString() + "<=" + getRightOperand().toString() + System.lineSeparator();
	}
}
