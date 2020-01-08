package logicalBlocks;

public class AndBlock extends OperatorBlock {

	@Override
	public String toString() {
		String leftOperand = (getLeftOperand() == null) ? "" : getLeftOperand().toString();
		String rightOperand = (getRightOperand() == null) ? "" : getRightOperand().toString();
		return leftOperand + " && " + rightOperand;
	}
}
