package application;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class LayoutView extends Pane {

	private AnchorPane playArea;
	private VBox blockStorage;
	private ArrayList<BlockView> blocks;
	private BorderPane layout;
	private BlockView firstBlock;

	public LayoutView(double width, double height) {

		layout = new BorderPane();
		layout.setPadding(new Insets(10));
		this.getChildren().add(layout);

		playArea = new AnchorPane();
		playArea.setMinSize(width - 500, height);
		System.err.println(width);
		layout.setCenter(playArea);

		blockStorage = makePalette();

		layout.setLeft(blockStorage);

		blocks = new ArrayList<BlockView>();

		blockStorage.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		Button run = new Button("RUN");
		run.setOnMousePressed(new RunListener());
		run.setMinSize(80, 50);
		run.setAlignment(Pos.BOTTOM_LEFT);
		layout.setRight(run);
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
				
				current = new BlockView(300, 50, id, false);
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
				for(BlockView b : blocks) {
					if(b.getNextBlock() == null && (Math.pow(current.getLayoutX() - b.getLayoutX(),2) + Math.pow(current.getLayoutY() - (b.getLayoutY() + b.getHeight()), 2) < 1600)) {
						current.setLayoutX(b.getLayoutX());
						current.setLayoutY(b.getLayoutY() + b.getHeight());
						b.setNextBlock(current);
						current.setBlockAbove(b);
					}
				}
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
				if(block.getNextBlock() != null) block.getNextBlock().setBlockAbove(null);
				block.setNextBlock(null);
				if(block.getBlockAbove() != null) block.getBlockAbove().setNextBlock(null);
				block.setBlockAbove(null);
				
			} else if (type.equals(MouseEvent.MOUSE_DRAGGED)) {

				this.block.setLayoutX(this.block.getLayoutX() + event.getX() - anchorX);
				this.block.setLayoutY(this.block.getLayoutY() + event.getY() - anchorY);
			} else if (type.equals(MouseEvent.MOUSE_RELEASED)) {
				anchorX = anchorY = 0;
				for(BlockView b : blocks) {
					if(b.getNextBlock() == null && (Math.pow(block.getLayoutX() - b.getLayoutX(),2) + Math.pow(block.getLayoutY() - (b.getLayoutY() + b.getHeight()), 2) < 1600)) {
						block.setLayoutX(b.getLayoutX());
						block.setLayoutY(b.getLayoutY() + b.getHeight());
						b.setNextBlock(block);
						block.setBlockAbove(b);
					}
				}
			}
		}
	}

	private VBox makePalette() {
		VBox box = new VBox();
		box.setSpacing(20);
		box.setPadding(new Insets(30));
		ArrayList<BlockView> paletteBlocks = new ArrayList<BlockView>();
		paletteBlocks.add(new BlockView(300, 50, "variable", true));
		paletteBlocks.add(new BlockView(300, 50, "print", true));
		paletteBlocks.add(new BlockView(300, 50, "if", true));
		paletteBlocks.add(new BlockView(300, 50, "Change_Variable", true));
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

	private class RunListener implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent event)
		{
			Builder builder = new Builder();
			for(BlockView b : blocks)
			{
				if(b.getId().equals("print"))
				{
					String input = ((TextField)(b.getChildren().get(1))).getText();
					builder.print(input);
				}
				else if(b.getId().equals("variable"))
				{
					String value = ((TextField)(b.getChildren().get(3))).getText();
					String name = ((TextField)(b.getChildren().get(1))).getText();
					builder.createVariable(name, value);
				}
				else if (b.getId().equals("if"))
				{
					String operand = "";
				}
			}
			String s = builder.run();
			Stage stage = new Stage();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			Label console = new Label(s);
			root.setCenter(console);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}

		
	}
}
