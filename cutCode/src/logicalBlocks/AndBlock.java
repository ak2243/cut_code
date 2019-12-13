package logicalBlocks;

public class AndBlock extends OperatorBlock {

	@Override
	public String toString() {
		return getLeftOperand().toString() + "&&" + getRightOperand().toString() + System.lineSeparator();
	}
}
