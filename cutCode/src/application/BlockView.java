package application;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.geometry.*;

public class BlockView extends Button {
	public BlockView(int width, int height, String ID) {
		// Paint p = new Paint();
		// this.setFill(p);
		this.setId(ID);
		this.setMinHeight(height);
		this.setMinWidth(width);
		this.setText(ID);
	}
}
