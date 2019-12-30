package graphics;

import java.util.ArrayList;

import cutcode.BSTree;
import cutcode.Main;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Workspace extends Pane {

	private BSTree<Sequence<GraphicalBlock>> sequences;
	//private Pane playArea;
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
		layout.setLeft(palette);
		
		sequences = new BSTree<Sequence<GraphicalBlock>>();
		
	}

	public VBox setupPalette() {
		VBox palette = new VBox();
		palette.setSpacing(40);
		palette.setPadding(new Insets(30));
		palette.setMinWidth(200);
		
		palette.setMinHeight(this.getMinHeight());
		palette.setBackground(
				new Background(new BackgroundFill(Color.rgb(255, 10, 10, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
		GraphicalBlock[] paletteBlocks = { new IfBlock(), new DoubleBlock() };
		for (GraphicalBlock b : paletteBlocks) {
			
			MouseHandler handler = new MouseHandler(b);
			b.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
			b.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
			b.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);
			
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
				
				current = block.cloneBlock();
				
				//add.setPrefWidth(200);
				//add.setPrefHeight(40);
				Workspace.this.getChildren().add(current);
				double mouseX = e.getSceneX();
				double mouseY = e.getSceneY();

				double blockX = block.getLayoutX();
				double blockY = block.getLayoutY();

				offsetX = mouseX - blockX;
				offsetY = mouseY - blockY;

				
			}else if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				
				Point2D mouse = new Point2D(e.getSceneX(),e.getSceneY());
				if(palette.contains(mouse)) {
					Workspace.this.getChildren().remove(current);
				}else {
					BlockHandler handler = new BlockHandler(current);
					current.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
					current.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
					current.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);
					
					
					boolean connected = false;
					ArrayList<Sequence<GraphicalBlock>> sequencesSorted = sequences.traverse(BSTree.INORDER);
					for(Sequence<GraphicalBlock> s : sequencesSorted){
						double endX = s.getEnd().getLayoutX();
						double endY = s.getEnd().getLayoutY();
						
						if(Math.pow(block.getLayoutY() - endY,2) + Math.pow(block.getLayoutX() - endX, 2) < 100) {
							s.add(block);
							block.setSequence(s);
							block.setLayoutX(endX);
							block.setLayoutY(endY + s.getEnd().getHeight());
							connected = true;
							break;
						}
						System.err.println("Sequence passed w/o connection");
					}
					if(!connected) {
						Sequence<GraphicalBlock> sequence = new Sequence<GraphicalBlock>();
						sequence.add(block);
						block.setSequence(sequence);
						sequences.add(sequence);
						sNumber++;
					}
					System.err.println(sNumber);
				}
			}
			
			current.setLayoutX(e.getSceneX() - offsetX);
			current.setLayoutY(e.getSceneY() - offsetY);
			
		}

	}
	
	private class BlockHandler implements EventHandler<MouseEvent>{
		
		
		private GraphicalBlock block;
		private double offsetX;
		private double offsetY;
		
		public BlockHandler(GraphicalBlock b) {
			block = b;
		}
		
		@Override
		public void handle(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
				
				offsetX = e.getSceneX() - block.getLayoutX();
				offsetY = e.getSceneY() - block.getLayoutY();
				
			}else if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				Point2D mouse = new Point2D(e.getSceneX(),e.getSceneY());
				if(palette.contains(mouse)) {
					Workspace.this.getChildren().remove(block);
				}else {
					
				}
				
			}
			block.setLayoutX(e.getSceneX() - offsetX);
			block.setLayoutY(e.getSceneY() - offsetY);
		}
		
	}

}
