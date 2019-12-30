package graphics;

import javafx.scene.layout.VBox;

import logicalBlocks.Block;

public abstract class GraphicalBlock extends VBox {
	
	private Sequence<GraphicalBlock> sequence;
	
	public GraphicalBlock() {
		super();
	}
	
	public GraphicalBlock(double width, double height) {
		setMinHeight(height);
		setMinWidth(width);
		setMaxHeight(height);
		setMaxWidth(width);
	}
	
	/**
	 * 
	 * @return the logical block object for the graphical block
	 */
	public abstract Block getLogicalBlock();
	
	public abstract GraphicalBlock cloneBlock();

	public Sequence<GraphicalBlock> getSequence() {
		return sequence;
	}

	public void setSequence(Sequence<GraphicalBlock> sequence) {
		this.sequence = sequence;
	}
	
}
