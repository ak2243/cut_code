package graphics;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class BooleanBlock extends GraphicalBlock {
	public ComboBox<String> value;
	public TextField name;

	public BooleanBlock() {
		super(200, 40);

		name = new TextField();
		name.setMaxWidth(30);
		value = new ComboBox<String>();
		value.getItems().add("true");
		value.getItems().add("false");
		value.setMinWidth(70);
		value.setMaxWidth(70);
		HBox line = new HBox();
		line.setSpacing(5);
		line.setPadding(new Insets(8));
		line.getChildren().add(new Label("condition"));
		line.getChildren().add(name);
		line.getChildren().add(new Label("="));
		line.getChildren().add(value);

		this.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		System.err.println(this.getMinWidth() + "f");
		this.getChildren().add(line);
	}

	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.BooleanBlock ret = new logicalBlocks.BooleanBlock();
		ret.setValue(Boolean.parseBoolean(value.getValue()));
		ret.setName(name.getText());
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new BooleanBlock();
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
}
