package python;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;
import cutcode.LogicalBlock;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GraphicalPrintBlock extends GraphicalBlock {
	private VBox[] nestBoxes;
	private double initWidth, initHeight;
	
	@Override 
	public VBox[] getNestBoxes() {
		return nestBoxes;
	}

	
	public GraphicalPrintBlock(double width, double height) { //sets up visuals of the block
		super(width, height);
		this.initWidth = width;
		this.initHeight = height;
		nestBoxes = new VBox[1];
		HBox firstLine = new HBox();
		firstLine.setSpacing(height/8);
		firstLine.setPadding(new Insets(height/5));
		Label label = new Label("print");
		VBox value = new VBox();
		value.minWidthProperty().set(initWidth - initWidth/3);
		value.minHeightProperty().set(initHeight - initHeight/3);
		value.maxWidthProperty().set(initWidth - initWidth/3);
		value.maxHeightProperty().set(initHeight - initHeight/3);

		value.setStyle("-fx-background-color: #E6E6E6");
		nestBoxes[0] = value;
		firstLine.getChildren().addAll(label, value);
		this.getChildren().add(firstLine);

		this.setBackground(new Background(new BackgroundFill(Color.web("#E09DFA"), CornerRadii.EMPTY, Insets.EMPTY)));

	}

	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		if(nestBoxes[0].getChildren().size() == 0) {
			tagErrorOnBlock();
			throw new BlockCodeCompilerErrorException();
		}
		return logicalFactory.createPrint(getIndentFactor(), ((GraphicalBlock)nestBoxes[0].getChildren().get(0)).getLogicalBlock());
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalPrintBlock(initWidth, initHeight);
	}

	/**
	 * @param lineLocations the hashmap to put the line number and Graphical Block
	 * @return the integer for the line number of the next block. -1 if the block isn't an independent line
	 */
	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		lineLocations.put(getLineNumber(), this);
		return getLineNumber() + 1;
	}

	@Override
	public Point2D[] getNestables() {
		Point2D[] ret = new Point2D[nestBoxes.length];
		for (int i = 0; i < nestBoxes.length; i++)
			ret[i] = nestBoxes[i].localToScene(nestBoxes[i].getLayoutBounds().getMinX(), nestBoxes[i].getLayoutBounds().getMinY());
		return ret;
	}


	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException { //Nest the value to be printed
		if (nestBoxes[index].getChildren().size() != 0 || index != 0)
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
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
		if (box == null || rem == null)
			throw new InvalidNestException();
		rem.minHeightProperty().removeListener(super.heightListeners.get(rem));
		rem.minWidthProperty().removeListener(super.widthListeners.get(rem));
		super.heightListeners.remove(rem);
		super.widthListeners.remove(rem);
		
		box.getChildren().remove(rem);
		
		this.setSize(initWidth, initHeight);
		
		box.minWidthProperty().set(initWidth - initWidth/3);
		box.minHeightProperty().set(initHeight - initHeight/3);
		box.maxWidthProperty().set(initWidth - initWidth/3);
		box.maxHeightProperty().set(initHeight - initHeight/3);


	}



	@Override
	public ArrayList<GraphicalBlock> getChildBlocks() {
		ArrayList<GraphicalBlock> ret = new ArrayList<>();
		for (VBox box : nestBoxes) {
			for (Node b : box.getChildren()) {
				if (b instanceof GraphicalBlock) {
					ret.add((GraphicalBlock) b);
				}
			}
		}
		return ret;
	}

}