package graphics;

import cutcode.BSTree;
import cutcode.BlockCodeCompilerErrorException;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Arjun Khanna and Peter Timpane
 *
 */
public class Workspace extends Pane {

	private BSTree<GraphicalBlock> blocks;
	private BorderPane layout;
	private VBox palette;
	private ScrollPane paletteScroll;
	@SuppressWarnings("unused")
	private int childrenStartIndex; // the index from which blocks start appearing in Workspace.this.getChildren()

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

		childrenStartIndex = Workspace.this.getChildren().size();
	}

	public VBox setupPalette() {
		VBox palette = new VBox();
		palette.setSpacing(40);
		palette.setPadding(new Insets(30));
		palette.setMinWidth(200);

		palette.setBackground(
				new Background(new BackgroundFill(Color.rgb(255, 10, 10, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		GraphicalBlock[] paletteBlocks = { new PrintBlock(), new VariableBlock(), new VariableCallBlock(), new BooleanBinaryOperatorBlock()}; // TODO: Add new Blocks here

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

			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				current.setLayoutX(e.getSceneX());
				current.setLayoutY(e.getSceneY());

			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				if (palette.contains(e.getSceneX(), e.getSceneY())) {
					Workspace.this.getChildren().remove(current);
					return;
				}

				
				for (GraphicalBlock b : blocks.traverse(BSTree.INORDER)) {
					Point2D checkPoint = new Point2D(b.getLayoutX(), b.getLayoutY() + b.getMaxHeight());
					Point2D clickPoint = new Point2D(current.getLayoutX(), current.getLayoutY());
					double distance = checkPoint.distance(clickPoint);
					if (b.getBound() == current)
						continue;
					if (distance < 15 && (b.getBoundTo() == null)) {
						current.setBound(b);
						b.setBoundTo(current);
						current.layoutXProperty().bind(b.layoutXProperty());
						current.layoutYProperty().bind(b.layoutYProperty().add(b.getHeight()));
						break;
					}

					Point2D[] nestables = b.getNestables();
					for(int i = 0; i < nestables.length; i++) {

						distance = clickPoint.distance(nestables[i]);
						if(distance < 40) {
							try {
								b.nest(i, current);
								Workspace.this.getChildren().remove(current);
							} catch (InvalidNestException invalidNestException) {
								invalidNestException.printStackTrace();
							}

						}
					}
				}
				blocks.add(current);
			}

		}

	}

	private class BlockHandler implements EventHandler<MouseEvent> {

		private GraphicalBlock block;
		private double offsetX, offsetY;
		private List<GraphicalBlock> clickSequence;

		public BlockHandler(GraphicalBlock b) {
			block = b;
		}

		@Override
		public void handle(MouseEvent e) {
			if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
				block.layoutXProperty().unbind();
				block.layoutYProperty().unbind();
				// If the block was bound, it unbinds. All the blocks bound to this block will
				// stay that way

				offsetX = e.getSceneX() - block.getLayoutX();
				offsetY = e.getSceneY() - block.getLayoutY();
				// offsets make it so the point you clicked on the block follows your mouse.
				// Looks better

				GraphicalBlock above = block.getBound();
				if (above != null)
					above.setBoundTo(null); // Allows for things to be bound to "above"

				block.setBound(null);
				GraphicalBlock rem = block;
				int count = 0;
				clickSequence = new ArrayList<GraphicalBlock>();
				while (rem != null) {
					rem.toFront();
					clickSequence.add(blocks.remove(rem));
					rem = rem.getBoundTo();
				}
			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				block.setLayoutX(e.getSceneX() - offsetX);
				block.setLayoutY(e.getSceneY() - offsetY);

			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				if (palette.contains(e.getSceneX() - offsetX, e.getSceneY() - offsetY)) {
					for(GraphicalBlock rem : clickSequence)
						Workspace.this.getChildren().remove(rem);
					return;
				}



				for (GraphicalBlock b : blocks.traverse(BSTree.INORDER)) {
					Point2D clickPoint = new Point2D(block.getLayoutX(), block.getLayoutY());
					Point2D point = new Point2D(b.getLayoutX(), b.getLayoutY() + b.getMaxHeight());
					double distance = point.distance(new Point2D(block.getLayoutX(), block.getLayoutY()));
					if(b == block)
						System.err.println("wuh oh");
					if (b.getBound() == block)
						continue;
					if (distance < 15 && (b.getBoundTo() == null)) {
						b.setBoundTo(block);
						block.setBound(b);
						block.layoutXProperty().bind(b.layoutXProperty());
						block.layoutYProperty().bind(b.layoutYProperty().add(b.getHeight()));
						break;
					}

					Point2D[] nestables = b.getNestables();
					for(int i = 0; i < nestables.length; i++) {
						distance = clickPoint.distance(nestables[i]);
						if(distance < 40) {
							try {
								b.nest(i, block);
								Workspace.this.getChildren().remove(block);
							} catch (InvalidNestException invalidNestException) {
								invalidNestException.printStackTrace();
							}
						}
					}
				}

				for(GraphicalBlock add : clickSequence)
					blocks.add(add); // Height of the block is now set, can add back to BSTree
				
			}

		}
	}

	/**
	 * O(infinity)
	 * @return - the output from the program
	 * @throws BlockCodeCompilerErrorException if the block code doesn't compile
	 * 
	 */
	private String run() throws BlockCodeCompilerErrorException {
		return "asfd";
	}

}
