package graphics;

public abstract class OperatorBlock extends GraphicalBlock {
	private GraphicalBlock leftOperand;
	private GraphicalBlock rightOperand;
	/**
	 * 
	 * @return the operand to the left of the operator
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public GraphicalBlock getLeftOperand() {
		return leftOperand;
	}
	
	/**
	 * 
	 * @param leftOperand - the object to be set to the left of the operator
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public void setLeftOperand(GraphicalBlock leftOperand) {
		this.leftOperand = leftOperand;
	}
	
	/**
	 * 
	 * @return the operand to the right of the operator
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public GraphicalBlock getRightOperand() {
		return rightOperand;
	}
	
	/**
	 * @param rightOperand - the object to be set to the right of the operator
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public void setRightOperand(GraphicalBlock rightOperand) {
		this.rightOperand = rightOperand;
	}
}
