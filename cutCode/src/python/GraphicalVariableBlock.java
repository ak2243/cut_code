package python;

import cutcode.Block;
import cutcode.GraphicalBlock;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class GraphicalVariableBlock extends GraphicalBlock {
	public TextField value, name;
	
	public GraphicalVariableBlock() {
		super(200,40);
		
		name = new TextField();
		name.setMaxWidth(100);
		value = new TextField();
		value.setMinWidth(50);
		value.setMaxWidth(100);
		HBox line = new HBox();
		line.setSpacing(5);
		line.setPadding(new Insets(8));
		line.getChildren().add(name);
		line.getChildren().add(new Label("="));
		line.getChildren().add(value);
		
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,CornerRadii.EMPTY,Insets.EMPTY)));
		this.getChildren().add(line);
	}
	
	/**
	 * O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		return null;
	}
	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalVariableBlock();
	}


	
}
