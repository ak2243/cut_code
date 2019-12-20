package logicalBlocks;

public class GreaterEqualBlock extends OperatorBlock {
	@Override
	public String toString() {
		return getLeftOperand().toString() + ">=" + getRightOperand().toString() + System.lineSeparator();
	}
}
