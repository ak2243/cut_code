package graphics;

import java.util.List;

import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.geometry.Point2D;

import logicalBlocks.Block;

public abstract class GraphicalBlock extends VBox implements Comparable<GraphicalBlock> {
	public boolean boundTo;
	private GraphicalBlock bound;
	
	@Override
	public int compareTo(GraphicalBlock other) {
		return Double.compare(this.getLayoutY(), other.getLayoutY());
	}
	

	public GraphicalBlock() {
		super();
		boundTo = false;
	}

	public GraphicalBlock(double width, double height) {
		setMinWidth(width);
		setMinHeight(height);
		boundTo = false;
	}
	
	public void setBound(GraphicalBlock b) {
		bound = b;
	}
	
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
	public abstract void nest(int index) throws InvalidNestException;
}