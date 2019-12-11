package logicalBlocks;

public abstract class OperandBlock implements Block {
	private Block leftOperand;
	private Block rightOperand;
	public Block getRightOperand() {
		return rightOperand;
	}
	public void setRightOperand(Block rightOperand) {
		this.rightOperand = rightOperand;
	}
	public Block getLeftOperand() {
		return leftOperand;
	}
	public void setLeftOperand(Block leftOperand) {
		this.leftOperand = leftOperand;
	}
}
