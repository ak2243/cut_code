package python;

import java.util.ArrayList;
import java.util.HashMap;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;
import cutcode.LogicalBlock;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GraphicalVariableBlock extends GraphicalBlock {
	private TextField name;
	private VBox[] nestBoxes;
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
		name = new TextField();
		name.setMaxWidth(initWidth/2);
		name.setMinWidth(initWidth/2);
		name.setMaxHeight(initHeight * 0.6);
		name.setMinHeight(initHeight * 0.6);
		System.err.println(name.getHeight());
		VBox value = new VBox();
		value.setMinWidth(initWidth/4);
		value.setMinHeight(initHeight * 0.6);
		value.setMaxWidth(initWidth/4);
		value.setMaxHeight(initHeight * 0.6);

		value.setStyle("-fx-background-color: #E6E6E6"); //color of nest field
		nestBoxes[0] = value;
		HBox line = new HBox();
		line.setSpacing(initHeight/8);
		line.setPadding(new Insets(initHeight/5));
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
		box.setMinWidth(initWidth/4);
		box.setMinHeight(initHeight * 0.6);
		box.setMaxWidth(initWidth/4);
		box.setMaxHeight(initHeight * 0.6);


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
		return logicalFactory.createVariable(getIndentFactor(), name.getText(), ((GraphicalBlock) nestBoxes[0].getChildren().get(0)).getLogicalBlock());
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalVariableBlock(initWidth, initHeight);
	}


}
