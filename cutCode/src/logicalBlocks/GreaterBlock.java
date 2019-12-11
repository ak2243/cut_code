package logicalBlocks;

public class GreaterBlock extends OperandBlock{
	@Override
	public String toString() {
		return getLeftOperand().toString() + ">" + getRightOperand().toString();
	}
}
