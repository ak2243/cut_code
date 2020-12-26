package cutcode;

import java.util.ArrayList;
import java.util.HashMap;

import factories.LogicalFactory;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;

public abstract class GraphicalBlock extends VBox implements Comparable<GraphicalBlock> {

	private GraphicalBlock above;
	private GraphicalBlock below;
	private boolean ignoreNext;
	private int indentFactor;
	private int lineNumber;
	protected LogicalFactory logicalFactory;
	private GraphicalBlock nestedIn;
	protected InvalidationListener widthListener, heightListener;


	public GraphicalBlock(double width, double height) {
		this.setSize(width, height);
	}

	public void actionIgnored() {
		ignoreNext = false;
	}

	public abstract GraphicalBlock cloneBlock();

	@Override
	public int compareTo(GraphicalBlock other) {
		int compare = Double.compare(this.getLayoutY(), other.getLayoutY());
		if (compare == 0)
			return Double.compare(this.getLayoutX(), other.getLayoutX());
		else
			return compare;
	}

	/**
	 * @return the block this block is bound to (above)
	 */
	public GraphicalBlock getAbove() {
		return above;
	}

	/**
	 * @return the block that is bound to this block (below)
	 */
	public GraphicalBlock getBelow() {
		return below;
	}

	public ArrayList<GraphicalBlock> getChildBlocks() {
		return null;
	}

	public int getIndentFactor() {
		return indentFactor + logicalFactory.getBaseIndent();
	}

	public int getLineNumber() {
		return lineNumber;
	}
	
	/**
	 * @return the logical block object for the graphical block
	 */
	public abstract LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException;
	

	/**
	 * @return an array of points where a graphical block can nest
	 */
	public Point2D[] getNestables() {
		return new Point2D[0];
	}

	/**
	 * @return the block that this block is nested in
	 */
	public GraphicalBlock getNestedIn() {
		return nestedIn;
	}

	public void ignoreNextAction() {
		ignoreNext = true;
	}

	public boolean ignoreStatus() {
		return ignoreNext;
	}

	/**
	 * @param incrementBox    - the box to be incremented
	 * @param heightIncrement - the increase in height
	 * @param widthIncrement  - the increase in width
	 */
	public void increment(VBox box, GraphicalBlock nest) {
		if (nest == null)
			throw new NullPointerException();

		// STEP 0 - calculate the difference between box size and the nest size
		double incWidth = nest.maxWidthProperty().get() - box.maxWidthProperty().get();
		double incHeight = nest.maxHeightProperty().get() - box.maxHeightProperty().get();

		// STEP 1 - change dimensions of box
		box.minWidthProperty().set(box.maxWidthProperty().get() + incWidth);
		box.maxWidthProperty().set(box.maxWidthProperty().get() + incWidth);
		box.minHeightProperty().set(box.maxHeightProperty().get() + incHeight);
		box.maxHeightProperty().set(box.maxHeightProperty().get() + incHeight);


		// STEP 2 - change dimensions of this block
		this.setSize(this.minWidthProperty().get() + incWidth, this.minHeightProperty().get() + incHeight);

		// STEP 3 - add listeners so that change in nested block's dimensions affect changes in this block as well
		this.widthListener = new DeltaWidthListener(box, nest);
		nest.minWidthProperty().addListener(widthListener);
		
		this.heightListener = new DeltaHeightListener(box, nest);
		nest.minHeightProperty().addListener(heightListener);

		
		if (this.getBelow() != null)
			this.getBelow().setAbove(this);

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
	 * @param lineLocations the hashmap to put the line number and Graphical Block
	 * @return the integer for the line number of the next block. -1 if the block
	 *         isn't an independent line
	 */
	public abstract int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations);

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
	 * @param b the block that is bound to this block (b is below)
	 */
	public void setBelow(GraphicalBlock b) {
		below = b;
	}

	public void setIndentFactor(int indentFactor) {
		this.indentFactor = indentFactor;
	}

	public void setLineNumber(int line) {
		lineNumber = line;
	}

	public void setLogicalFactory(LogicalFactory logicalFactory) {
		this.logicalFactory = logicalFactory;
	}

	/**
	 * @param nestedIn the block that this is nested in
	 */
	public void setNestedIn(GraphicalBlock nestedIn) {
		this.nestedIn = nestedIn;
	}

	public void setSize(double width, double height) {
		this.maxHeightProperty().set(height);
		this.maxWidthProperty().set(width);
		this.minHeightProperty().set(height);
		this.minWidthProperty().set(width);
	}

	public void tagErrorOnBlock() {
		String cssLayout = "-fx-border-color: white;\n" + "-fx-border-width: 5;\n" + "-fx-border-style: dashed;\n";
		this.setStyle(cssLayout);
	}

	/**
	 *
	 * @param box the box from which rem is being removed
	 * @param rem the block that is being unnested
	 * @throws InvalidNestException if this block does not contain a nested block
	 *                              rem
	 */
	public abstract void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException;
	public void untag() {
		this.setStyle(null);
	}
	
	private class DeltaHeightListener implements InvalidationListener {

		VBox box;
		GraphicalBlock nest;
		public DeltaHeightListener(VBox box, GraphicalBlock nest) {
			this.box = box;
			this.nest = nest;
		}
		@Override
		public void invalidated(Observable observable) {
			double deltaHeight = nest.minHeightProperty().get() - box.maxHeightProperty().get();
			box.minHeightProperty().set(box.maxHeightProperty().get() + deltaHeight);
			box.maxHeightProperty().set(box.maxHeightProperty().get() + deltaHeight);
			GraphicalBlock.super.minHeightProperty().set(GraphicalBlock.super.minHeightProperty().get() + deltaHeight);
			GraphicalBlock.super.maxHeightProperty().set(GraphicalBlock.super.minHeightProperty().get() + deltaHeight);
			
		}
		
	}
	private class DeltaWidthListener implements InvalidationListener {

		VBox box;
		GraphicalBlock nest;
		public DeltaWidthListener(VBox box, GraphicalBlock nest) {
			this.box = box;
			this.nest = nest;
		}
		@Override
		public void invalidated(Observable observable) {
			double deltaWidth = nest.minWidthProperty().get() - box.maxWidthProperty().get();
			box.minWidthProperty().set(box.maxWidthProperty().get() + deltaWidth);
			box.maxWidthProperty().set(box.maxWidthProperty().get() + deltaWidth);
			GraphicalBlock.super.minWidthProperty().set(GraphicalBlock.super.minWidthProperty().get() + deltaWidth);
			GraphicalBlock.super.maxWidthProperty().set(GraphicalBlock.super.minWidthProperty().get() + deltaWidth);
			
		}
		
	}
}