package python;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.InvalidNestException;
import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * @Deprecated
 */
//Deemed too complicated for the current version of cut code
public class GraphicalBreakBlock extends GraphicalBlock { //Class not in use
	public GraphicalBreakBlock() {
		super(200, 40);
		Label label = new Label("break loop");
		this.getChildren().add(label);
		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(Color.web("#6366B8"), CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	@Override 
	public VBox[] getNestBoxes() {
		return null;
	}

	
	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		return logicalFactory.createBreak(getIndentFactor());
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalBreakBlock();
	}

	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		throw new InvalidNestException();
	}

	@Override
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
		throw new InvalidNestException();
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
}
