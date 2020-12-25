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

public class GraphicalElseBlock extends GraphicalBlock {
	private VBox[] nestBoxes;
	private HashMap<VBox, double[]> nestDimensions;

	public GraphicalElseBlock() { //sets up visuals of the block
		super(200, 80);

		nestDimensions = new HashMap<>();

		this.setPadding(new Insets(10));
		this.setBackground(new Background(new BackgroundFill(Color.web("#8079D8"), CornerRadii.EMPTY, Insets.EMPTY)));


		HBox topLine = new HBox();
		topLine.getChildren().addAll(new Label("else"));
		VBox bottomLine = new VBox();
		bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		bottomLine.setMinWidth(140);
		bottomLine.setMinHeight(40);
		double[] bottomLineDimensions = {40.0, 40.0};
		nestDimensions.put(bottomLine, bottomLineDimensions);
		this.getChildren().addAll(topLine, bottomLine);

		nestBoxes = new VBox[1];
		nestBoxes[0] = bottomLine;
	}

	public GraphicalElseBlock(double width, double height) {
		super(width, height);
	}

	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		ArrayList<LogicalBlock> executeBlocks = new ArrayList<>();
		for(Node n : nestBoxes[0].getChildren()) { //gets all the blocks to be executed if the if statement evaluates to true
			((GraphicalBlock) n).setIndentFactor(getIndentFactor() + 1);
			executeBlocks.add(((GraphicalBlock) n).getLogicalBlock());
		}
		return logicalFactory.createElseBlock(getIndentFactor(), executeBlocks);
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new python.GraphicalElseBlock();
	}

	/**
	 * @return an array of 2 points, the top left of the slots of the operand spaces, regardless of whether something
	 * is already nested there or not.
	 */
	@Override
	public Point2D[] getNestables() {
		Point2D[] ret = new Point2D[nestBoxes.length];
		double secondaryIncrementY = 0; //need to account for blocks already nested (makes it bottom right)
		for (Node n : nestBoxes[0].getChildren())
			secondaryIncrementY += ((GraphicalBlock) n).getHeight();
		ret[0] = nestBoxes[0].localToScene(nestBoxes[0].getLayoutBounds().getMinX(), nestBoxes[0].getLayoutBounds().getMinY() + secondaryIncrementY);
		return ret;
	}


	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		if (index == 0) {
			VBox box = nestBoxes[0];
			increment(box, nest);
			box.getChildren().add(nest);
		} else
			throw new InvalidNestException();
		nest.setNestedIn(this);
	}


	@Override
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException { //removes block from nest field and resets sizes of block and fields
		box.getChildren().remove(rem);
		double[] dimensions = nestDimensions.get(box);
		if (dimensions != null && dimensions.length == 2) {
			box.setMinWidth(dimensions[0]);
			box.setMinHeight(dimensions[1]);
		}
		if (nestBoxes[0].getChildren().size() == 0) {
			this.setMinWidth(200);
			this.setMinHeight(80);
			this.setMaxWidth(200);
			this.setMaxHeight(80);
		}
	}

	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		lineLocations.put(getLineNumber(), this);
		int ret = getLineNumber() + 1;
		for(Node n : nestBoxes[0].getChildren()) {
			if(n instanceof GraphicalBlock) {
				((GraphicalBlock) n).setLineNumber(ret);
				ret = ((GraphicalBlock) n).putInHashMap(lineLocations);
			}
		}
		return ret + logicalFactory.getEndingBrace(); ////Block class reused for java so might have ending brace
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
