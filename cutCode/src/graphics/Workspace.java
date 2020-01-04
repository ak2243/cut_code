package graphics;

import java.util.ArrayList;
import java.util.List;

import cutcode.BSTree;
import cutcode.BlockCodeCompilerErrorException;
import cutcode.LList;
import cutcode.Main;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

public class Workspace extends Pane {

	private BSTree<Sequence<GraphicalBlock>> sequences;
	private LList<FunctionBlock> functions;
	// private Pane playArea;
	private BorderPane layout;
	private VBox palette;
	private static int sNumber;

	public Workspace(double width, double height) {

		this.setMinHeight(height);
		this.setMaxHeight(height);
		this.setMinWidth(width);
		this.setMaxWidth(width);
		layout = new BorderPane();
		this.getChildren().add(layout);
		palette = setupPalette();
		palette.setPrefHeight(palette.getHeight() + 100);
		
		ScrollPane paletteScroll = new ScrollPane();
		paletteScroll.setContent(palette);
		paletteScroll.setMinHeight(this.getMinHeight());
		paletteScroll.setMaxHeight(this.getMaxHeight());
		paletteScroll.setPrefHeight(this.getMaxHeight());
		paletteScroll.setMinWidth(200);
		
		layout.setLeft(paletteScroll);

		sequences = new BSTree<Sequence<GraphicalBlock>>();
		functions = new LList<FunctionBlock>();

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
					System.err.println("?");
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


		GraphicalBlock[] paletteBlocks = { new IfBlock(), new DoubleBlock(), new PrintBlock(), new StringBlock(), 
				new BooleanBlock(), new VariableCallBlock(), new WhileBlock(), new OrBlock(), new AndBlock(), new GreaterBlock(),
				new GreaterEqualBlock(), new LesserBlock(), new LesserEqualBlock(), new FunctionBlock()};
		
		int height = 60;
		

		for (GraphicalBlock b : paletteBlocks) {

			MouseHandler handler = new MouseHandler(b);
			b.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
			b.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
			b.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);

			palette.getChildren().add(b);
			height = (int)(height + 40 + b.getHeight());
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

				current = block.cloneBlock();

				// add.setPrefWidth(200);
				// add.setPrefHeight(40);
				Workspace.this.getChildren().add(current);
				double mouseX = e.getSceneX();
				double mouseY = e.getSceneY();

				double blockX = block.getLayoutX();
				double blockY = block.getLayoutY();

				offsetX = mouseX - blockX;
				offsetY = mouseY - blockY;

				current.setLayoutX(e.getSceneX() - offsetX);
				current.setLayoutY(e.getSceneY() - offsetY);

			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {

				Point2D mouse = new Point2D(e.getSceneX(), e.getSceneY());
				if (palette.contains(mouse)) {

					Workspace.this.getChildren().remove(current);
				} else {
					BlockHandler handler = new BlockHandler(current);
					current.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
					current.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
					current.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);

					boolean connected = false;
					ArrayList<Sequence<GraphicalBlock>> sequencesSorted = sequences.traverse(BSTree.INORDER);

					for (Sequence<GraphicalBlock> s : sequencesSorted) {
						connected = false;
						double endX = s.getEnd().getLayoutX();
						double endY = s.getEnd().getLayoutY() + s.getEnd().getHeight();

						System.err.println(endX + "," + endY);

						if (Math.pow(current.getLayoutY() - endY, 2) + Math.pow(current.getLayoutX() - endX, 2) < 225) {
							s.add(current);
							current.setSequence(s);
							System.err.println("(" + current.getLayoutX() + " , " + current.getLayoutY() + ")");
							current.setLayoutX(endX);
							current.setLayoutY(endY);
							System.err.println("(" + current.getLayoutX() + " , " + current.getLayoutY() + ")");

							connected = true;
							System.err.println("Connected!");
							break;
						}
						System.err.println("Sequence passed w/o connection");
					}

					System.err.println(connected);
					if (!connected) {

						Sequence<GraphicalBlock> sequence = new Sequence<GraphicalBlock>();
						sequence.add(current);
						current.setSequence(sequence);
						sequences.add(sequence);
						sNumber++;
					}
					System.err.println(sNumber);
				}
			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				current.setLayoutX(e.getSceneX() - offsetX);
				current.setLayoutY(e.getSceneY() - offsetY);
			}

		}

	}

	private class BlockHandler implements EventHandler<MouseEvent> {

		private GraphicalBlock block;
		private double offsetX;
		private double offsetY;

		public BlockHandler(GraphicalBlock b) {
			block = b;
		}

		@Override
		public void handle(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {

				offsetX = e.getSceneX() - block.getLayoutX();
				offsetY = e.getSceneY() - block.getLayoutY();

				if (block.getSequence().size() != 1) {
					Sequence<GraphicalBlock> sequence = new Sequence<GraphicalBlock>();
					boolean found = false;

					int index = 0;
					Sequence<GraphicalBlock> oldSequence = block.getSequence();
					for (int i = 0; i < oldSequence.size(); i++) {
						if (oldSequence.get(i).equals(block)) {
							found = true;
							index = 1;
						}

						if (!found) {
							continue;
						}
						sequence.add(oldSequence.get(i));
					}
					for (int i = index; i < oldSequence.size(); i++) {
						oldSequence.remove(i);
					}

					block.setSequence(sequence);

				}

				for (int i = 1; i < block.getSequence().size(); i++) {
					GraphicalBlock b = block.getSequence().get(i);
					b.layoutXProperty().unbind();
					b.layoutXProperty().bind(block.layoutXProperty());

					b.layoutYProperty().unbind();
					b.layoutYProperty().bind(block.layoutYProperty().add(b.getLayoutY() - block.getLayoutY()));
				}

			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				Point2D mouse = new Point2D(e.getSceneX(), e.getSceneY());
				if (palette.contains(mouse)) {
					for (GraphicalBlock b : block.getSequence()) {
						Workspace.this.getChildren().remove(b);
					}
				} else {
					boolean connected = false;
					ArrayList<Sequence<GraphicalBlock>> sequencesSorted = sequences.traverse(BSTree.INORDER);
					for (Sequence<GraphicalBlock> s : sequencesSorted) {
						connected = false;
						double endX = s.getEnd().getLayoutX();
						double endY = s.getEnd().getLayoutY() + s.getEnd().getHeight();

						System.err.println(endX + "," + endY);

						if (Math.pow(block.getLayoutY() - endY, 2) + Math.pow(block.getLayoutX() - endX, 2) < 225) {
							for (GraphicalBlock b : block.getSequence()) {
								s.add(b);
								b.setSequence(s);
							}
							System.err.println("(" + block.getLayoutX() + " , " + block.getLayoutY() + ")");
							block.setLayoutX(endX);
							block.setLayoutY(endY);
							System.err.println("(" + block.getLayoutX() + " , " + block.getLayoutY() + ")");
							connected = true;
							System.err.println("Connected!");
							break;
						}
						System.err.println("Sequence passed w/o connection");
					}
					System.err.println(connected);

					System.err.println(sNumber);
				}

			} else {
				block.setLayoutX(e.getSceneX() - offsetX);
				block.setLayoutY(e.getSceneY() - offsetY);
				for (GraphicalBlock b : block.getSequence()) {

				}

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
		FunctionBlock main = new FunctionBlock();
		main.makeMain(sequences.traverse(BSTree.INORDER));
		functions.add(0, main);
		String s = Main.run(functions);
		functions.remove(0);
		return s;
	}
}
