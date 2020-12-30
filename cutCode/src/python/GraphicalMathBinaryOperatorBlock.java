package python;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Arjun Khanna
 */
public class GraphicalMathBinaryOperatorBlock extends GraphicalBlock {
	private ComboBox<String> operatorChoice;
	private VBox[] nestBoxes;
	private double initWidth, initHeight;

	@Override 
	public VBox[] getNestBoxes() {
		return nestBoxes;
	}


	public GraphicalMathBinaryOperatorBlock() {
		this(200, 40);
	}

	public GraphicalMathBinaryOperatorBlock(double width, double height) { //sets up visuals of block
		super(width, height);
		this.initWidth = width;
		this.initHeight = height;
		HBox line = new HBox();
		nestBoxes = new VBox[2];
		VBox op1 = new VBox();
		op1.setMaxWidth(initWidth/4);
		op1.setMaxHeight(initHeight - initHeight/3);
		op1.setPrefWidth(initWidth/4);
		op1.setPrefHeight(initHeight - initHeight/3);
		op1.setStyle("-fx-background-color: #E6E6E6");
		VBox op2 = new VBox();
		op2.setMaxWidth(initWidth/4);
		op2.setMaxHeight(initHeight - initHeight/3);
		op2.setPrefWidth(initWidth/4);
		op2.setPrefHeight(initHeight - initHeight/3);
		op2.setStyle("-fx-background-color: #E6E6E6");
		String[] choiceOp = {"+", "-", "÷", "X", "%"}; //drop down options
		operatorChoice = new ComboBox<String>(FXCollections.observableArrayList(FXCollections.observableArrayList(choiceOp)));
		operatorChoice.setValue("+"); //default value is addition
		operatorChoice.setMinWidth((initWidth * 3)/ 8);
		line.getChildren().addAll(op1, operatorChoice, op2);
		this.getChildren().add(line);
		this.setBackground(new Background(new BackgroundFill(Color.web("#B08BE9"), CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPadding(new Insets(initHeight/5));

		nestBoxes[0] = op1;
		nestBoxes[1] = op2;
	}


	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		if(nestBoxes[0].getChildren().size() != 1 || nestBoxes[1].getChildren().size() != 1) {
			tagErrorOnBlock();
			throw new BlockCodeCompilerErrorException();
		}
		return logicalFactory.createBinaryMathOperator(((GraphicalBlock) nestBoxes[0].getChildren().get(0)).getLogicalBlock(), operatorChoice.getValue(), ((GraphicalBlock) nestBoxes[1].getChildren().get(0)).getLogicalBlock());
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalMathBinaryOperatorBlock(this.initWidth, this.initHeight);
	}

	/**
	 * @return an array of 2 points, the top left of the slots of the operand spaces, regardless of whether something
	 * is already nested there or not.
	 */
	@Override
	public Point2D[] getNestables() {
		Point2D[] ret = new Point2D[nestBoxes.length];
		for (int i = 0; i < nestBoxes.length; i++)
			ret[i] = nestBoxes[i].localToScene(nestBoxes[i].getLayoutBounds().getMinX(), nestBoxes[i].getLayoutBounds().getMinY());
		return ret;
	}


	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		if(nestBoxes[index].getChildren().size() != 0)
			throw new InvalidNestException();
		try {
			VBox box = nestBoxes[index];
			double incrementWidth = nest.getWidth() - box.getWidth();
			double incrementHeight = nest.getHeight() - box.getHeight();
			increment(box, nest);
			box.getChildren().add(nest);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidNestException();
		}
		nest.setNestedIn(this);
	}


	@Override
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
		if(box == null || rem == null)
			throw new InvalidNestException();
		
		rem.minHeightProperty().removeListener(super.heightListeners.get(rem));
		rem.minWidthProperty().removeListener(super.widthListeners.get(rem));
		super.heightListeners.remove(rem);
		super.widthListeners.remove(rem);

		
		box.getChildren().remove(rem);
		//reset box size (only 1 nest in these boxes)
		box.setMaxWidth(initWidth/4);
		box.setMaxHeight(initHeight - initHeight/3);
		box.setMinWidth(initWidth/4);
		box.setMinHeight(initHeight - initHeight/3);

		double deltaHeight = rem.getMinHeight() - box.getMaxHeight();
		double deltaWidth = rem.getMinWidth() - box.getMinWidth();
		this.setSize(this.getMinWidth() - deltaWidth, this.getMinHeight() - deltaHeight);
	}

	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		return 0;
	}

	@Override
	public ArrayList<GraphicalBlock> getChildBlocks() {
		ArrayList<GraphicalBlock> ret = new ArrayList<>();
		for(VBox box : nestBoxes) {
			for(Node b : box.getChildren()) {
				if(b instanceof GraphicalBlock) {
					ret.add((GraphicalBlock) b);
				}
			}
		}
		return ret; //No new line because not used independently
	}
}
