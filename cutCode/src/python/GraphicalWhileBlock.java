package python;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphicalWhileBlock extends GraphicalBlock {

	private GraphicalBooleanBinaryOperatorBlock condition; // TODO make this OperatorBlock
	private VBox[] nestBoxes;
	private HashMap<VBox, double[]> nestDimensions;

	public GraphicalWhileBlock() {
		super(200, 80);

		nestDimensions = new HashMap<>();

		this.setPadding(new Insets(10));
		this.setBackground(new Background(new BackgroundFill(Color.web("#6F73D2"), CornerRadii.EMPTY, Insets.EMPTY)));


		HBox topLine = new HBox();
		topLine.getChildren().addAll(new Label("while"));
		VBox bottomLine = new VBox();
		bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		bottomLine.setMinWidth(140);
		bottomLine.setMinHeight(32);
		double[] bottomLineDimensions = {140.0, 32.0};
		nestDimensions.put(bottomLine, bottomLineDimensions);
		VBox conditionSpace = new VBox();
		conditionSpace.setMinHeight(30);
		conditionSpace.setMinWidth(140);
		double[] conditionSpaceDimensions = {140.0, 30.0};
		nestDimensions.put(conditionSpace, conditionSpaceDimensions);
		conditionSpace.setBackground(
				new Background(new BackgroundFill(Color.web("#7D80D7"), CornerRadii.EMPTY, Insets.EMPTY)));
		topLine.getChildren().add(conditionSpace);

		this.getChildren().addAll(topLine, bottomLine);

		nestBoxes = new VBox[2];
		nestBoxes[0] = conditionSpace;
		nestBoxes[1] = bottomLine;
	}

	public GraphicalWhileBlock(double width, double height) {
		super(width, height);
	}

	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		if(nestBoxes[0].getChildren().size() != 1)
			throw new BlockCodeCompilerErrorException();
		ArrayList<LogicalBlock> executeBlocks = new ArrayList<>();
		for(Node n : nestBoxes[1].getChildren()) { //gets all the blocks to be executed if the if statement evaluates to true
			((GraphicalBlock) n).setIndentFactor(indentFactor + 1);
			executeBlocks.add(((GraphicalBlock) n).getLogicalBlock());
		}
		return logicalFactory.createWhileLoop(indentFactor, ((GraphicalBlock) nestBoxes[0].getChildren().get(0)).getLogicalBlock(), executeBlocks);
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalWhileBlock();
	}

	/**
	 * @return an array of 2 points, the top left of the slots of the operand spaces, regardless of whether something
	 * is already nested there or not.
	 */
	@Override
	public Point2D[] getNestables() {
		Point2D[] ret = new Point2D[nestBoxes.length];
		ret[0] = nestBoxes[0].localToScene(nestBoxes[0].getLayoutBounds().getMinX(), nestBoxes[0].getLayoutBounds().getMinY());
		double secondaryIncrementY = 0;
		for (Node n : nestBoxes[1].getChildren())
			secondaryIncrementY += ((GraphicalBlock) n).getHeight();
		ret[1] = nestBoxes[1].localToScene(nestBoxes[1].getLayoutBounds().getMinX(), nestBoxes[0].getLayoutBounds().getMinY() + secondaryIncrementY);
		return ret;
	}


	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		if (index == 0) {
			VBox box = nestBoxes[0];
			if (nestBoxes[index].getChildren().size() != 0)
				throw new InvalidNestException();
			double incrementWidth = nest.getWidth() - box.getWidth();
			double incrementHeight = nest.getHeight() - box.getHeight();
			increment(box, incrementHeight, incrementWidth);
			box.getChildren().add(nest);

		} else if (index == 1) {
			VBox box = nestBoxes[1];
			double incrementWidth = nest.getWidth() - box.getWidth();
			double incrementHeight = nest.getHeight() - box.getHeight();
			if (box.getChildren().size() != 0) {
				for (Node n : box.getChildren()) {
					incrementWidth += ((GraphicalBlock) n).getWidth();
					incrementHeight += ((GraphicalBlock) n).getHeight();
				}
			}
			increment(box, incrementHeight, incrementWidth);
			box.getChildren().add(nest);
		} else
			throw new InvalidNestException();
		nest.setNestedIn(this);
	}


	@Override
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
		box.getChildren().remove(rem);
		double[] dimensions = nestDimensions.get(box);
		if (dimensions != null && dimensions.length == 2) {
			box.setMinWidth(dimensions[0]);
			box.setMinHeight(dimensions[1]);
		}
		if (nestBoxes[0].getChildren().size() == 0 && nestBoxes[1].getChildren().size() == 0) {
			this.setMaxWidth(200);
			this.setMaxHeight(80);
		}
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


