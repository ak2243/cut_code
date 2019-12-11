package logicalBlocks;

public class AndBlock extends OperandBlock {

	@Override
	public String toString() {
		return getLeftOperand().toString() + "&&" + getRightOperand().toString();
	}
}
