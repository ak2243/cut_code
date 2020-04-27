package graphics;

import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import logicalBlocks.Block;

public abstract class GraphicalBlock extends VBox implements Comparable<GraphicalBlock> {
	private GraphicalBlock boundTo;
	private GraphicalBlock bound;

	@Override
	public int compareTo(GraphicalBlock other) {
		return Double.compare(this.getLayoutY(), other.getLayoutY());
	}
	

	public GraphicalBlock() {
		super();
		boundTo = null;
	}

	public GraphicalBlock(double width, double height) {
		setMinWidth(width);
		setMinHeight(height);
		setMaxWidth(width);
		setMaxHeight(height);
		boundTo = null;
	}

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
	
	public abstract Point2D[] getNestables();
	public abstract void nest(int index, GraphicalBlock nest) throws InvalidNestException;
}