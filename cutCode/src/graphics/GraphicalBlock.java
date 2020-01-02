package graphics;

import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;

import logicalBlocks.Block;

public abstract class GraphicalBlock extends VBox {

	private Sequence<GraphicalBlock> sequence;

	public GraphicalBlock() {
		super();
	}

	public GraphicalBlock(double width, double height) {
		setMinWidth(width);
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
	
	/**
	 * 
	 * @return - the json representation of this block 
	 */
	public abstract String toJSON();

	public void setSequence(Sequence<GraphicalBlock> sequence) {
		this.sequence = sequence;
	}

	public void tagErrorOnBlock() {
		String cssLayout = "-fx-border-color: red;\n" + "-fx-border-width: 3;\n" + "-fx-border-style: dashed;\n";
		this.setStyle(cssLayout);
	}

}
