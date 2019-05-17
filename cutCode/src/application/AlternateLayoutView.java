package application;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class AlternateLayoutView extends Pane {

	private AnchorPane playArea;
	private VBox blockStorage;
	private ArrayList<BlockView> blocks;
	private BorderPane layout;

	public AlternateLayoutView(double width, double height) {

		layout = new BorderPane();
		layout.setPadding(new Insets(10));
		this.getChildren().add(layout);

		playArea = new AnchorPane();
		playArea.setMinSize(width - 100, height);
		System.err.println(width);
		layout.setCenter(playArea);

		blockStorage = makePalette();

		layout.setLeft(blockStorage);

		blocks = new ArrayList<BlockView>();

		blockStorage.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		/*Button run = new Button("RUN");
		layout.setRight(run);*/
	}

	private class Mouser implements EventHandler<MouseEvent> {
		private BlockView current;
		private Pane layout;
		private String id;

		public Mouser(Pane layout, String id) {
			this.layout = layout;
			this.id = id;
		}

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub

			EventType<? extends MouseEvent> type = event.getEventType();
			if (type.equals(MouseEvent.MOUSE_PRESSED)) {

				double mouseX = event.getSceneX();
				double mouseY = event.getSceneY();
				
				current = new BlockView(100, 50, id);
				layout.getChildren().add(current);
				current.setLayoutX(mouseX - (current.getMinWidth() / 2));
				current.setLayoutY(mouseY - (current.getMinHeight() / 2));

			} else if (type.equals(MouseEvent.MOUSE_DRAGGED)) {
				current.setLayoutX(event.getSceneX() - (current.getWidth() / 2));
				current.setLayoutY(event.getSceneY() - (current.getHeight() / 2));

			} else if (type.equals(MouseEvent.MOUSE_RELEASED)) {
				BlockHandler handler = new BlockHandler(current);
				current.setOnMousePressed(handler);
				current.setOnMouseDragged(handler);
				current.setOnMouseReleased(handler);
				blocks.add(current);
				current = null;
			}
		}
	}

	private class BlockHandler implements EventHandler<MouseEvent> {

		private BlockView block;
		private double anchorX, anchorY;

		public BlockHandler(BlockView block) {
			this.block = block;
		}

		@Override
		public void handle(MouseEvent event) {
			EventType<? extends MouseEvent> type = event.getEventType();
			if (type.equals(MouseEvent.MOUSE_PRESSED)) {
				anchorX = event.getX();
				anchorY = event.getY();
			} else if (type.equals(MouseEvent.MOUSE_DRAGGED)) {

				this.block.setLayoutX(this.block.getLayoutX() + event.getX() - anchorX);
				this.block.setLayoutY(this.block.getLayoutY() + event.getY() - anchorY);
				System.err.println("(" + event.getX() + ", " + event.getY());
			} else if (type.equals(MouseEvent.MOUSE_RELEASED)) {
				anchorX = anchorY = 0;
			}
		}
	}

	private VBox makePalette() {
		VBox box = new VBox();
		box.setSpacing(20);
		box.setPadding(new Insets(30));
		ArrayList<BlockView> paletteBlocks = new ArrayList<BlockView>();
		paletteBlocks.add(new BlockView(100, 50, "variable"));
		paletteBlocks.add(new BlockView(100, 50, "print"));
		paletteBlocks.add(new BlockView(100, 50, "if"));
		paletteBlocks.add(new BlockView(100, 50, "operator"));
		box.setMaxSize(160, 320);
		for (BlockView block : paletteBlocks) {
			Mouser mouser = new Mouser(this, block.getId());
			block.setOnMousePressed(mouser);
			block.setOnMouseDragged(mouser);
			block.setOnMouseReleased(mouser);
			box.getChildren().add(block);
		}

		return box;
	}
}
