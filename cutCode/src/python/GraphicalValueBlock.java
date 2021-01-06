package python;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.InvalidNestException;
import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashMap;

public class GraphicalValueBlock extends GraphicalBlock {
	private TextField value;
	private double initWidth, initHeight;
	
	@Override 
	public VBox[] getNestBoxes() {
		return null;
	}

	
	/**
	 * O(1)
	 * @author Arjun Khanna
	 */
	public GraphicalValueBlock(double width, double height) {
		super(width, height);
		this.setAlignment(Pos.CENTER);
		initWidth = width;
		initHeight = height;
		value = new TextField();
		Text label = new Text("value ");
		value.setMinHeight(height - height/5);
		HBox line = new HBox();
		line.setSpacing(height/8);
		line.setPadding(new Insets(height/5));
		line.getChildren().add(label);
		line.getChildren().add(value);
		this.getChildren().add(line);
		this.setBackground(
				new Background(new BackgroundFill(Color.web("#F0A3FF"), CornerRadii.EMPTY, Insets.EMPTY)));
	}

	/**
	 * O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		return logicalFactory.createValue(value.getText());
	}

	/**
	 * O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalValueBlock(initWidth, initHeight);
	}

	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		throw new InvalidNestException(); //No nesting in value blocks
	}

	@Override
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
		throw new InvalidNestException(); //No nesting in value blocks
	}

	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		return 0;
	}


	@Override
	public VBox[] getIndependentNestBoxes() {
		return null;
	}


}
