package logicalBlocks;

public class LesserBlock extends OperatorBlock {
	@Override
	public String toString() {
		return getLeftOperand().toString() + "<" + getRightOperand().toString() + System.lineSeparator();
	} 
}
