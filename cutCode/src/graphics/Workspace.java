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
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

public class Workspace extends Pane {

	private BSTree<Sequence<GraphicalBlock>> sequences;
	private LList<FunctionBlock> functions;
	private LList<NestableBlock> nestables;
	// private Pane playArea;
	private BorderPane layout;
	private VBox palette;
	private ScrollPane paletteScroll;

	public Workspace(double width, double height) {

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
		nestables = new LList<NestableBlock>();
	}

	public VBox setupPalette() {
		VBox palette = new VBox();
		palette.setSpacing(40);
		palette.setPadding(new Insets(30));
		palette.setMinWidth(200);

		palette.setBackground(
				new Background(new BackgroundFill(Color.rgb(255, 10, 10, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		GraphicalBlock[] paletteBlocks = { new ElseBlock(), new IfBlock(), new DoubleBlock(), new PrintBlock(),
				new StringBlock(), new BooleanBlock(), new VariableCallBlock(), new WhileBlock(), new OrBlock(),
				new AndBlock(), new GreaterBlock(), new GreaterEqualBlock(), new LesserBlock(), new LesserEqualBlock(),
				new FunctionBlock() };

		int height = 60;

		for (GraphicalBlock b : paletteBlocks) {

			MouseHandler handler = new MouseHandler(b);
			b.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
			b.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
			b.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);

			palette.getChildren().add(b);
			height = (int) (height + 40 + b.getHeight());
		}

		return palette;
	}

	private class MouseHandler implements EventHandler<MouseEvent> {

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

				double scrollPos = paletteScroll.getVvalue() * paletteScroll.getHeight();
				System.err.println(scrollPos);

				// add.setPrefWidth(200);
				// add.setPrefHeight(40);
				Workspace.this.getChildren().add(current);
				double mouseX = e.getSceneX();
				double mouseY = e.getSceneY();

				double blockX = block.getLayoutX();
				double blockY = block.getLayoutY();

				current.setLayoutX(e.getSceneX());
				current.setLayoutY(e.getSceneY());

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
					List<Sequence<GraphicalBlock>> sequencesSorted = sequences.traverse(BSTree.INORDER);

					for (Sequence<GraphicalBlock> s : sequencesSorted) {

						double endX = s.getEnd().getLayoutX();
						double endY = s.getEnd().getLayoutY() + s.getEnd().getHeight();

						if (Math.pow(current.getLayoutY() - endY, 2) + Math.pow(current.getLayoutX() - endX, 2) < 225) {
							s.add(current);
							current.setSequence(s);
							current.setLayoutX(endX);
							current.setLayoutY(endY);

							connected = true;

							break;
						}

					}

					if (!connected) {
						boolean nested = false;
						Point2D currentPoint = new Point2D(current.getLayoutX(), current.getLayoutY());
						for (NestableBlock nest : nestables) {
							System.err.println("nest");
							if (nest.getPrimaryNestPoint() != null
									&& currentPoint.distance(nest.getPrimaryNestPoint()) < 15) {
								nest.primaryNest(current);
								
								nested = true;
								break;
							} else if (nest.getSecondaryNestPoint() != null
									&& currentPoint.distance(nest.getSecondaryNestPoint()) < 15) {
								nest.secondaryNest(current);
								
								nested = true;
								break;
							}
						}
						if (!nested) {
							Sequence<GraphicalBlock> sequence = new Sequence<GraphicalBlock>();
							sequence.add(current);
							current.setSequence(sequence);
							sequences.add(sequence);
						}
						if (current instanceof NestableBlock)
							nestables.add((NestableBlock) current);
					}

				}
			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				current.setLayoutX(e.getSceneX());
				current.setLayoutY(e.getSceneY());

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

				Sequence<GraphicalBlock> sequence = new Sequence<GraphicalBlock>();// Create a new sequence for the
																					// block

				Sequence<GraphicalBlock> oldSequence = block.getSequence();// Get the old sequence

				int index = -1;// Store the position of the block in the sequence
				for (int i = 0; i < oldSequence.size(); i++) {
					if (oldSequence.get(i) == block) {
						index = i;// When you find the block, store it's location
					}

					if (index < 0) {// If the block hasn't been found yet, go on to the next iteration
						continue;
					}

					sequence.add(oldSequence.get(i));// Add the block to the new sequence. Only runs after the block has
														// been found
					oldSequence.get(i).setSequence(sequence);// Store the sequence in the blocks
				}

				if (oldSequence.size() == sequence.size()) {
					System.err.println(sequences.remove(oldSequence));// If the new sequence ends up containing all the
																		// block from the old sequence,
				} else { // remove the old sequence from the list of sequences

					while (oldSequence.size() > index) {// No iteration required because the list gets smaller and
														// smaller each time
						oldSequence.remove(index);
						System.err.println("remove");
					}

				}
				// sequences.add(sequence);

				for (int i = 1; i < sequence.size(); i++) {// Starts at 1 because we can't bind block's properties to
															// itself
					GraphicalBlock b = sequence.get(i);

					b.layoutXProperty().unbind();
					b.layoutXProperty().bind(block.layoutXProperty());
					b.layoutYProperty().unbind();
					b.layoutYProperty().bind(block.layoutYProperty().add(b.getLayoutY() - block.getLayoutY()));
				}

				System.err.println(block.layoutXProperty());
				block.layoutXProperty().unbind();
				block.layoutYProperty().unbind();

			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {

				Point2D mouse = new Point2D(e.getSceneX(), e.getSceneY());
				if (palette.contains(mouse)) {
					for (GraphicalBlock b : block.getSequence()) {
						Workspace.this.getChildren().remove(b);

					}

				} else {
					boolean nested = false;
					Point2D currentPoint = new Point2D(block.getLayoutX(), block.getLayoutY());
					for (NestableBlock nest : nestables) {
						if (nest.getPrimaryNestPoint() != null
								&& currentPoint.distance(nest.getPrimaryNestPoint()) < 15) {
							nest.primaryNest(block);
							block.setLayoutX(nest.getPrimaryNestPoint().getX());
							block.setLayoutY(nest.getPrimaryNestPoint().getY());
							for(GraphicalBlock b : block.getSequence()) {
								if(b == block) {
									continue;
								}
								b.layoutXProperty().unbind();
								b.layoutYProperty().unbind();
								nest.primaryNest(b);
								
							}
							nested = true;
							break;
						} else if (nest.getSecondaryNestPoint() != null
								&& currentPoint.distance(nest.getSecondaryNestPoint()) < 15) {
							nest.secondaryNest(block);
							block.setLayoutX(nest.getSecondaryNestPoint().getX());
							block.setLayoutY(nest.getSecondaryNestPoint().getY());
							for(GraphicalBlock b : block.getSequence()) {
								if(b == block) {
									continue;
								}
								b.layoutXProperty().unbind();
								b.layoutYProperty().unbind();
								nest.primaryNest(b);
								
							}
							nested = true;
							break;
						}
					}
					if (!nested) {
						sequences.add(block.getSequence());

						for (Sequence<GraphicalBlock> s : sequences.traverse(BSTree.INORDER)) {// Go through all
																								// sequences in the
																								// workspace

							if (Math.pow(block.getLayoutX() - s.getEnd().getLayoutX(), 2) + // Check if the block is
																							// close enough to the end
									Math.pow(block.getLayoutY() - (s.getEnd().getLayoutY() + s.getEnd().getHeight()),
											2) < 225) {// Distance formula

								block.setLayoutX(s.getEnd().getLayoutX());
								block.setLayoutY(s.getEnd().getLayoutY() + s.getEnd().getHeight());

								// System.err.println("Connect");
								Sequence<GraphicalBlock> oldSequence = block.getSequence();
								System.out.println(block.getSequence().size());
								for (GraphicalBlock b : oldSequence) {
									s.add(b);
									b.setSequence(s);
								}

								// for(Sequence<GraphicalBlock> se : sequences.traverse(BSTree.INORDER)) {
								// System.err.println(se);
								// }
								// System.err.println(oldSequence);

								System.err.println(sequences.remove(oldSequence));

								break;
							}
						}
					}
				}
				System.err.println(sequences.traverse(BSTree.INORDER).size());

			} else {
				// Move the blocks around the screen
				if (block instanceof IfBlock) {

				}
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
		FunctionBlock main = new FunctionBlock();
		main.makeMain(sequences.traverse(BSTree.INORDER));
		functions.add(0, main);
		try {
			String s = Main.run(functions);

			functions.remove(0);
			unTagAll(functions, sequences.traverse(BSTree.INORDER));

			return s;
		} catch (BlockCodeCompilerErrorException e) {
			functions.remove(0);
			throw new BlockCodeCompilerErrorException();
		}

	}

	private void unTagAll(List<FunctionBlock> funcs, List<Sequence<GraphicalBlock>> blocks) {
		System.err.println("fuker");
		for (Sequence<GraphicalBlock> bs : blocks) {
			for (GraphicalBlock b : bs) {
				b.untag();
			}

		}
		for (FunctionBlock b : funcs) {
			b.untag();
		}
	}
}
