package graphics;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logicalBlocks.Block;

public class IntegerBlock extends GraphicalBlock {

	private TextField box1, box2;

	public IntegerBlock() {
		super(200, 40);

		box1 = new TextField();
		box1.setMaxWidth(50);
		box2 = new TextField();
		box2.setMaxWidth(30);
		HBox line = new HBox();
		line.setSpacing(5);
		line.setPadding(new Insets(8));
		Text text = new Text("Integer");
		text.setFill(Color.WHITE);
		line.getChildren().add(text);
		line.getChildren().add(box1);
		line.getChildren().add(new Label("="));
		line.getChildren().add(box2);

		this.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

		this.getChildren().add(line);

		box2.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!newValue.matches("\\d*")) {
					box2.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

	}

	public IntegerBlock(double width, double height) {
		super(width, height);
	}

	/**
	 * @apiNote O(1)
	 * @author Peter Timpane
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.IntegerBlock ret = new logicalBlocks.IntegerBlock();
		ret.setValue(Integer.parseInt(box2.getText()));
		ret.setName(box1.getText());
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new IntegerBlock();
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
