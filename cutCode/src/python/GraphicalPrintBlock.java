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

public class GraphicalPrintBlock extends GraphicalBlock {
	private VBox[] nestBoxes;
	public GraphicalPrintBlock() {
		this(200, 40);
	}
	public GraphicalPrintBlock(double width, double height) { //sets up visuals of the block
		super(width, height);
		nestBoxes = new VBox[1];
		HBox firstLine = new HBox();
		firstLine.setSpacing(5);
		firstLine.setPadding(new Insets(8));
		Label label = new Label("print");
		VBox value = new VBox();
		value.setMinWidth(140);
		value.setMinHeight(24);
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
		return new GraphicalPrintBlock();
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
		if (nestBoxes[index].getChildren().size() != 0)
			throw new InvalidNestException();
		try {
			VBox box = nestBoxes[index];
			double incrementWidth = nest.getWidth() - box.getWidth();
			double incrementHeight = nest.getHeight() - box.getHeight();
			increment(box, incrementHeight, incrementWidth);
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
		box.getChildren().remove(rem);
		box.setMaxWidth(140);
		box.setMaxHeight(24);
		this.setMinWidth(200);
		this.setMinHeight(40);
		this.setMaxWidth(200);
		this.setMaxHeight(40);
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