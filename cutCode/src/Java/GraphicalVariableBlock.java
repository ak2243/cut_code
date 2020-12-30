
package Java;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphicalVariableBlock extends GraphicalBlock {
	private TextField name;
	private VBox[] nestBoxes;
	private ComboBox<String> types;
	private double initWidth, initHeight;
	
	@Override 
	public VBox[] getNestBoxes() {
		return nestBoxes;
	}


	public GraphicalVariableBlock(double width, double height) {
		//setting up the visuals of the block
		super(width, height);
		this.initWidth = width;
		this.initHeight = height;
		nestBoxes = new VBox[1];
		String[] typeChoices = {"num", "T/F", "str", "edit"}; //type options
		types = new ComboBox<String>(FXCollections.observableArrayList(FXCollections.observableArrayList(typeChoices)));
		types.setMinWidth(2 * initWidth/5);
		name = new TextField();
		name.setMaxWidth(initWidth/5);
		name.setMinWidth(initWidth/5);
		VBox value = new VBox();
		value.setMinWidth(initWidth/6);
		value.setMinHeight(initHeight - initHeight/3);
		value.setMaxWidth(initWidth/6);
		value.setMaxHeight(initHeight - initHeight/3);

		value.setStyle("-fx-background-color: #E6E6E6"); //color of nest field
		nestBoxes[0] = value;
		HBox line = new HBox();
		line.setSpacing(initHeight/8);
		line.setPadding(new Insets(initHeight/5));
		line.getChildren().add(types);
		line.getChildren().add(name);
		line.getChildren().add(new Label("="));
		line.getChildren().add(value);

		this.setBackground(
				new Background(new BackgroundFill(Color.web("#D097F4"), CornerRadii.EMPTY, Insets.EMPTY)));

		this.getChildren().add(line);
	}

	@Override
	public Point2D[] getNestables() { //returns the top left of the nest point
		Point2D[] ret = new Point2D[nestBoxes.length];
		for (int i = 0; i < nestBoxes.length; i++)
			ret[i] = nestBoxes[i].localToScene(nestBoxes[i].getLayoutBounds().getMinX(), nestBoxes[i].getLayoutBounds().getMinY());
		return ret;
	}


	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		if (nestBoxes[index].getChildren().size() != 0) //can only nest one block inside it (this block can have blocks nested inside it though)
			throw new InvalidNestException();
		try {
			VBox box = nestBoxes[index];
			increment(box, nest);
			box.getChildren().add(nest);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidNestException();
		}
		nest.setNestedIn(this);
	}


	@Override
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException { //takes out the block and resets field and block sizes
		if (box == null || rem == null)
			throw new InvalidNestException();
		
		rem.minHeightProperty().removeListener(super.heightListeners.get(rem));
		rem.minWidthProperty().removeListener(super.widthListeners.get(rem));
		super.heightListeners.remove(rem);
		super.widthListeners.remove(rem);

		
		box.getChildren().remove(rem);
		box.setMinWidth(initWidth/6);
		box.setMinHeight(initHeight - initHeight/3);
		box.setMaxWidth(initWidth/6);
		box.setMaxHeight(initHeight - initHeight/3);

		this.setSize(initWidth, initHeight);

	}


	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) { //used to match block to line locations
		lineLocations.put(getLineNumber(), this);
		return getLineNumber() + 1; //has a new line at the end of it
	}

	@Override
	public ArrayList<GraphicalBlock> getChildBlocks() {
		ArrayList<GraphicalBlock> ret = new ArrayList<>();
		if(nestBoxes[0].getChildren().size() == 1) {
			ret.add((GraphicalBlock) nestBoxes[0].getChildren().get(0)); //only one block nested inside
		}
		return ret; //empty array list if nothing nested
	}

	/**
	 * O(1)
	 *
	 * @author Arjun Khanna
	 */
	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		if(nestBoxes[0].getChildren().size() != 1) {
			tagErrorOnBlock();
			throw new BlockCodeCompilerErrorException(); //needs something nested inside it
		}
		return logicalFactory.createVariable(getIndentFactor(), types.getValue(), name.getText(), ((GraphicalBlock) nestBoxes[0].getChildren().get(0)).getLogicalBlock());
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalVariableBlock(initWidth, initHeight);
	}


}
