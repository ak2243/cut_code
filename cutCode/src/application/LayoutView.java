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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.*;

/**
 * 
 * @author Peter Timpane
 * @author Arjun Khanna
 */
public class LayoutView extends Pane {

	private VBox blockStorage;// The palate that holds the blocks for usage in code
	private ArrayList<BlockView> blocks;// The ArrayList that holds all blocks
	private BorderPane layout;// The borderpane that holds the blockStorage and the run button;
								// essentially, the layout of the program

	public LayoutView() {

		// Set up the layout and add it to the LayoutView
		layout = new BorderPane();
		layout.setPadding(new Insets(10));
		this.getChildren().add(layout);

		// Set up the blockStorage
		blockStorage = makePalette();
		// Put a border on the blockStorage to make it stand out
		blockStorage.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		// Add the blockStorage to the left side of the screen
		layout.setLeft(blockStorage);

		// Attach an ArrayList
		blocks = new ArrayList<BlockView>();

		// Setup the run button and add it to the screen
		Button run = new Button("RUN");
		run.setOnMousePressed(new RunListener());// Attach a listener
		run.setMinSize(80, 50);
		layout.setRight(run);
	}

	private class Mouser implements EventHandler<MouseEvent> {
		private BlockView current;// The blockView currently being made/posisioned
		private Pane layout;// The layout where the block is; essentially, it just stores the LayoutView
		private String id;// What type the block is, ie "if", "variable", "print"

		public Mouser(Pane layout, String id) {
			this.layout = layout;// Set the layout to the input
			this.id = id;// Set the id to the input
		}

		@Override
		public void handle(MouseEvent event) {
			//Get the type of the event, ex: MousePressed, MouseDragged
			EventType<? extends MouseEvent> type = event.getEventType();
			if (type.equals(MouseEvent.MOUSE_PRESSED)) {

				double mouseX = event.getSceneX();//Get the x and y of the mouse
				double mouseY = event.getSceneY();
				
				//Create the BlockView and add it to the scene
				current = new BlockView(300, 50, id, false);
				layout.getChildren().add(current);
				//Move current to the position where you clicked, giving the impression that you're just dragging it off of the palate
				current.setLayoutX(mouseX - (current.getMinWidth() / 2));
				current.setLayoutY(mouseY - (current.getMinHeight() / 2));

			} else if (type.equals(MouseEvent.MOUSE_DRAGGED)) {
				//Move current with the mouse as you drag. We subtract half the width and height to keep the mouse in the center
				current.setLayoutX(event.getSceneX() - (current.getWidth() / 2));
				current.setLayoutY(event.getSceneY() - (current.getHeight() / 2));

			} else if (type.equals(MouseEvent.MOUSE_RELEASED)) {
				//Attach a new handler to current; this one is on the sample block
				BlockHandler handler = new BlockHandler(current);
				current.setOnMousePressed(handler);
				current.setOnMouseDragged(handler);
				current.setOnMouseReleased(handler);
				//Handle "clicking into place" of the block
				//Use the distance formula to measure the distance between the two blocks
				for (BlockView b : blocks) {
					if (b.getNextBlock() == null && (Math.pow(current.getLayoutX() - b.getLayoutX(), 2)
							+ Math.pow(current.getLayoutY() - (b.getLayoutY() + b.getHeight()), 2) < 1600)) {
						if (b.getId().equals("if")) {
							//Set up the logic of the "if"
							b.setNestedIn(current);
						}
						//Snap the block to the bottom of the block
						current.setLayoutX(b.getLayoutX());
						current.setLayoutY(b.getLayoutY() + b.getHeight());
						b.setNextBlock(current);//Set the blocks below and above 
						current.setBlockAbove(b);
					}
				}
				blocks.add(current);//Add the block to the arraylist
				//Test to see if the block is hovering above the BlockStorage 
				if (this.current.getLayoutY() >= blockStorage.getLayoutY()
						&& this.current.getLayoutY() <= blockStorage.getLayoutY() + blockStorage.getHeight()
						&& this.current.getLayoutX() >= blockStorage.getLayoutX()
						&& this.current.getLayoutX() <= blockStorage.getLayoutX() + blockStorage.getWidth()) {

					//Remove the block from the screen and the arraylist
					LayoutView.this.getChildren().remove(current);
					blocks.remove(current);
				}

				current = null;//Set current to null; it gets set at the top
			}
		}
	}
	
