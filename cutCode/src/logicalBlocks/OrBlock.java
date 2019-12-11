package logicalBlocks;

public class OrBlock extends OperandBlock{
	@Override
	public String toString() {
		return getLeftOperand().toString() + "&&" + getRightOperand().toString();
	}
}
