package graphics;

import java.util.List;

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

public class ElseBlock extends GraphicalBlock implements NestableBlock {
	private VBox bottomLine;
	private Sequence<GraphicalBlock> commands;

	public ElseBlock() {
		super(200, 80);

		this.setPadding(new Insets(10));
		this.setBackground(new Background(new BackgroundFill(Color.web("#D06201"), CornerRadii.EMPTY, Insets.EMPTY)));

		HBox topLine = new HBox();
		topLine.getChildren().addAll(new Label("else"));
		bottomLine = new VBox();
		bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		bottomLine.setMinWidth(40);
		bottomLine.setMinHeight(40);
		this.getChildren().addAll(topLine, bottomLine);
		commands = new Sequence<GraphicalBlock>();
	}

	/**
	 * @return the logicalBlock for an else block
	 * @apiNote O(n)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.ElseBlock ret = new logicalBlocks.ElseBlock();
		for (GraphicalBlock g : commands) {
			ret.commands.add(g.getLogicalBlock());
		}
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new ElseBlock();
	}

	/**
	 * 
	 * @param block
	 */
	public void add(GraphicalBlock block) {
		
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
		if(incrementWidth > 0) {
			bottomLine.setMinWidth(bottomLine.getMinWidth() + incrementWidth);
			this.setWidth(this.getWidth() + incrementWidth);
		}
		double incrementHeight = block.getHeight() - bottomLine.getHeight();
		if(incrementHeight > 0) {
			bottomLine.setMinHeight(bottomLine.getHeight() + incrementHeight);
			this.setHeight(this.getHeight() + incrementHeight);
		}
		bottomLine.getChildren().add(block);
		
	}

	@Override
	public Point2D getPrimaryNestPoint() {
		return new Point2D(bottomLine.getLayoutX() + this.getLayoutX(), bottomLine.getLayoutY() + this.getLayoutY() + bottomLine.getHeight());
	}

	@Override
	public Point2D getSecondaryNestPoint() {
		return null;
	}

	@Override
	public void secondaryNest(GraphicalBlock block) {
		// TODO Auto-generated method stub
		
	}


}
