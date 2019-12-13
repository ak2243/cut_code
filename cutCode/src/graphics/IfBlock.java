package graphics;

import cutcode.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class IfBlock extends GraphicalBlock {

	private Sequence<GraphicalBlock> commands;

	public IfBlock() {
		super(200,80);
		
		this.setPadding(new Insets(10));
		this.setBackground(new Background(new BackgroundFill(Color.ORANGE,CornerRadii.EMPTY,Insets.EMPTY)));
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

	@Override
	public logicalBlocks.Block getLogicalBlock() {
		logicalBlocks.IfBlock ret = new logicalBlocks.IfBlock();
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
