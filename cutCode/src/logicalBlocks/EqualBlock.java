package logicalBlocks;

public class EqualBlock extends OperatorBlock {
	@Override
	public String toString() {
		return getLeftOperand().toString() + ".equals(" + getRightOperand().toString() + ");" + System.lineSeparator();
	}
}