	private class BlockHandler implements EventHandler<MouseEvent> {

		private BlockView block;//The block we're manipulating
		private double anchorX, anchorY;//The places where you first touch down

		public BlockHandler(BlockView block) {
			this.block = block;//Set block to the input
		}

		@Override
		public void handle(MouseEvent event) {
			EventType<? extends MouseEvent> type = event.getEventType();
			if (type.equals(MouseEvent.MOUSE_PRESSED)) {
				anchorX = event.getX();//Set the anchorX and anchorY to the first touch point
				anchorY = event.getY();
				//Handles removing the block from a stack and dis-entangling it from the block, code-wise
				if (block.getNextBlock() != null)//prevents null-pointer exceptions
					block.getNextBlock().setBlockAbove(null);
				block.setNextBlock(null);
				if (block.getBlockAbove() != null) {//prevents null-pointer exceptions
					block.getBlockAbove().setNextBlock(null);
					block.getBlockAbove().setNestedIn(null);
				}
				block.setBlockAbove(null);

			} else if (type.equals(MouseEvent.MOUSE_DRAGGED)) {
				//Drags the block around the screen. Designed to work by moving
				//the block along a delta, or the change in position between where you click down and where you are now
				this.block.setLayoutX(this.block.getLayoutX() + event.getX() - anchorX);
				this.block.setLayoutY(this.block.getLayoutY() + event.getY() - anchorY);
				
			} else if (type.equals(MouseEvent.MOUSE_RELEASED)) {
				anchorX = anchorY = 0;//Reset anchorX and anchorY
				
				//Handle the clicking of blocks into place
				for (BlockView b : blocks) {
					//Use the distance formula to measure the distance between the two blocks
					if (b.getNextBlock() == null && (Math.pow(block.getLayoutX() - b.getLayoutX(), 2)
							+ Math.pow(block.getLayoutY() - (b.getLayoutY() + b.getHeight()), 2) < 1600)) {

						if (b.getId().equals("if")) {
							b.setNestedIn(block);
						}
						//Set up the relations between the two blocks
						block.setLayoutX(b.getLayoutX());
						block.setLayoutY(b.getLayoutY() + b.getHeight());
						b.setNextBlock(block);
						block.setBlockAbove(b);
					}
				}
				//Test if the block is over the blockStorage, and if so, delete it
				if (this.block.getLayoutY() >= blockStorage.getLayoutY()
						&& this.block.getLayoutY() <= blockStorage.getLayoutY() + blockStorage.getHeight()
						&& this.block.getLayoutX() >= blockStorage.getLayoutX()
						&& this.block.getLayoutX() <= blockStorage.getLayoutX() + blockStorage.getWidth()) {
					//remove the block from the screen and the arraylist
					blocks.remove(block);
					LayoutView.this.getChildren().remove(block);
				}
			}
		}
	}
	//Set up the blockStorage
	private VBox makePalette() {
		VBox box = new VBox();
		//Set up spacing and padding
		box.setSpacing(20);
		box.setPadding(new Insets(30));
		//Use an arraylist to hold the different blocks
		ArrayList<BlockView> paletteBlocks = new ArrayList<BlockView>();
		//The blocks that will be in the palate
		paletteBlocks.add(new BlockView(300, 50, "variable", true));
		paletteBlocks.add(new BlockView(300, 50, "print", true));
		paletteBlocks.add(new BlockView(300, 50, "if", true));
		
		//Set the max size of the box
		box.setMaxSize(320, 230);
		for (BlockView block : paletteBlocks) {
			//Set up the handlers
			Mouser mouser = new Mouser(this, block.getId());
			block.setOnMousePressed(mouser);
			block.setOnMouseDragged(mouser);
			block.setOnMouseReleased(mouser);
			//add the block to the box
			box.getChildren().add(block);
		}

		return box;//Return the completed box
	}

	private class RunListener implements EventHandler<MouseEvent> {

		ArrayList<BlockView> heads;//The arraylist that stores the "heads";
								   //blocks that aren't attached above

