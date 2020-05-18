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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Arjun Khanna
 */
public class GraphicalBooleanBinaryOperatorBlock extends GraphicalBlock {
	private ComboBox<String> operatorChoice;
	private VBox[] nestBoxes;


	public GraphicalBooleanBinaryOperatorBlock() {
		this(200, 40);
	}

	public GraphicalBooleanBinaryOperatorBlock(int width, int height) {
		super(width, height);
		HBox line = new HBox();
		nestBoxes = new VBox[2];
		VBox op1 = new VBox();
		op1.setMinWidth(50);
		op1.setMinHeight(24);
		op1.setStyle("-fx-background-color: #AA90E7");
		VBox op2 = new VBox();
		op2.setMinWidth(50);
		op2.setMinHeight(24);
		op2.setStyle("-fx-background-color: #AA90E7");
		String[] choiceOp = {"or", "and", ">", ">=", "<", "<="};
		operatorChoice = new ComboBox<String>(FXCollections.observableArrayList(FXCollections.observableArrayList(choiceOp)));
		operatorChoice.setValue("or");
		operatorChoice.setMinWidth(75);
		line.getChildren().addAll(op1, operatorChoice, op2);
		this.getChildren().add(line);
		this.setMinWidth(200);
		this.setStyle("-fx-background-color: #A085E4");
		this.setPadding(new Insets(8));

		nestBoxes[0] = op1;
		nestBoxes[1] = op2;
	}


	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		return logicalFactory.createBinaryBooleanOperator(((GraphicalBlock) (nestBoxes[0].getChildren().get(0))).getLogicalBlock(), operatorChoice.getValue(), ((GraphicalBlock) (nestBoxes[1].getChildren().get(0))).getLogicalBlock());
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalBooleanBinaryOperatorBlock();
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
		box.setMinWidth(50);
		box.setMinHeight(30);
		if (nestBoxes[0].getChildren().size() == 0 && nestBoxes[1].getChildren().size() == 0) {
			this.setMinWidth(200);
			this.setMinHeight(40);
			this.setMaxWidth(200);
			this.setMaxHeight(40);
		}
	}

	/**
	 * @param lineLocations the hashmap to put the line number and Graphical Block
	 * @return the integer for the line number of the next block. -1 if the block isn't an independent line
	 */
	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		return 0;
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