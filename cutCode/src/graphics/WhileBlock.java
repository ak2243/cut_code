package graphics;

import logicalBlocks.Block;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class WhileBlock extends GraphicalBlock {

	public Sequence<GraphicalBlock> commands;
	private OperatorBlock condition;

	public WhileBlock() {

		super(200, 90);
		this.setPadding(new Insets(10));
		this.setSpacing(10);
		this.setStyle("-fx-background-color: #D06201");
		HBox topLine = new HBox();
		topLine.getChildren().add(new Label("while"));
		VBox conditionSpace = new VBox();
		topLine.setSpacing(5);
		;
		conditionSpace.setMinHeight(30);
		conditionSpace.setMinWidth(140);
		conditionSpace.setStyle("-fx-background-color: #D96969;");
		topLine.getChildren().add(conditionSpace);
		VBox bottomLine = new VBox();
		bottomLine.setMinWidth(160);
		bottomLine.setMinHeight(30);
		bottomLine.setStyle("-fx-background-color: #DFDFDF");

		this.getChildren().addAll(topLine, bottomLine);

	}

	/**
	 * @apiNote method efficiency O(infinity)?
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.WhileBlock ret = new logicalBlocks.WhileBlock();
		ret.setCondition((logicalBlocks.OperatorBlock) condition.getLogicalBlock());
		for (GraphicalBlock g : commands) {
			ret.commands.add(g.getLogicalBlock());
		}
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub

		return new WhileBlock();
	}

	/**
	 * @deprecated
	 */
	@Override
	public String toJSON() {

		return null;
	}

}
