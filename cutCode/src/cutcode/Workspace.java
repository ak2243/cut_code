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
	private double bindDistance, nestDistance;
	private ArrayList<GraphicalBlock> blocks;
	private BorderPane layout;
	private VBox palette;
	private ScrollPane paletteScroll; //Palette may need to scroll in the feature
	private GUIFactory guiFactory;//used for factory design pattern
	private double paletteWidth, runButtonHeight;
	private LogicalFactory logicalFactory; //used for factory design pattern

	private Button run; //run button
	private Button changeLanguage; //change language button
	private double height, width; //necessary for reset

	public Workspace(double width, double height, GUIFactory guiFactory, LogicalFactory logicalFactory, Main mainClass, int baseLineNumber) {
		this.height = height;
		this.width = width;
		this.logicalFactory = logicalFactory;
		this.guiFactory = guiFactory;
		blocks = new ArrayList<>();
		
		bindDistance = nestDistance = this.width*0.025;
		runButtonHeight = this.height/30;
		paletteWidth = this.width/6;

		
		this.setMinHeight(height);
		this.setMaxHeight(height);
		this.setMinWidth(width);
		this.setMaxWidth(width);
		layout = new BorderPane();
		this.getChildren().add(layout);
		palette = setUpPalette(paletteWidth, height - runButtonHeight);
		

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
					int lineLoc = baseLineNumber;

					for(GraphicalBlock b : blocks) { //Goes through all the block
						b.untag(); //Ensures that error tags from previous methods don't stay
						if(b.getNestedIn() == null) {
							if(b.getAbove() == null)
								orderedHeadBlocks.add(b); //Ordering by height of head blocks (aren't nested in or attached to something)
						}
					}
					if(orderedHeadBlocks.inOrder() == null) {
						OutputView.output("Please create some blocks before exporting", new Stage());
						return;
					}
					List<GraphicalBlock> funcBlocks = guiFactory.sortFunctions(orderedHeadBlocks.inOrder(), logicalFactory);
					if(funcBlocks == null)
						throw new BlockCodeCompilerErrorException();
					for(GraphicalBlock b : funcBlocks) {
						System.err.println(lineLoc + ": " + b.toString());
						b.setLineNumber(lineLoc);
						lineLoc = b.putInHashMap(lineLocations);
					}
					List<LogicalBlock> orderedBlocks = new ArrayList<>();
					for(GraphicalBlock func : funcBlocks) {
						orderedBlocks.add(func.getLogicalBlock());
					}
					String ret = mainClass.run(orderedBlocks, lineLocations); //Runs the code
					OutputView.output(ret, new Stage());
				} catch (BlockCodeCompilerErrorException e1) {
					OutputView.output("There was an error in your code. We attempted to identify the problem but may not have been successful in doing so.", new Stage());
				}
			}
		});
		changeLanguage = new Button("change language");
		changeLanguage.setMaxHeight(runButtonHeight);
		changeLanguage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mainClass.reset(); //Changing languages requires a reset
			}
		});
		Button export = new Button("export");
		export.setOnMouseClicked(new EventHandler<MouseEvent>() {
			//Didn't comment the second time because it's the same as running but you call mainClass.export() instead of mainClass.run()
			@Override
			public void handle(MouseEvent e) {
				HashMap<Integer, GraphicalBlock> lineLocations = new HashMap<>();
				BSTree<GraphicalBlock> orderedHeadBlocks = new BSTree<>();
				int lineLoc = baseLineNumber;

				for(GraphicalBlock b : blocks) { //Goes through all the block
					b.untag(); //Ensures that error tags from previous methods don't stay
					if(b.getNestedIn() == null) {
						if(b.getAbove() == null)
							orderedHeadBlocks.add(b); //Ordering by height of head blocks (aren't nested in or attached to something)
					}
				}
				if(orderedHeadBlocks.inOrder() == null) {
					OutputView.output("Please create some blocks before exporting", new Stage());
					return;
				}
				List<GraphicalBlock> funcBlocks = guiFactory.sortFunctions(orderedHeadBlocks.inOrder(), logicalFactory);
				for(GraphicalBlock b : funcBlocks) {
					b.setLineNumber(lineLoc);
					lineLoc = b.putInHashMap(lineLocations);
				}
				List<LogicalBlock> orderedBlocks = new ArrayList<>();
				for(GraphicalBlock func : funcBlocks) {
					try {
						orderedBlocks.add(func.getLogicalBlock());
					} catch (BlockCodeCompilerErrorException e1) {
						e1.printStackTrace();
						System.exit(1);
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

	public VBox setUpPalette(double width, double height) {
		VBox palette = new VBox();
		palette.setSpacing(width/5);
		palette.setPadding(new Insets(3 * width/20));
		palette.setMinWidth(width);
		palette.setMinHeight(height);
		palette.setBackground(
				new Background(new BackgroundFill(Color.rgb(128, 209, 255, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));


		for (GraphicalBlock b : guiFactory.getAllBlocks()) {
			CreateHandler handler = new CreateHandler(b);
			b.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
			b.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
			b.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);
			//These handlers are used to create blocks
			palette.getChildren().add(b);
		}

		return palette;
	}

	private class CreateHandler implements EventHandler<MouseEvent> { //Handler for the blocks in palette

		GraphicalBlock block;

		GraphicalBlock current;

		public CreateHandler(GraphicalBlock b) {
			block = b;
		}

		@Override
		public void handle(MouseEvent e) {
			if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
				current = block.cloneBlock(); //Creates block. Dragging now affects the new block
				Workspace.this.getChildren().add(current);

				current.setLayoutX(e.getSceneX());
				current.setLayoutY(e.getSceneY());

				BlockHandler handler = new BlockHandler(current);
				current.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
				current.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
				current.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);
				current.setLogicalFactory(logicalFactory);
			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) { //Drag listener
				current.setLayoutX(e.getSceneX());
				current.setLayoutY(e.getSceneY());

			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				if (palette.contains(e.getSceneX(), e.getSceneY()) || e.getSceneX() < palette.getWidth()) {
					Workspace.this.getChildren().remove(current);
					return;
				} //Remove block if the mouse is to the left of the pallette | play area border

				blocks.add(current); //Need to add the new block to the list of blocks
				for (GraphicalBlock b : blocks) { //Need to check for nesting and attaching
					if (b == current)
						continue;
					Point2D checkPoint = new Point2D(b.getLayoutX(), b.getLayoutY() + b.getMaxHeight());
					Point2D clickPoint = new Point2D(current.getLayoutX(), current.getLayoutY());
					double distance = checkPoint.distance(clickPoint);
					if (distance < bindDistance && (b.getBelow() == null)) { //Check for attaching
//						current.setAbove(b);
						current.bindTo(b);
//						b.setBelow(current);
						break;
					}

					Point2D[] nestables = b.getNestables();
					for (int i = 0; i < nestables.length; i++) { //Check for nesting
						distance = clickPoint.distance(nestables[i]);
						if (distance < nestDistance) {
							try {
								b.nest(i, current);
							} catch (InvalidNestException invalidNestException) {
								continue;
							}

						}
					}
				}

			}

		}

	}

	private class BlockHandler implements EventHandler<MouseEvent> { //Handlers for clicking and dragging blocks when it's already created

		private GraphicalBlock block;
		private double offsetX, offsetY;
		private List<GraphicalBlock> clickSequence;
		private List<GraphicalBlock> ignoring;

		public BlockHandler(GraphicalBlock b) {
			block = b;
		}

		@Override
		public void handle(MouseEvent e) {
			if (block.ignoreStatus() && !e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) //Avoids ripple effect of nested blocks
				return;
			else
				block.actionIgnored();
			if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
				GraphicalBlock add = block;

				clickSequence = new ArrayList<>(); //so that all blocks attached to this block are kept track of
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

//				GraphicalBlock above = block.getAbove();
//				if (above != null)
//					above.setBelow(null); // Allows for things to be bound to "above"

//				block.setAbove(null);//detaches block
				block.unbind();


				GraphicalBlock nestedIn = block.getNestedIn(); //If the block is nested in something
				if (nestedIn != null) {
					try {
						offsetX = 0;
						offsetY = 0;
						VBox nestBox = (VBox) block.getParent();
						for (GraphicalBlock block : clickSequence) { //unnests all blocks in clickSequence from the parent block
							nestedIn.unnest(nestBox, block);
							Workspace.this.getChildren().add(block);
							block.setNestedIn(null);
						}
						block.setLayoutX(e.getSceneX() - offsetX);
						block.setLayoutY(e.getSceneY() - offsetY);
						GraphicalBlock ignore = nestedIn;
						ignoring = new ArrayList<>();
						do { //Need to iterate at least once so do while
							ignoring.add(ignore);
							ignore.ignoreNextAction();
							ignore = ignore.getNestedIn();
						} while (ignore != null);
					} catch (ClassCastException | InvalidNestException castException) {
						//TODO this should never happen. Maybe institute a failsafe here
						castException.printStackTrace();
					}

				}

			} else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) { //drag listener
				block.setLayoutX(e.getSceneX() - offsetX);
				block.setLayoutY(e.getSceneY() - offsetY);


			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {

				if (ignoring != null) { //Need to unignore all listeners being ignored, otherwise problems occur
					for (GraphicalBlock b : ignoring) {
						b.actionIgnored();
					}
					ignoring = null;
				}

				//remove block if block dragged to the remove area (palette) or the left of it
				if (e.getSceneX() < palette.getWidth() || palette.contains(e.getSceneX() - offsetX, e.getSceneY() - offsetY) || palette.contains(e.getSceneX(), e.getSceneY())) {
					for (GraphicalBlock rem : clickSequence) {
						remove(rem);
					}
					return;
				} else {
					for (GraphicalBlock b : blocks) { //Need to check for attaching and listening
						if (b == block)
							continue;
						Point2D clickPoint = new Point2D(block.getLayoutX(), block.getLayoutY());
						Point2D point = new Point2D(b.getLayoutX(), b.getLayoutY() + b.getMaxHeight());
						double distance = point.distance(new Point2D(block.getLayoutX(), block.getLayoutY()));
						//need to make sure you're not looking at the block itself or the block below it. Otherwise, you get a cycle
						if (b == block || b.getAbove() == block)
							continue;
						if (distance < bindDistance && (b.getBelow() == null)) {
//							b.setBelow(block); //attaches block if close enough
//							block.setAbove(b);
							block.bindTo(b);
							break;
						}

						Point2D[] nestables = b.getNestables();
						boolean cycle = false; //need to make sure you're not cycling in nesting
						GraphicalBlock cycleCheck = b;
						while (cycleCheck.getNestedIn() != null && !cycle) {
							cycle = b == block;
							cycleCheck = cycleCheck.getNestedIn();
						}
						if (!cycle) {
							for (int i = 0; i < nestables.length; i++) { //checks for nesting
								distance = clickPoint.distance(nestables[i]);

								if (distance < nestDistance) { //This means it's close enough to nest
									//This needs to be done before returning
									try {
										b.nest(i, block); //Done before for loop in case there's an error on the first one
										for (GraphicalBlock add : clickSequence) {
											if (add == block)
												continue;
											try {
												if(add.getAbove() != null) { //destroys sequence
													add.unbind();
												}
//												add.setAbove(null);
												add.unbind();
												b.nest(i, add);
											} catch (InvalidNestException innerNestException) { //error here means the nest field can only take one
												add.layoutXProperty().set(add.layoutXProperty().get() + add.maxHeightProperty().get()/4);
												add.layoutYProperty().set(add.layoutYProperty().get() + add.maxHeightProperty().get()/4);
												//Keeps the rest of the sequence intact and offsets the block to indicate nest failure
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




	private void remove(GraphicalBlock rem) { //uses recursion for nested block
		blocks.remove(rem);
		this.getChildren().remove(rem);
		ArrayList<GraphicalBlock> rems = rem.getChildBlocks(); //gets all blocks that are children of rem
		if (rems != null) {
			for (GraphicalBlock recurseRem : rems)
				remove(recurseRem); //recursion
		}
	}

	public void reset( GUIFactory guiFactory, LogicalFactory logicalFactory, Main mainClass, int baseLineNumber) {
		//redoes the necessary parts of constructor in order to reset
		//didn't duplicate comments from constructor
		this.guiFactory = guiFactory;
		this.logicalFactory = logicalFactory;
		palette = setUpPalette(paletteWidth, height - runButtonHeight);
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
						b.untag();
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
				this.getChildren().remove(i);
				i--;
			}

		}
	}

}