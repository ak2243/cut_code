package graphics;

import java.util.List;
import cutcode.BSTree;
import cutcode.BlockCodeCompilerErrorException;
import cutcode.LList;
import cutcode.Main;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Workspace extends Pane {

	private BSTree<GraphicalBlock> blocks;
	private BorderPane layout;
	private VBox palette;
	private ScrollPane paletteScroll;

	public Workspace(double width, double height) {
		blocks = new BSTree<GraphicalBlock>();
		
		
		this.setMinHeight(height);
		this.setMaxHeight(height);
		this.setMinWidth(width);
		this.setMaxWidth(width);
		layout = new BorderPane();
		this.getChildren().add(layout);
		palette = setupPalette();
		palette.setPrefHeight(palette.getHeight() + 100);

		paletteScroll = new ScrollPane();
		paletteScroll.setContent(palette);
		paletteScroll.setMinHeight(this.getMinHeight());
		paletteScroll.setMaxHeight(this.getMaxHeight());
		paletteScroll.setPrefHeight(this.getMaxHeight());
		paletteScroll.setMinWidth(200);

		layout.setLeft(paletteScroll);

		Button run = new Button("RUN");
		run.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Stage stage = new Stage();
				BorderPane root = new BorderPane();

				try {
					root.setCenter(new Label(run()));
					Scene scene = new Scene(root, 400, 400);
					stage.setScene(scene);
					stage.show();
				} catch (BlockCodeCompilerErrorException e1) {
				}
			}
		});
		layout.setTop(run);
	}

	public VBox setupPalette() {
		VBox palette = new VBox();
		palette.setSpacing(40);
		palette.setPadding(new Insets(30));
		palette.setMinWidth(200);

		palette.setBackground(
				new Background(new BackgroundFill(Color.rgb(255, 10, 10, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		GraphicalBlock[] paletteBlocks = { new PrintBlock() }; //TODO: Add new Blocks here

		int height = 60;

		for (GraphicalBlock b : paletteBlocks) {

			CreateHandler handler = new CreateHandler(b);
			b.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
			b.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
			b.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);

			palette.getChildren().add(b);
			height = (int) (height + 40 + b.getHeight());
		}

		return palette;
	}

	private class CreateHandler implements EventHandler<MouseEvent> {

		GraphicalBlock block;

		GraphicalBlock current;

		public CreateHandler(GraphicalBlock b) {
			block = b;
		}

		@Override
		public void handle(MouseEvent e) {
			
			if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {

				current = block.cloneBlock();
				Workspace.this.getChildren().add(current);

				current.setLayoutX(e.getSceneX());
				current.setLayoutY(e.getSceneY());
				
				BlockHandler handler = new BlockHandler(current);
				current.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
				current.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
				current.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);

			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				
				for(GraphicalBlock b : blocks.traverse(BSTree.INORDER)) {
					Point2D point = new Point2D(b.getLayoutX(), b.getLayoutY());
					double distance = point.distance(new Point2D(current.getLayoutX(), current.getLayoutY()));
					System.err.println(distance);
					if(distance < 50 && !b.boundTo) {
						current.setBound(b);
						b.boundTo = true;
						current.layoutXProperty().bind(b.layoutXProperty());
						current.layoutYProperty().bind(b.layoutYProperty().add(b.getHeight()));
						break;
					}
				}
				blocks.add(current);
			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				current.setLayoutX(e.getSceneX());
				current.setLayoutY(e.getSceneY());

			}

		}

	}

	private class BlockHandler implements EventHandler<MouseEvent> {

		private GraphicalBlock block;
		private double offsetX, offsetY;

		public BlockHandler(GraphicalBlock b) {
			block = b;
		}

		@Override
		public void handle(MouseEvent e) {
			if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
				block.layoutXProperty().unbind();
				block.layoutYProperty().unbind();
				offsetX = e.getSceneX() - block.getLayoutX();
				offsetY = e.getSceneY() - block.getLayoutY();
				if(block.getBound() != null)
					block.getBound().boundTo = false;
				block.setBound(null);
				
			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				for(GraphicalBlock b : blocks.traverse(BSTree.INORDER)) {
					Point2D point = new Point2D(b.getLayoutX(), b.getLayoutY());
					double distance = point.distance(new Point2D(block.getLayoutX(), block.getLayoutY()));
					if(distance < 50 && !b.boundTo && block != b && b.getBound() != block) {
						b.boundTo = true;
						block.setBound(b);
						block.layoutXProperty().bind(b.layoutXProperty());
						block.layoutYProperty().bind(b.layoutYProperty().add(b.getHeight()));
						break;
					}
				}
			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				block.setLayoutX(e.getSceneX() - offsetX);
				block.setLayoutY(e.getSceneY() - offsetY);

			}

		}
	}

	/**
	 * @apiNote O(infinity)
	 * @return - the output from the program
	 * @throws BlockCodeCompilerErrorException
	 * 
	 */
	private String run() throws BlockCodeCompilerErrorException {

		return "asfd";
	}

	

}
