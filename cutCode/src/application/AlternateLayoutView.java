package application;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


import java.util.*;

public class AlternateLayoutView extends Pane{

	private AnchorPane playArea;
	private VBox blockStorage;
	private ArrayList<BlockView> blocks;
	private BorderPane layout;
	
	
	public AlternateLayoutView() {
		layout = new BorderPane();
		layout.setPadding(new Insets(10));
		this.getChildren().add(layout);
		
		
		playArea = new AnchorPane();
		layout.setCenter(playArea);
		
		blockStorage = new VBox();
		layout.setLeft(blockStorage);
		
		
		blocks = new ArrayList<BlockView>();
		
		BlockView addMe;
		
		BlockView ifBlock = new BlockView(30, 40, Color.GREEN);
		
		
		Mouser mouser = new Mouser(this);
		ifBlock.setOnMousePressed(mouser);
		ifBlock.setOnMouseDragged(mouser);
		ifBlock.setOnMouseReleased(mouser);
		blockStorage.getChildren().add(ifBlock);
		
		
	}
	
	private class Mouser implements EventHandler<MouseEvent>{
		private BlockView current;
		private Pane layout;
		
		public Mouser(Pane layout) {
			this.layout = layout;
		}
		
		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			
			EventType<? extends MouseEvent> type = event.getEventType();
			if(type.equals( MouseEvent.MOUSE_PRESSED)) {
				current = new BlockView(30,40, Color.AQUA);
				current.setLayoutX(event.getX());
				current.setLayoutY(event.getY());
				layout.getChildren().add(current);
				
			}else if(type.equals( MouseEvent.MOUSE_DRAGGED)) {
				current.setLayoutX(event.getX());
				current.setLayoutY(event.getY());

			}else if(type.equals( MouseEvent.MOUSE_RELEASED)) {
				BlockHandler handler = new BlockHandler(current);
				current.setOnMousePressed(handler);
				current.setOnMouseDragged(handler);
				current.setOnMouseReleased(handler);
				blocks.add(current);
				current = null;
			}
		}						
	}
	
	private class BlockHandler implements EventHandler<MouseEvent>{
		
		private BlockView block;
		private double anchorX, anchorY;
		
		public BlockHandler(BlockView block) {
			this.block = block;
		}
		
		@Override
		public void handle(MouseEvent event) {			
			EventType<? extends MouseEvent> type = event.getEventType();
			if(type.equals( MouseEvent.MOUSE_PRESSED)) {
				anchorX = event.getX();
				anchorY = event.getY();
			}else if(type.equals( MouseEvent.MOUSE_DRAGGED)) {
				
				this.block.setLayoutX(this.block.getLayoutX()+event.getX()-anchorX);
				this.block.setLayoutY(this.block.getLayoutY()+event.getY()-anchorY);
				System.err.println("(" + event.getX() + ", " + event.getY());
			}else if(type.equals( MouseEvent.MOUSE_RELEASED)) {
				anchorX = anchorY = 0;
			}
		}
	}
	
}
