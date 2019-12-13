package logicalBlocks;

public class GreaterBlock extends OperatorBlock{
	@Override
	public String toString() {
		return getLeftOperand().toString() + ">" + getRightOperand().toString() + System.lineSeparator();
	}
}
