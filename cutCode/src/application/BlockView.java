package application;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import javafx.geometry.*;

public class BlockView extends HBox {
	private BlockView nextBlock;
	private BlockView nestedIn;
	
	
	public BlockView(int width, int height, String ID) {
		// Paint p = new Paint();
		// this.setFill(p);
		this.setId(ID);
		this.setMinHeight(height);
		this.setMinWidth(width);
		
	}

	public BlockView getNextBlock() {
		return nextBlock;
	}

	public void setNextBlock(BlockView nextBlock) {
		this.nextBlock = nextBlock;
	}
}
