package cutcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import factories.LogicalFactory;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

public abstract class GraphicalBlock extends VBox implements Comparable<GraphicalBlock> {

	private GraphicalBlock above;
	protected boolean allowBind;
	private GraphicalBlock below;
	protected ChangeListener bindListener;
	private boolean ignoreNext;
	protected int indentFactor;
	private int lineNumber;
	protected LogicalFactory logicalFactory;
	private GraphicalBlock nestedIn;
	protected HashMap<GraphicalBlock, ChangeListener> widthListeners, heightListeners;

	public GraphicalBlock(double width, double height) {
		this.setSize(width, height);
		this.widthListeners = new HashMap<GraphicalBlock, ChangeListener>();
		this.heightListeners = new HashMap<GraphicalBlock, ChangeListener>();
		allowBind = true; // blocks must explicitly set this to false after this constructor is called as
							// the vast majority of blocks allow binding
	}

	/**
	 * Resets the action ignore value, meaning the block will once again process
	 * mouse events like normal
	 */
	public void actionIgnored() {
		ignoreNext = false;
	}

	/**
	 * 
	 * Attach a block to the one above it
	 * 
	 * @param b the block to which this block is binding
	 */
	public void bindTo(GraphicalBlock above) {
		if (above == null) { // effectively the same as unbinding
			this.unbind();
			this.above = null;
			return;
		} else if (!above.allowBind || !this.allowBind) { // some blocks cannot have things bound to them. these blocks
															// have
			// rounded edges
			this.setLayoutX(this.getLayoutX() + this.getMaxHeight() / 4);
			this.setLayoutY(this.getLayoutY() + this.getMaxHeight() / 4);
			cutcode.OutputView.output(
					"Certain blocks cannot be attached to others or have other blocks attached to them. These blocks are indicated by rounded edges.",
					new Stage(), this.getMinWidth() * 1.5, this.getMinHeight() * 1.5);
			// TODO: replace pop-up window with a less intrusive alert in a later version
			return;
		}
		this.above = above;
		above.below = this;
		this.layoutXProperty().unbind();
		this.layoutYProperty().unbind();
		this.layoutXProperty().bind(above.layoutXProperty());
		this.layoutYProperty().bind(above.layoutYProperty().add(above.maxHeightProperty().get()));
		this.bindListener = new BindListener();
		above.maxHeightProperty().addListener(bindListener);

	}

	/**
	 * @return a new block of the same type
	 */
	public abstract GraphicalBlock cloneBlock();

