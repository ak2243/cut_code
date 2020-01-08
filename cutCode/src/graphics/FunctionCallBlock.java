package graphics;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logicalBlocks.Block;

public class FunctionCallBlock extends GraphicalBlock { //THIS FILE IS NOT BEING USED IN THE CURRENT VERSION OF CUT_CODE
	private TextField name;
	/**
	 * @deprecated
	 * 
	 */
	public FunctionCallBlock() {
		super(200, 40);
		name = new TextField();
		Text label = new Text("func:");
		label.setFill(Color.WHITE);
		name.setMinHeight(32);
		HBox line = new HBox();
		line.setSpacing(5);
		line.setPadding(new Insets(8));
		line.getChildren().add(label);
		line.getChildren().add(name);
		this.getChildren().add(line);
		this.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	/**
	 * @deprecated
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.FunctionCallBlock ret = new logicalBlocks.FunctionCallBlock();
		ret.setName(name.getText());
		return ret;
	}

	/**
	 * @deprecated
	 */
	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub
		return new FunctionCallBlock();
	}

	/**
	 * @deprecated
	 */
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
