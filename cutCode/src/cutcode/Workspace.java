package cutcode;


import factories.LogicalFactory;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import factories.GUIFactory;

/**
 * @author Arjun Khanna and Peter Timpane
 */
public class Workspace extends Pane {
	private static final int bindDistance = 15;
	private static final int nestDistance = 15;
	private ArrayList<GraphicalBlock> blocks;
	private BorderPane layout;
	private VBox palette;
	private ScrollPane paletteScroll;
	private GUIFactory guiFactory;
	private static final double paletteWidth = 200;
	private static final double runButtonHeight = 20;
	private LogicalFactory logicalFactory;
	private Button run;
	private Button changeLanguage;
	private double height; //necessary for reset

	public Workspace(double width, double height, GUIFactory guiFactory, LogicalFactory logicalFactory, Main mainClass, int baseLineNumber) {
		this.height = height;
		this.logicalFactory = logicalFactory;
		this.guiFactory = guiFactory;
		blocks = new ArrayList<>();

		this.setMinHeight(height);
		this.setMaxHeight(height);
		this.setMinWidth(width);
		this.setMaxWidth(width);
		layout = new BorderPane();
		this.getChildren().add(layout);
		palette = setupPalette(paletteWidth, height - runButtonHeight);
		palette.setPrefHeight(palette.getHeight() + 100);

		paletteScroll = new ScrollPane();
		paletteScroll.setContent(palette);
		paletteScroll.setMinHeight(this.getMinHeight());
		paletteScroll.setMaxHeight(this.getMaxHeight());
		paletteScroll.setPrefHeight(this.getMaxHeight());
		paletteScroll.setMinWidth(paletteWidth);

		layout.setLeft(paletteScroll);


		run = new Button("run");
		run.setMaxHeight(runButtonHeight);
		run.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				try {
					HashMap<Integer, GraphicalBlock> lineLocations = new HashMap<>();
					BSTree<GraphicalBlock> orderedHeadBlocks = new BSTree<>();
					ArrayList<LogicalBlock> orderedBlocks = new ArrayList<>();
					int lineLoc = baseLineNumber;
					for(GraphicalBlock b : blocks) {
						if(b.getNestedIn() == null) {
							b.setLineNumber(lineLoc);
							lineLoc = b.putInHashMap(lineLocations);
							if(b.getAbove() == null)
								orderedHeadBlocks.add(b);
						}
					}

					if(orderedHeadBlocks.inOrder() == null) {
						OutputView.output("Please create some blocks before exporting", new Stage());
						return;
					}
					for(GraphicalBlock head : orderedHeadBlocks.inOrder()) {
						for(GraphicalBlock curr = head; curr != null; curr = curr.getBelow())
							orderedBlocks.add(curr.getLogicalBlock());
					}
					String ret = mainClass.run(orderedBlocks, lineLocations);
					OutputView.output(ret, new Stage());
				} catch (BlockCodeCompilerErrorException e1) {
				}
			}
		});
		changeLanguage = new Button("change language");
		changeLanguage.setMaxHeight(runButtonHeight);
		changeLanguage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mainClass.reset();
			}
		});
		Button export = new Button("export");
		export.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				BSTree<GraphicalBlock> orderedHeadBlocks = new BSTree<>();
				ArrayList<LogicalBlock> orderedBlocks = new ArrayList<>();
				for(GraphicalBlock b : blocks) {
					if(b.getNestedIn() == null) {
						if(b.getAbove() == null)
							orderedHeadBlocks.add(b);
					}
				}
				if(orderedHeadBlocks.inOrder() == null) {
					OutputView.output("Please create some blocks before exporting", new Stage());
					return;
				}

				for(GraphicalBlock head : orderedHeadBlocks.inOrder()) {
					for(GraphicalBlock curr = head; curr != null; curr = curr.getBelow()) {
						try {
							orderedBlocks.add(curr.getLogicalBlock());
						} catch (BlockCodeCompilerErrorException blockCodeCompilerErrorException) {
							OutputView.output("Export failed, please check your code", new Stage());
						}
					}
				}
				String file = FilePicker.chooseFile(new Stage());
				if(file == null)
					OutputView.output("Please pick a valid file", new Stage());
				else {
					try {
						OutputView.output(mainClass.export(orderedBlocks, file), new Stage());
					} catch (IOException ioException) {
						OutputView.output("An unexpected error occurred in exporting your code to " + file, new Stage());
					}
				}
			}
		});
		export.setMaxHeight(runButtonHeight);


		HBox topButtons = new HBox(run, changeLanguage, export);
		topButtons.setAlignment(Pos.TOP_LEFT);
		layout.setTop(topButtons);

	}

	public VBox setupPalette(double width, double height) {
		VBox palette = new VBox();
		palette.setSpacing(40);
		palette.setPadding(new Insets(30));
		palette.setMinWidth(width);
		palette.setMinHeight(height);
		palette.setBackground(
				new Background(new BackgroundFill(Color.rgb(128, 209, 255, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));


		for (GraphicalBlock b : guiFactory.getAllBlocks()) {

			CreateHandler handler = new CreateHandler(b);
			b.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
			b.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
			b.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);

			palette.getChildren().add(b);
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
				current.setLogicalFactory(logicalFactory);
			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				current.setLayoutX(e.getSceneX());
				current.setLayoutY(e.getSceneY());

			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				if (palette.contains(e.getSceneX(), e.getSceneY()) || e.getSceneX() < palette.getWidth()) {
					Workspace.this.getChildren().remove(current);
					return;
				}

				blocks.add(current); //Need to add the new block to
				for (GraphicalBlock b : blocks) {
					if (b == current)
						continue;
					Point2D checkPoint = new Point2D(b.getLayoutX(), b.getLayoutY() + b.getMaxHeight());
					Point2D clickPoint = new Point2D(current.getLayoutX(), current.getLayoutY());
					double distance = checkPoint.distance(clickPoint);
					if (distance < bindDistance && (b.getBelow() == null)) {
						current.setAbove(b);
						b.setBelow(current);
						break;
					}

					Point2D[] nestables = b.getNestables();
					for (int i = 0; i < nestables.length; i++) {
						distance = clickPoint.distance(nestables[i]);
						if (distance < nestDistance) {
							try {
								b.nest(i, current);
							} catch (InvalidNestException invalidNestException) {
								continue; //TODO action here?
							}

						}
					}
				}

			}

		}

	}

	private class BlockHandler implements EventHandler<MouseEvent> {

		private GraphicalBlock block;
		private double offsetX, offsetY;
		private List<GraphicalBlock> clickSequence;
		private List<GraphicalBlock> ignoring;

		public BlockHandler(GraphicalBlock b) {
			block = b;
		}

		@Override
		public void handle(MouseEvent e) {
			if (block.ignoreStatus() && !e.getEventType().equals(MouseEvent.MOUSE_RELEASED))
				return;
			else
				block.actionIgnored();
			if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
				GraphicalBlock add = block;

				clickSequence = new ArrayList<>();
				while (add != null) {
					add.toFront();
					clickSequence.add(add);
					add = add.getBelow();
				}
				// If the block was bound, it unbinds. All the blocks bound to this block will
				// stay that way

				offsetX = e.getSceneX() - block.getLayoutX();
				offsetY = e.getSceneY() - block.getLayoutY();
				// offsets make it so the point you clicked on the block follows your mouse instead of the top left.
				// Looks better

				GraphicalBlock above = block.getAbove();
				if (above != null)
					above.setBelow(null); // Allows for things to be bound to "above"

				block.setAbove(null);


				//TODO: need to check if the click is on a nested block as opposed to it's parent block
				GraphicalBlock nestedIn = block.getNestedIn();
				if (nestedIn != null) {
					try {
						offsetX = 0;
						offsetY = 0;
						VBox nestBox = (VBox) block.getParent();
						for (GraphicalBlock block : clickSequence) {
							nestedIn.unnest(nestBox, block);
							Workspace.this.getChildren().add(block);
							block.setNestedIn(null);
						}
						block.setLayoutX(e.getSceneX() - offsetX);
						block.setLayoutY(e.getSceneY() - offsetY);
						GraphicalBlock ignore = nestedIn;
						ignoring = new ArrayList<>();
						do {
							ignoring.add(ignore);
							ignore.ignoreNextAction();
							ignore = ignore.getNestedIn();
						} while (ignore != null);
					} catch (ClassCastException | InvalidNestException castException) {
						//TODO this should never happen. Maybe institute a failsafe here
						castException.printStackTrace();
					}

				}

			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				block.setLayoutX(e.getSceneX() - offsetX);
				block.setLayoutY(e.getSceneY() - offsetY);


			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {


				if (ignoring != null) { //Need to unignore all listeners being ignored, otherwise problems occur
					for (GraphicalBlock b : ignoring) {
						b.actionIgnored();
					}
					ignoring = null;
				}

				if (palette.contains(e.getSceneX() - offsetX, e.getSceneY() - offsetY)) {
					for (GraphicalBlock rem : clickSequence) {
						remove(rem);
					}
					return;
				} else {
					for (GraphicalBlock b : blocks) {
						if (b == block)
							continue;
						Point2D clickPoint = new Point2D(block.getLayoutX(), block.getLayoutY());
						Point2D point = new Point2D(b.getLayoutX(), b.getLayoutY() + b.getMaxHeight());
						double distance = point.distance(new Point2D(block.getLayoutX(), block.getLayoutY()));
						if (b == block || b.getAbove() == block)
							continue;
						if (distance < bindDistance && (b.getBelow() == null)) {
							b.setBelow(block);
							block.setAbove(b);
							block.layoutXProperty().bind(b.layoutXProperty());
							block.layoutYProperty().bind(b.layoutYProperty().add(b.getHeight()));
							break;
						}

						Point2D[] nestables = b.getNestables();
						boolean cycle = false;
						GraphicalBlock cycleCheck = b;
						while (cycleCheck.getNestedIn() != null && !cycle) {
							cycle = b == block;
							cycleCheck = cycleCheck.getNestedIn();
						}
						if (!cycle) {
							for (int i = 0; i < nestables.length; i++) {
								distance = clickPoint.distance(nestables[i]);

								if (distance < nestDistance) {
									//This needs to be done before returning
									try {
										b.nest(i, block);

										for (GraphicalBlock add : clickSequence) {
											if (add == block)
												continue;
											try {
												if(add.getAbove() != null) {
													add.getAbove().setBelow(null);
												}
												add.setAbove(null);
												b.nest(i, add);
											} catch (InvalidNestException innerNestException) {
												add.getAbove().setBelow(null);
												add.setAbove(null);
												add.setLayoutX(add.getLayoutX() + 10);
												add.setLayoutY(add.getLayoutY() + 10);
												break;
											}
										}
									} catch (InvalidNestException | IllegalArgumentException invalidNestException) { //In case it finds a cycle or invalid nest
										continue;
									}
									return;

								}
							}
						}
					}
				}
			}
		}
	}




	private void remove(GraphicalBlock rem) {
		blocks.remove(rem);
		this.getChildren().remove(rem);
		ArrayList<GraphicalBlock> rems = rem.getChildBlocks();
		if (rems != null) {
			for (GraphicalBlock recurseRem : rems)
				remove(recurseRem);
		}
	}

	public void reset( GUIFactory guiFactory, LogicalFactory logicalFactory, Main mainClass, int baseLineNumber) {
		this.guiFactory = guiFactory;
		this.logicalFactory = logicalFactory;
		palette = setupPalette(paletteWidth, height - runButtonHeight);
		palette.setPrefHeight(palette.getHeight() + 100);
		paletteScroll = new ScrollPane();
		paletteScroll.setContent(palette);
		paletteScroll.setMinHeight(this.getMinHeight());
		paletteScroll.setMaxHeight(this.getMaxHeight());
		paletteScroll.setPrefHeight(this.getMaxHeight());
		paletteScroll.setMinWidth(paletteWidth);
		layout.setLeft(paletteScroll);
		blocks = new ArrayList<>();
		run.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				try {
					HashMap<Integer, GraphicalBlock> lineLocations = new HashMap<>();
					BSTree<GraphicalBlock> orderedHeadBlocks = new BSTree<>();
					ArrayList<LogicalBlock> orderedBlocks = new ArrayList<>();
					int lineLoc = baseLineNumber;
					for(GraphicalBlock b : blocks) {
						if(b.getNestedIn() == null) {
							b.setLineNumber(lineLoc);
							lineLoc = b.putInHashMap(lineLocations);
							if(b.getAbove() == null)
								orderedHeadBlocks.add(b);
						}
					}

					if(orderedHeadBlocks.inOrder() == null) {
						OutputView.output("Please create some blocks before exporting", new Stage());
						return;
					}
					for(GraphicalBlock head : orderedHeadBlocks.inOrder()) {
						for(GraphicalBlock curr = head; curr != null; curr = curr.getBelow())
							orderedBlocks.add(curr.getLogicalBlock());
					}
					String ret = mainClass.run(orderedBlocks, lineLocations);
					OutputView.output(ret, new Stage());
				} catch (BlockCodeCompilerErrorException e1) {
				}
			}
		});
		for(int i = 0; i < this.getChildren().size(); i++) { //Blocks reset when language changed
			Node n = this.getChildren().get(i);
			if(n instanceof GraphicalBlock) {
				this.getChildren().remove(n);
			}
		}
	}

}
