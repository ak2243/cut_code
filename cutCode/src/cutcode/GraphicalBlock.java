package cutcode;

import factories.LogicalFactory;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class GraphicalBlock extends VBox implements Comparable<GraphicalBlock> {
	private GraphicalBlock below;
	private GraphicalBlock above;
	private GraphicalBlock nestedIn;
	protected LogicalFactory logicalFactory;
	private boolean ignoreNext;
	private int lineNumber;
	private int indentFactor;

	public int getIndentFactor() {
		return indentFactor + logicalFactory.getBaseIndent();
	}

	public void setIndentFactor(int indentFactor) {
		this.indentFactor = indentFactor;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int line) {
		lineNumber = line;
	}

	public void setLogicalFactory(LogicalFactory logicalFactory) {
		this.logicalFactory = logicalFactory;
	}

	public ArrayList<GraphicalBlock> getChildBlocks() {
		return null;
	}

	public boolean ignoreStatus() {
		return ignoreNext;
	}

	public void ignoreNextAction() {
		ignoreNext = true;
	}

	public void actionIgnored() {
		ignoreNext = false;
	}

	@Override
	public int compareTo(GraphicalBlock other) {
		int compare = Double.compare(this.getLayoutY(), other.getLayoutY());
		if (compare == 0)
			return Double.compare(this.getLayoutX(), other.getLayoutX());
		else
			return compare;
	}

	public GraphicalBlock(double width, double height) {
		this.setSize(width, height);
	}
	
	protected void setSize(double width, double height) {
		this.maxHeightProperty().set(height);
		this.maxWidthProperty().set(width);
		this.minHeightProperty().set(height);
		this.minWidthProperty().set(width);
	}

	/**
	 * @param nestedIn the block that this is nested in
	 */
	public void setNestedIn(GraphicalBlock nestedIn) {
		this.nestedIn = nestedIn;
	}

	/**
	 * @return the block that this block is nested in
	 */
	public GraphicalBlock getNestedIn() {
		return nestedIn;
	}

	/**
	 * @param b the block that is bound to this block (b is below)
	 */
	public void setBelow(GraphicalBlock b) {
		below = b;
	}

	/**
	 * @return the block that is bound to this block (below)
	 */
	public GraphicalBlock getBelow() {
		return below;
	}

	/**
	 * @param b the block this block is bound to (b is above)
	 */
	public void setAbove(GraphicalBlock b) {
		above = b;
		this.layoutXProperty().unbind();
		this.layoutYProperty().unbind();
		if (b != null) {
			this.layoutXProperty().bind(b.layoutXProperty());
			this.layoutYProperty().bind(b.layoutYProperty().add(b.getMaxHeight()));
		}
	}

	/**
	 * @return the block this block is bound to (above)
	 */
	public GraphicalBlock getAbove() {
		return above;
	}

	/**
	 * @return the logical block object for the graphical block
	 */
	public abstract LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException;

	public abstract GraphicalBlock cloneBlock();

	public void tagErrorOnBlock() {
		String cssLayout = "-fx-border-color: white;\n" + "-fx-border-width: 5;\n" + "-fx-border-style: dashed;\n";
		this.setStyle(cssLayout);
	}

	public void untag() {
		this.setStyle(null);
	}

	/**
	 * @return an array of points where a graphical block can nest
	 */
	public Point2D[] getNestables() {
		return new Point2D[0];
	}

	/**
	 * @param index the index from getNestables() to which the given block must be
	 *              nested
	 * @param nest  the block to be nested in the index
	 * @throws InvalidNestException - thrown if the index does not correspond to a
	 *                              valid nest location
	 */
	public abstract void nest(int index, GraphicalBlock nest) throws InvalidNestException;

	/**
	 *
	 * @param box the box from which rem is being removed
	 * @param rem the block that is being unnested
	 * @throws InvalidNestException if this block does not contain a nested block
	 *                              rem
	 */
	public abstract void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException;

	/**
	 * @param incrementBox    - the box to be incremented
	 * @param heightIncrement - the increase in height
	 * @param widthIncrement  - the increase in width
	 */
	public void increment(VBox box, GraphicalBlock nest) {
		if (nest == null)
			throw new NullPointerException();
		double incHeight = this.minHeightProperty().get() - box.maxHeightProperty().get();
		double incWidth = this.minWidthProperty().get() - box.maxWidthProperty().get();

		// Step 1: change dimensions of box
		box.minWidthProperty().bind(nest.minWidthProperty());
		box.maxWidthProperty().bind(nest.maxWidthProperty());
		box.minHeightProperty().bind(nest.minHeightProperty());
		box.maxHeightProperty().bind(nest.maxHeightProperty());

		// Step 2: change dimensions of this block
		this.minWidthProperty().bind(nest.minWidthProperty().add(incWidth));
		this.maxWidthProperty().bind(nest.minWidthProperty().add(incWidth));
		this.minHeightProperty().bind(nest.minHeightProperty().add(incHeight));
		this.maxHeightProperty().bind(nest.minHeightProperty().add(incHeight));
		


		if (this.getBelow() != null)
			this.getBelow().setAbove(this);

	}

	/**
	 *
	 * @param lineLocations the hashmap to put the line number and Graphical Block
	 * @return the integer for the line number of the next block. -1 if the block
	 *         isn't an independent line
	 */
	public abstract int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations);

}