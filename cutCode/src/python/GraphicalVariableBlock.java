package python;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphicalVariableBlock extends GraphicalBlock {
	private TextField name;
	private VBox[] nestBoxes; //array for consistent and easily expandable design

	public GraphicalVariableBlock() { //sets up visuals of the block
		super(200, 40);
		nestBoxes = new VBox[1];
		name = new TextField();
		name.setMaxWidth(100);
		VBox value = new VBox();
		value.setMinWidth(50);
		value.setMinHeight(24);
		value.setStyle("-fx-background-color: #E6E6E6");
		nestBoxes[0] = value;
		HBox line = new HBox();
		line.setSpacing(5);
		line.setPadding(new Insets(8));
		line.getChildren().add(name);
		line.getChildren().add(new Label("="));
		line.getChildren().add(value);

		this.setBackground(
				new Background(new BackgroundFill(Color.web("#D097F4"), CornerRadii.EMPTY, Insets.EMPTY)));

		this.getChildren().add(line);
	}

	@Override
	public Point2D[] getNestables() { //returns top left of the nesting
		Point2D[] ret = new Point2D[nestBoxes.length];
		for (int i = 0; i < nestBoxes.length; i++)
			ret[i] = nestBoxes[i].localToScene(nestBoxes[i].getLayoutBounds().getMinX(), nestBoxes[i].getLayoutBounds().getMinY());
		return ret;
	}


	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		if (nestBoxes[index].getChildren().size() != 0) //only nest one block
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
		//Need to reset block and box dimensions (no more blocks because only 1 nested)
		if (box == null || rem == null)
			throw new InvalidNestException();
		box.getChildren().remove(rem);
		box.setMinWidth(50);
		box.setMinHeight(24);
		this.setMinWidth(200);
		this.setMinHeight(40);
		this.setMaxWidth(200);
		this.setMaxHeight(40);

	}

	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		lineLocations.put(getLineNumber(), this);
		return getLineNumber() + 1; //Independent block so new line at the end
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

	/**
	 * O(1)
	 *
	 * @author Arjun Khanna
	 */
	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		if(nestBoxes[0].getChildren().size() != 1) {
			tagErrorOnBlock();
			throw new BlockCodeCompilerErrorException();
		}
		return logicalFactory.createVariable(getIndentFactor(), name.getText(), ((GraphicalBlock) nestBoxes[0].getChildren().get(0)).getLogicalBlock());
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalVariableBlock();
	}


}
