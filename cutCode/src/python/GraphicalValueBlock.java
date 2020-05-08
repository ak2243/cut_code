package python;

import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GraphicalValueBlock extends GraphicalBlock {
	private TextField name;
	/**
	 * O(1)
	 * @author Arjun Khanna
	 */
	public GraphicalValueBlock() {
		super(200, 40);
		name = new TextField();
		Text label = new Text("value ");
		name.setMinHeight(32);
		HBox line = new HBox();
		line.setSpacing(5);
		line.setPadding(new Insets(8));
		line.getChildren().add(label);
		line.getChildren().add(name);
		this.getChildren().add(line);
		this.setBackground(
				new Background(new BackgroundFill(Color.web("#E09DFA"), CornerRadii.EMPTY, Insets.EMPTY)));

	}

	/**
	 * O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public LogicalBlock getLogicalBlock() {
		return null;
	}

	/**
	 * O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalValueBlock();
	}




}
