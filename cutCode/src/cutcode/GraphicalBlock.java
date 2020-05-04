package cutcode;

import factories.LogicalFactory;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;

public abstract class GraphicalBlock extends VBox implements Comparable<GraphicalBlock> {
	private GraphicalBlock boundTo;
	private GraphicalBlock bound;
	private GraphicalBlock nestedIn;
	private LogicalFactory logicalFactory;
	private boolean ignoreNext;

	public boolean ignoreStatus() {return ignoreNext;}
	public void ignoreNextAction() {ignoreNext = true;}
	public void actionIgnored() {ignoreNext = false;}
	@Override
	public int compareTo(GraphicalBlock other) {
		int compare = Double.compare(this.getLayoutY(), other.getLayoutY());
		if (compare == 0)
			return Double.compare(this.getLayoutX(), other.getLayoutX());
		else
			return compare;
	}


	public GraphicalBlock() {
		this(200,40);
	}

	public GraphicalBlock(double width, double height) {
		setMinWidth(width);
		setMinHeight(height);
		setMaxWidth(width);
		setMaxHeight(height);
	}

	/**
	 *
	 * @param nestedIn the block that this is nested in
	 */
	public void setNestedIn(GraphicalBlock nestedIn) {
		this.nestedIn = nestedIn;
	}

	/**
	 *
	 * @return the block that this block is nested in
	 */
	public GraphicalBlock getNestedIn() {return nestedIn;}

	/**
	 *
	 * @param b the block that is bound to this block (b is below)
	 */
	public void setBoundTo(GraphicalBlock b) {
		boundTo = b;
	}

	/**
	 *
	 * @return the block that is bound to this block (below)
	 */
	public GraphicalBlock getBoundTo() {return boundTo;}


	/**
	 * 
	 * @param b the block this block is bound to (b is above)
	 */
	public void setBound(GraphicalBlock b) {
		bound = b;
	}
	
	/**
	 * 
	 * @return the block this block is bound to (above)
	 */
	public GraphicalBlock getBound() {return bound;}
	

	/**
	 * 
	 * @return the logical block object for the graphical block
	 */
	public abstract Block getLogicalBlock();

	public abstract GraphicalBlock cloneBlock();
	

	public void tagErrorOnBlock() {
		String cssLayout = "-fx-border-color: white;\n" + "-fx-border-width: 5;\n" + "-fx-border-style: dashed;\n";
		this.setStyle(cssLayout);
	}
	
	public void untag() {
		this.setStyle(null);
	}

	/**
	 *
	 * @return an array of points where a graphical block can nest
	 */
	public Point2D[] getNestables() {return new Point2D[0];}

	/**
	 *
	 * @param index the index from getNestables() to which the given block must be nested
	 * @param nest the block to be nested in the index
	 * @throws InvalidNestException - thrown if the index does not correspond to a valid nest location
	 */
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {throw new InvalidNestException();}
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {throw new InvalidNestException();}

	public double getX() {
		if(this.getNestedIn() == null)
			return this.getLayoutX();
		double x = 0;
		GraphicalBlock curr = this;
		while(curr != null) {
			x += curr.getParent().getLayoutX() + curr.getLayoutX();
			curr = curr.getNestedIn();
		}

		return x;
	}
	public double getY() {
		if(this.getNestedIn() == null)
			return this.getLayoutY();
		double y = 0;
		GraphicalBlock curr = this;
		while(curr != null) {
			y += curr.getParent().getLayoutY() + curr.getLayoutY();
			curr = curr.getNestedIn();
		}
		return y;
	}

}