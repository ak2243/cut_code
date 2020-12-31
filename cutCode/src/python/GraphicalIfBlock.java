package python;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import cutcode.InvalidNestException;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphicalIfBlock extends GraphicalBlock {
	private GraphicalBooleanBinaryOperatorBlock condition; // TODO make this OperatorBlock
	private VBox[] nestBoxes;
	private HashMap<VBox, double[]> nestDimensions;
	private double initWidth, initHeight;

	@Override 
	public VBox[] getNestBoxes() {
		return nestBoxes;
	}

	

	public GraphicalIfBlock(double width, double height) {
		super(width, height);
		this.initWidth = width;
		this.initHeight = height;

		nestDimensions = new HashMap<>();

		this.setPadding(new Insets(height / 5));
		this.setBackground(new Background(new BackgroundFill(Color.web("#907FDE"), CornerRadii.EMPTY, Insets.EMPTY)));

		HBox topLine = new HBox();
		topLine.setSpacing(height / 5);
		Label label = new Label("if");
	    label.setTextFill(Color.WHITE);
		topLine.getChildren().add(label);
		VBox bottomLine = new VBox();
		bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		bottomLine.setMinWidth(initWidth * 0.88);
		bottomLine.setMaxWidth(bottomLine.getMinWidth());
		bottomLine.setMinHeight(initHeight / 3);
		bottomLine.setMaxHeight(bottomLine.getMinHeight());
		double[] bottomLineDimensions = { bottomLine.getMinWidth(), bottomLine.getMinHeight() };
		nestDimensions.put(bottomLine, bottomLineDimensions);
		VBox conditionSpace = new VBox();
		conditionSpace.setMinHeight(initHeight / 3);
		conditionSpace.setMaxHeight(conditionSpace.getMinHeight());
		conditionSpace.setMinWidth(initWidth * 0.68);
		conditionSpace.setMaxWidth(conditionSpace.getMinWidth());
		double[] conditionSpaceDimensions = { conditionSpace.getMinWidth(), conditionSpace.getMinHeight() };
		nestDimensions.put(conditionSpace, conditionSpaceDimensions);
		conditionSpace.setBackground(
				new Background(new BackgroundFill(Color.web("#E6E6E6"), CornerRadii.EMPTY, Insets.EMPTY)));

		topLine.getChildren().add(conditionSpace);

		this.getChildren().addAll(topLine, bottomLine);

		nestBoxes = new VBox[2];
		nestBoxes[0] = conditionSpace;
		nestBoxes[1] = bottomLine;

	}

	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		if (nestBoxes[0].getChildren().size() != 1) {
			tagErrorOnBlock();
			throw new BlockCodeCompilerErrorException();
		}
		ArrayList<LogicalBlock> executeBlocks = new ArrayList<>();
		for (Node n : nestBoxes[1].getChildren()) { // gets all the blocks to be executed if the if statement evaluates
													// to true
			((GraphicalBlock) n).setIndentFactor(getIndentFactor() + 1);
			executeBlocks.add(((GraphicalBlock) n).getLogicalBlock());
		}
		return logicalFactory.createIf(getIndentFactor(),
				((GraphicalBlock) nestBoxes[0].getChildren().get(0)).getLogicalBlock(), executeBlocks);
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalIfBlock(initWidth, initHeight);
	}

	/**
	 * @return an array of 2 points, the top left of the slots of the operand
	 *         spaces, regardless of whether something is already nested there or
	 *         not.
	 */
	@Override
	public Point2D[] getNestables() {
		Point2D[] ret = new Point2D[nestBoxes.length];
		ret[0] = nestBoxes[0].localToScene(nestBoxes[0].getLayoutBounds().getMinX(),
				nestBoxes[0].getLayoutBounds().getMinY());
		double secondaryIncrementY = 0;
		for (Node n : nestBoxes[1].getChildren())
			secondaryIncrementY += ((GraphicalBlock) n).getHeight();
		ret[1] = nestBoxes[1].localToScene(nestBoxes[1].getLayoutBounds().getMinX(),
				nestBoxes[0].getLayoutBounds().getMinY() + secondaryIncrementY);
		return ret;
	}

	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {

		if (index == 0) {
			VBox box = nestBoxes[0];
			if (nestBoxes[index].getChildren().size() != 0)
				throw new InvalidNestException();
			increment(box, nest);
			box.getChildren().add(nest);

		} else if (index == 1) {
			VBox box = nestBoxes[1];

			increment(box, nest);
			box.getChildren().add(nest);
		} else
			throw new InvalidNestException();
		nest.setNestedIn(this);
	}

	@Override
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
		// resets sizes of block and fields
		box.getChildren().remove(rem);
		double[] dimensions = nestDimensions.get(box);
		if (dimensions != null && dimensions.length == 2) {
			rem.minHeightProperty().removeListener(super.heightListeners.get(rem));
			rem.minWidthProperty().removeListener(super.widthListeners.get(rem));
			super.heightListeners.remove(rem);
			super.widthListeners.remove(rem);

			box.getChildren().remove(rem);
			double boxHeight = box.getMaxHeight();
			if (box.getChildren().size() > 0) {
				double newBoxWidth = 0;
				for (Node n : box.getChildren()) {
					GraphicalBlock b = (GraphicalBlock) n;
					if (b.getMaxWidth() > newBoxWidth) {
						newBoxWidth = b.getMaxWidth();
					}
				}
				
				double deltaWidth = box.getMaxWidth() - newBoxWidth;
				double deltaHeight = rem.getMinHeight(); // no need for subtraction since there's more than one nested
															// block
				double farthestOut = 0;
				for(VBox b : this.nestBoxes) {
					double farOut = b.getMaxWidth() + b.getLayoutX();
					if(farOut > farthestOut) {
						farthestOut = farOut;
					}
				}
				
				box.setMaxWidth(newBoxWidth);
				box.setMinWidth(box.getMaxWidth());
				box.setMaxHeight(boxHeight - rem.getMinHeight());
				box.setMinHeight(box.getMaxHeight());
				this.setSize(this.getMaxWidth() - deltaWidth, this.getMinHeight() - deltaHeight);
			} else {
				double newWidth = this.getMinWidth() - (box.getMaxWidth() - this.nestDimensions.get(box)[0]);
				double newHeight = this.getMinHeight() - (box.getMaxHeight() - this.nestDimensions.get(box)[1]);
				box.setMaxWidth(this.nestDimensions.get(box)[0]);
				box.setMinWidth(box.getMaxWidth());
				box.setMaxHeight(this.nestDimensions.get(box)[1]);
				box.setMinHeight(box.getMaxHeight());
				this.setSize(newWidth, newHeight);
			}
		}
	}

	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		lineLocations.put(getLineNumber(), this);
		int ret = getLineNumber() + 1;
		for (Node n : nestBoxes[1].getChildren()) {
			if (n instanceof GraphicalBlock) {
				((GraphicalBlock) n).setLineNumber(ret);
				ret = ((GraphicalBlock) n).putInHashMap(lineLocations);
			}
		}
		return ret + logicalFactory.getEndingBrace(); // Block class reused for java so might have ending brace
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