	@Override
	public int compareTo(GraphicalBlock other) { // used in sorting
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

	/**
	 * 
	 * @return an list of all the graphical blocks nested inside this one.
	 */
	public ArrayList<GraphicalBlock> getChildBlocks() {
		return null;
	}

	/**
	 * 
	 * @return the amount by which this block is indented in syntactical code
	 */
	public int getIndentFactor() {
		return indentFactor + logicalFactory.getBaseIndent();
	}

	/**
	 * 
	 * @return the list of nest boxes this block has for which the contents should
	 *         be independent blocks. null if none
	 */
	public abstract VBox[] getIndependentNestBoxes();

	/**
	 * 
	 * @return the line number associated with this graphical block
	 */
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

	public abstract VBox[] getNestBoxes();

	/**
	 * @return the block in which this block is nested
	 */
	public GraphicalBlock getNestedIn() {
		return nestedIn;
	}

	/**
	 * sets the ignore next action value to true
	 */
	public void ignoreNextAction() {
		ignoreNext = true;
	}

	/**
	 * 
	 * @return the ignore next action value
	 */
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
		double incWidth = nest.maxWidthProperty().get() - box.getMaxWidth();
		double incHeight = nest.getMaxHeight();
		if (incWidth < 0 && box.getChildren().size() > 0) { // possible when a block is already nested
			incWidth = 0;
		}
		if (box.getChildren().size() == 0)
			incHeight -= box.getMaxHeight();

		// STEP 1 - change dimensions of box
		box.minWidthProperty().set(box.maxWidthProperty().get() + incWidth);
		box.maxWidthProperty().set(box.maxWidthProperty().get() + incWidth);
		box.minHeightProperty().set(box.maxHeightProperty().get() + incHeight);
		box.maxHeightProperty().set(box.maxHeightProperty().get() + incHeight);

		// STEP 2 - change dimensions of this block
		this.setSize(this.minWidthProperty().get() + incWidth, this.minHeightProperty().get() + incHeight);

		// STEP 3 - add listeners so that change in nested block's dimensions affect
		// changes in this block as well
		ChangeListener widthListener = new DeltaWidthListener(box, nest);
		nest.minWidthProperty().addListener(widthListener);

		ChangeListener heightListener = new DeltaHeightListener(box);
		nest.minHeightProperty().addListener(heightListener);

		// STEP 3.5 - put these listeners in a hashmap so they can be removed when the
		// block is unnested
		widthListeners.put(nest, widthListener);
		heightListeners.put(nest, heightListener);

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
	 * @deprecated
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
	 * @deprecated
	 * @param b the block that is bound to this block (b is below)
	 */
	public void setBelow(GraphicalBlock b) {
		below = b;
	}

	/**
	 * 
	 * @param indentFactor - the amount of times this block should be indented when
	 *                     converted to syntactical code
	 */
	public void setIndentFactor(int indentFactor) {
		this.indentFactor = indentFactor;
	}

	/**
	 * 
	 * @param line - the line number that should be associated with this block
	 */
	public void setLineNumber(int line) {
		lineNumber = line;
	}

	/**
	 * 
	 * @param logicalFactory - the logical factory for the language the user is
	 *                       running
	 */
	public void setLogicalFactory(LogicalFactory logicalFactory) {
		this.logicalFactory = logicalFactory;
	}

	/**
	 * @param nestedIn the block that this is nested in
	 */
	public void setNestedIn(GraphicalBlock nestedIn) {
		this.nestedIn = nestedIn;
	}

	/**
	 * 
	 * @param width  of this block
	 * @param height of this block
	 */
	public void setSize(double width, double height) {
		this.maxHeightProperty().set(height);
		this.maxWidthProperty().set(width);
		this.minHeightProperty().set(height);
		this.minWidthProperty().set(width);
	}

	/**
	 * Adds a white border to this block to indicate a user error
	 */
	public void tagErrorOnBlock() {
		String cssLayout = "-fx-border-color: white;" + System.lineSeparator() + "-fx-border-width: 5;"
				+ System.lineSeparator() + "-fx-border-style: dashed;" + System.lineSeparator();
		this.setStyle(cssLayout);
	}

	/**
	 * 
	 * @return true if the block above this exists and was unbinded. false if there
	 *         is no block below
	 */
	public boolean unbind() {
		GraphicalBlock above = this.above;
		if (above == null)
			return false;
		this.layoutXProperty().unbind();
		this.layoutYProperty().unbind();
		above.maxHeightProperty().removeListener(bindListener);
		bindListener = null;
		this.above = null;
		above.below = null;

		return true;

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

	private class BindListener implements ChangeListener<Double> {

		@Override
		public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
			GraphicalBlock.super.layoutYProperty().unbind();
			GraphicalBlock.super.layoutYProperty().bind(above.layoutYProperty().add(above.maxHeightProperty().get()));
		}

	}

	private class DeltaHeightListener implements ChangeListener<Double> {

		VBox box;

		public DeltaHeightListener(VBox box) {
			this.box = box;
		}

		@Override
		public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
			// height of a nested block changes, need to adjust height of this block
			double deltaHeight = newValue - oldValue;
			box.minHeightProperty().set(box.maxHeightProperty().get() + deltaHeight);
			box.maxHeightProperty().set(box.maxHeightProperty().get() + deltaHeight);
			GraphicalBlock.super.minHeightProperty().set(GraphicalBlock.super.minHeightProperty().get() + deltaHeight);
			GraphicalBlock.super.maxHeightProperty().set(GraphicalBlock.super.minHeightProperty().get());

		}

	}

	private class DeltaWidthListener implements ChangeListener<Double> {

		VBox box;
		GraphicalBlock nest;

		public DeltaWidthListener(VBox box, GraphicalBlock nest) {
			this.box = box;
			this.nest = nest;
		}

		@Override
		public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
			// width of a nested block changes, need to adjust height of this block

			GraphicalBlock biggestWidth = nest;
			for (Node n : box.getChildren()) { // find the widest block in this box
				GraphicalBlock b = (GraphicalBlock) n;
				if (b.getMaxWidth() > biggestWidth.getMaxWidth())
					biggestWidth = b;
			}
			double deltaWidth = biggestWidth.getMinWidth() - box.getMaxWidth(); // 0 if the box is already adjusted to
																				// the widest block
			box.setMinWidth(biggestWidth.getMinWidth());
			box.setMaxWidth(box.getMinWidth());
			if (biggestWidth != nest) // another block is wider than this, no need to change width
				return;
			VBox farthestOut = box;
			for (VBox b : getNestBoxes()) { // find the farthest out box
				if (b.getLayoutX() + b.getMinWidth() > farthestOut.getLayoutX() + farthestOut.getMinWidth())
					farthestOut = b;
			}
			if (farthestOut == box) { // farthest out block is the one affected, need to change block width
				GraphicalBlock.super.setMinWidth(GraphicalBlock.super.getMinWidth() + deltaWidth);
				GraphicalBlock.super.setMaxWidth(GraphicalBlock.super.getMinWidth());
			}
		}

	}
}