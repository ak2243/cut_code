package graphics;

import javafx.scene.layout.VBox;

import logicalBlocks.Block;

public abstract class GraphicalBlock extends VBox {
	
	public GraphicalBlock() {
		super();
	}
	
	public GraphicalBlock(double width, double height) {
		setMinHeight(height);
		setMinWidth(width);
		setMaxHeight(height);
		setMaxWidth(width);
		System.err.println("hello" + getMaxWidth() + ", " + getMaxHeight());
	}
	
	/**
	 * 
	 * @return the logical block object for the graphical block
	 */
	public abstract Block getLogicalBlock();
	
	public abstract GraphicalBlock cloneBlock();
	
}
