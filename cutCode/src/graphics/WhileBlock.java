package graphics;

import logicalBlocks.Block;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class WhileBlock extends GraphicalBlock {

	public Sequence<GraphicalBlock> commands;
	private OperatorBlock condition;
	private VBox bottomLine;
	public WhileBlock() {

		super(200, 90);
		this.setPadding(new Insets(10));
		this.setSpacing(10);
		this.setBackground(new Background(new BackgroundFill(Color.web("#D06201"),CornerRadii.EMPTY,Insets.EMPTY)));
		HBox topLine = new HBox();
		topLine.getChildren().add(new Label("while"));
		VBox conditionSpace = new VBox();
		topLine.setSpacing(5);
		
		conditionSpace.setMinHeight(30);
		conditionSpace.setMinWidth(140);
		conditionSpace.setBackground(new Background(new BackgroundFill(Color.web("#D96969"),CornerRadii.EMPTY,Insets.EMPTY)));
		topLine.getChildren().add(conditionSpace);
		bottomLine = new VBox();
		bottomLine.setMinWidth(160);
		bottomLine.setMinHeight(30);
		bottomLine.setBackground(new Background(new BackgroundFill(Color.web("#DFDFDF"),CornerRadii.EMPTY,Insets.EMPTY)));

		this.getChildren().addAll(topLine, bottomLine);
		commands = new Sequence<GraphicalBlock>();
		bottomLine.setPadding(new Insets(5));
	}

	/**
	 * @apiNote method efficiency O(infinity)?
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {

		logicalBlocks.WhileBlock ret = new logicalBlocks.WhileBlock();
		if(condition != null)
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
