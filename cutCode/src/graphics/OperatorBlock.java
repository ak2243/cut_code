package graphics;

import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;

public abstract class OperatorBlock extends NestableBlock {
	protected GraphicalBlock leftOperand;
	protected GraphicalBlock rightOperand;

	protected VBox op1;
	protected VBox op2;

	/**
	 * 
	 * @return the operand to the left of the operator
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */

	protected OperatorBlock(int width, int height) {
		super(width, height);

	}

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

	@Override
	public Point2D getPrimaryNestPoint() {
		return new Point2D(op1.getLayoutX() + this.getLayoutX(), op1.getLayoutY() + this.getLayoutY());
	}

	@Override
	public Point2D getSecondaryNestPoint() {
		return new Point2D(op2.getLayoutX() + this.getLayoutX(), op2.getLayoutY() + this.getLayoutY());
	}

	@Override
	public void primaryNest(GraphicalBlock block) {
		if (leftOperand == null) {
			leftOperand = block;
			double incrementWidth = block.getWidth() - op1.getWidth();
			if (incrementWidth > 0) {
				op1.setMinWidth(op1.getMinWidth() + incrementWidth);
				this.setMinWidth(this.getWidth() + incrementWidth);
			}
			double incrementHeight = block.getHeight() - op1.getHeight();
			if (incrementHeight > 0) {
				op1.setMinHeight(op1.getHeight() + incrementHeight);				
				this.setMinHeight(this.getHeight() + incrementHeight);
			}
			op1.getChildren().add(block);
		}
		
	}

	@Override
	public void secondaryNest(GraphicalBlock block) {
		System.err.print("Secondary nest is being run old sport");
		if (rightOperand == null) {
			rightOperand = block;
			double incrementWidth = block.getWidth() - op2.getWidth();
			if (incrementWidth > 0) {
				op2.setMinWidth(op2.getMinWidth() + incrementWidth);
				this.setMinWidth(this.getWidth() + incrementWidth);
			}
			double incrementHeight = block.getHeight() - op2.getHeight();
			if (incrementHeight > 0) {
				op2.setMinHeight(op2.getHeight() + incrementHeight);				
				this.setMinHeight(this.getHeight() + incrementHeight);
			}
			op2.getChildren().add(block);
		}

	}
	
	private void setBothHeight(double height) {
		this.setMinHeight(height);
	}
	
	private void setBothWidth(double Width) {
		this.setMaxWidth(Width);
		this.setMinWidth(Width);
	}
}
