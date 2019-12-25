package logicalBlocks;

public abstract class OperatorBlock implements Block {
	private Block leftOperand;
	private Block rightOperand;
	
	/**
	 * @return the operand (object type logicalBlocks.Block) that is the rightOperand
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public Block getRightOperand() { //TODO: restrictions here?
		return rightOperand;
	}
	
	/**
	 * @param rightOperand - the operand to the right of the operator
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public void setRightOperand(Block rightOperand) {
		this.rightOperand = rightOperand;
	}
	
	/**
	 * @return the operand (object type logicalBlocks.Block) to the left of the operator
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public Block getLeftOperand() {
		return leftOperand;
	}
	
	/**
	 * @param leftOperand - the operand to the left of the operator
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public void setLeftOperand(Block leftOperand) {
		this.leftOperand = leftOperand;
	}
}
