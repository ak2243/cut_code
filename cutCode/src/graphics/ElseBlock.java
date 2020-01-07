package graphics;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class ElseBlock extends GraphicalBlock {
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
		commands.add(block);
		double incrementWidth = block.getMinWidth() - bottomLine.getMinWidth();
		if(incrementWidth > 0) {
			bottomLine.setMinWidth(bottomLine.getMinWidth() + incrementWidth);
			this.setMinWidth(this.getMinWidth() + incrementWidth);
		}
		double incrementHeight = block.getMinHeight() - bottomLine.getMinWidth();
		if(incrementHeight > 0) {
			bottomLine.setMinHeight(bottomLine.getMinHeight() + incrementHeight);
			this.setMinHeight(this.getMinHeight() + incrementHeight);
		}
		bottomLine.getChildren().add(block);
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addConditionPoints(List<ConditionPoint> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNestPoints(List<NestPoint> list) {
		// TODO Auto-generated method stub
		
	}

}
