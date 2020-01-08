package graphics;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class IfBlock extends NestableBlock {

	public Sequence<GraphicalBlock> commands;
	private OperatorBlock condition; // TODO make this OperatorBlock
	private VBox bottomLine;
	private VBox conditionSpace;

	public IfBlock() {
		super(200, 80);

		this.setPadding(new Insets(10));
		this.setBackground(new Background(new BackgroundFill(Color.web("#D06201"), CornerRadii.EMPTY, Insets.EMPTY)));

		HBox topLine = new HBox();
		topLine.getChildren().addAll(new Label("if"));
		bottomLine = new VBox();
		bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		bottomLine.setMinWidth(40);
		bottomLine.setMinHeight(40);
		conditionSpace = new VBox();
		conditionSpace.setMinHeight(30);
		conditionSpace.setMinWidth(140);
		conditionSpace.setBackground(
				new Background(new BackgroundFill(Color.web("#D96969"), CornerRadii.EMPTY, Insets.EMPTY)));
		topLine.getChildren().add(conditionSpace);

		this.getChildren().addAll(topLine, bottomLine);
		commands = new Sequence<GraphicalBlock>();
		bottomLine.setPadding(new Insets(5));
	}

	public IfBlock(double width, double height) {
		super(width, height);
		commands = new Sequence<GraphicalBlock>();
	}

	/**
	 * @apiNote O(infinity)
	 * @author Peter Timpane
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.IfBlock ret = new logicalBlocks.IfBlock();
		if (condition != null)
			ret.setCondition((logicalBlocks.OperatorBlock) condition.getLogicalBlock());
		for (GraphicalBlock g : commands) {
			ret.commands.add(g.getLogicalBlock());
		}
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new IfBlock();
	}

	/**
	 * @deprecated
	 */
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void primaryNest(GraphicalBlock block) {
		commands.add(block);
		double incrementWidth = block.getWidth() - bottomLine.getWidth();
		if (incrementWidth > 0) {
			bottomLine.setMinWidth(bottomLine.getMinWidth() + incrementWidth);
			this.setWidth(this.getWidth() + incrementWidth);
		}
		double incrementHeight = block.getHeight() - bottomLine.getHeight();
		if (incrementHeight > 0) {
			bottomLine.setMinHeight(bottomLine.getHeight() + incrementHeight);
			this.setHeight(this.getHeight() + incrementHeight);
		}
		bottomLine.getChildren().add(block);

	}

	@Override
	public Point2D getPrimaryNestPoint() {
		return new Point2D(bottomLine.getLayoutX() + this.getLayoutX(),
				bottomLine.getLayoutY() + this.getLayoutY() + bottomLine.getHeight());
	}

	@Override
	public Point2D getSecondaryNestPoint() {
		return new Point2D(conditionSpace.getLayoutX() + this.getLayoutX(),
				conditionSpace.getLayoutY() + this.getLayoutY());
	}

	@Override
	public void secondaryNest(GraphicalBlock block) {
		if (block instanceof OperatorBlock) {
			condition = (OperatorBlock) block;
			double incrementWidth = block.getWidth() - conditionSpace.getWidth();
			if (incrementWidth > 0) {
				conditionSpace.setMinWidth(conditionSpace.getMinWidth() + incrementWidth);
				this.setWidth(this.getWidth() + incrementWidth);
			}
			double incrementHeight = block.getHeight() - conditionSpace.getHeight();
			if (incrementHeight > 0) {
				conditionSpace.setMinHeight(conditionSpace.getHeight() + incrementHeight);
				this.setHeight(this.getHeight() + incrementHeight);
			}
			conditionSpace.getChildren().add(block);
		}

	}

}
