package graphics;

import javafx.scene.layout.VBox;
import cutcode.Block;

public abstract class GraphicalBlock extends VBox{
	
	public GraphicalBlock() {
		super();
	}
	
	public GraphicalBlock(double width, double height) {
		this.setMinHeight(height);
		this.setMinWidth(width);
		this.setMaxHeight(height);
		this.setMaxWidth(width);
	}
	
	public abstract Block getLogicalBlock();
	
}
