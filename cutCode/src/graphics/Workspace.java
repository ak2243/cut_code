package graphics;

import cutcode.BSTree;
import cutcode.Sequence;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Workspace extends BorderPane {

	private BSTree<Sequence<GraphicalBlock>> chains;

	public Workspace() {
		VBox palette = setupPalette();
		this.setLeft(palette);
	}

	public VBox setupPalette() {
		VBox palette = new VBox();
		palette.setSpacing(40);
		palette.setPadding(new Insets(30));
		palette.setMinWidth(200);
		palette.setBackground(
				new Background(new BackgroundFill(Color.rgb(255, 10, 10, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
		GraphicalBlock[] paletteBlocks = { new IfBlock(), new DoubleBlock() };
		for (GraphicalBlock b : paletteBlocks) {
			
			MouseHandler handler = new MouseHandler(b);
			b.addEventHandler(MouseEvent.ANY, handler);
			
			palette.getChildren().add(b);
		}

		return palette;
	}

	private class MouseHandler implements EventHandler<MouseEvent> {

		double offsetX;
		double offsetY;
		GraphicalBlock block;
		
		GraphicalBlock current;
		
		public MouseHandler(GraphicalBlock b) {
			block = b;
		}

		@Override
		public void handle(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
				System.out.println("hafj");
				GraphicalBlock add = block.cloneBlock();
				//add.setPrefWidth(200);
				//add.setPrefHeight(40);
				Workspace.this.getChildren().add(add);
				double mouseX = e.getSceneX();
				double mouseY = e.getSceneY();

				double blockX = block.getLayoutX();
				double blockY = block.getLayoutY();

				offsetX = mouseX - blockX;
				offsetY = mouseY - blockY;

				add.setLayoutX(blockX);
				add.setLayoutY(blockY);
				current = add;
			}else if(e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				current.setLayoutX(e.getSceneX() - offsetX);
				current.setLayoutY(e.getSceneY() - offsetY);
			}
		}

	}

}
