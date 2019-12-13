package logicalBlocks;

public class OrBlock extends OperatorBlock{
	@Override
	public String toString() {
		return getLeftOperand().toString() + "&&" + getRightOperand().toString();
	}
}