		public RunListener() {
			heads = new ArrayList<BlockView>();//attach an arraylist to heads
		}

		@Override
		public void handle(MouseEvent event) {
			//Sort the blocks in order of height
			sortBlocks();
			//Create the builder
			Builder builder = new Builder();
			for (int i = 0; i < blocks.size(); i++) {
				BlockView b = blocks.get(i);
				if (b.getBlockAbove() != null && b.getBlockAbove().getId().equals("if")) {
					continue;
					//Don't run the block if it's in an if-block; we let the if-block do that
				}
				if (b.getId().equals("print")) {
					//Handles print blocks
					String input = ((TextField) (b.getChildren().get(1))).getText();
					//Print in the builder
					builder.print(input);
					
				} else if (b.getId().equals("variable")) {
					//Handles variables blocks
					String value = ((TextField) (b.getChildren().get(3))).getText();
					String name = ((TextField) (b.getChildren().get(1))).getText();
					//Create the variable in builder
					builder.createVariable(name, value);
					
				} else if (b.getId().equals("if")) {
					//Handles if blocks
					String operand1 = ((TextField) (b.getChildren().get(1))).getText();
					String operator = ((ComboBox<String>) (b.getChildren().get(2))).getValue();
					String operand2 = ((TextField) (b.getChildren().get(3))).getText();
					//Create the if block in builder
					builder.createIf(operand1, operator, operand2, i);
					try {
						
						builder.getIf(i).addToContents(addToIf(b.getNestedIn(), builder.getBlocks()));
					} catch (NullPointerException e) {
						builder.error();
					}
				}

			}
			
			//Set up a new stage/scene to display the output
			String s = builder.run();
			Stage stage = new Stage();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			Label console = new Label(s);
			root.setCenter(console);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}

		private void sortBlocks() {

			heads.clear();//Clear heads
			for (BlockView b : blocks) {
				//Add the blocks with no blocks above to heads
				if (b.getBlockAbove() == null)
					heads.add(b);
			}
			//Sorts the arrayList by y-value: the lower the value, the lower the position in the array
			//This has the effect of putting the top blocks on the screen first in the array
			Collections.sort(heads, new Comparator<BlockView>() {
				/**
				 * Collections.sort uses the comparator interface.
				 * This was set up to sort based on y-value
				 */
				@Override
				public int compare(BlockView arg0, BlockView arg1) {
					// TODO Auto-generated method stub
					if (arg0.getLayoutY() < arg1.getLayoutY())
						return -1;
					else if (arg0.getLayoutY() > arg1.getLayoutY())
						return 1;
					return 0;
				}

			});
			//Clear blocks
			blocks.clear();
			//Fills the blocks arraylist with with the heads in the arrayList,
			//followed by the rest of the blocks in the chains
			for (int i = 0; i < heads.size(); i++) {
				BlockView b = heads.get(i);
				blocks.add(b);
				while (b.getNextBlock() != null) {
					blocks.add(b.getNextBlock());
					b = b.getNextBlock();
				}
			}

		}
		
		//Handles adding a block to an if statement
		private Block<?> addToIf(BlockView b, ArrayList<Block<?>> blocks) {
			Builder builder = new Builder(blocks);

			if (b.getId().equals("print")) {
				//Set up for a print block
				String input = ((TextField) (b.getChildren().get(1))).getText();
				builder.print(input);
			} else if (b.getId().equals("variable")) {
				//Set up for a variable block
				String value = ((TextField) (b.getChildren().get(3))).getText();
				String name = ((TextField) (b.getChildren().get(1))).getText();
				builder.createVariable(name, value);
			} else if (b.getId().equals("if")) {
				//Set up for an if block
				String operand1 = ((TextField) (b.getChildren().get(1))).getText();
				String operator = ((ComboBox<String>) (b.getChildren().get(2))).getValue();
				String operand2 = ((TextField) (b.getChildren().get(3))).getText();
				builder.createIf(operand1, operator, operand2, 0);
				try {
					System.err.println("Size:" + builder.getBlocks().size());
					builder.getIf(0).addToContents(addToIf(b.getNestedIn(), builder.getBlocks()));
				} catch (NullPointerException e) {
					builder.error();
				}
			}
			return builder.get(0);

		}
	}

}
