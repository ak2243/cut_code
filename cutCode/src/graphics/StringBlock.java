package graphics;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class StringBlock extends GraphicalBlock {
	public TextField value, name;
	
	public StringBlock() {
		super(200,40);
		
		name = new TextField();
		name.setMaxWidth(50);
		value = new TextField();
		value.setMinWidth(50);
		value.setMaxWidth(70);
		HBox line = new HBox();
		line.setSpacing(5);
		line.setPadding(new Insets(8));
		line.getChildren().add(new Label("string"));
		line.getChildren().add(name);
		line.getChildren().add(new Label("="));
		line.getChildren().add(value);
		
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,CornerRadii.EMPTY,Insets.EMPTY)));
		System.err.println(this.getMinWidth() + "f");
		this.getChildren().add(line);
	}
	
	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.StringBlock ret = new logicalBlocks.StringBlock();
		ret.setValue(value.getText());
		ret.setName(name.getText());
		return ret;
	}
	@Override
	public GraphicalBlock cloneBlock() {
		return new StringBlock();
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
