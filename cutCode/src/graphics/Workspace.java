package graphics;

import cutcode.BSTree;
import cutcode.Sequence;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class Workspace extends BorderPane{
	
	private BSTree<Sequence<GraphicalBlock>> chains;
	
	public Workspace() {
		VBox palette = setupPalette();
		this.setLeft(palette);
	}
	
	public VBox setupPalette() {
		VBox palette = new VBox();
		palette.setSpacing(40);
		palette.setPadding(new Insets(30));
		palette.setMinWidth(100);
		palette.setBackground(new Background(new BackgroundFill(Color.rgb(255,10,10,0.8),
				CornerRadii.EMPTY,Insets.EMPTY)));
		
		return palette;
	}
	
}
