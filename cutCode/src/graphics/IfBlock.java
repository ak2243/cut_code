package graphics;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class IfBlock extends GraphicalBlock {

	private Sequence<GraphicalBlock> commands;
	private AndBlock condition; //TODO make this OperatorBlock

	public IfBlock() {
		super(200,80);

		this.setPadding(new Insets(10));
		this.setId("if");
		System.err.println(this.getId());
		HBox topLine = new HBox();
		topLine.getChildren().addAll(new Label("if"));
		VBox bottomLine = new VBox();
			bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
		bottomLine.setMinWidth(40);
		bottomLine.setMinHeight(40);
		this.getChildren().addAll(topLine,bottomLine);
		commands = new Sequence<GraphicalBlock>();
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
}
