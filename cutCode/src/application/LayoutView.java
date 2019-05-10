package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.util.*;

public class LayoutView extends Pane{

	private AnchorPane playArea;
	private VBox blockStorage;
	private ArrayList<BlockView> blocks;
	private BorderPane layout;
	
	
	public LayoutView() {
		layout = new BorderPane();
		layout.setPadding(new Insets(10));
		this.getChildren().add(layout);
		
		
		playArea = new AnchorPane();
		layout.setCenter(playArea);
		
		blockStorage = new VBox();
		layout.setLeft(blockStorage);
		
		blocks = new ArrayList<BlockView>();
		
		BlockView addMe;
		
		Button ifBlock = new Button();
		ifBlock.setPrefWidth(100);
		ifBlock.setPrefHeight(50);
		
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
			System.err.println("Handled");
			EventType<? extends MouseEvent> type = event.getEventType();
			if(type.equals( MouseEvent.MOUSE_PRESSED)) {
				press(event);
			}else if(type.equals( MouseEvent.MOUSE_DRAGGED)) {
				drag(event);
			}else if(type.equals( MouseEvent.MOUSE_RELEASED)) {
				release(event);
			}
		}
		
		private void press(MouseEvent e) {
			current = new BlockView();
			current.setLayoutX(e.getX());
			current.setLayoutY(e.getY());
			layout.getChildren().add(current);
			
		}
		
		private void drag(MouseEvent e) {
			current.setLayoutX(e.getX());
			current.setLayoutY(e.getY());
			
		}
		
		private void release(MouseEvent e) {
			BlockHandler handler = new BlockHandler(current);
			current.setOnMousePressed(handler);
			current.setOnMouseDragged(handler);
			current.setOnMouseReleased(handler);
			
			blocks.add(current);
			current = null;
			
			
		}
		
	}
	
	private class BlockHandler implements EventHandler<MouseEvent>{
		
		private BlockView block;
		
		public BlockHandler(BlockView block) {
			this.block = block;
		}
		
		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			
			System.out.println("(" + event.getX() + ", " + event.getY() + ")");
			
			EventType<? extends MouseEvent> type = event.getEventType();
			if(type.equals( MouseEvent.MOUSE_PRESSED)) {
				this.press(event);
			}else if(type.equals( MouseEvent.MOUSE_DRAGGED)) {
				this.drag(event);
			}else if(type.equals( MouseEvent.MOUSE_RELEASED)) {
				this.release(event);
			}
		}
		
		private void press(MouseEvent e) {
			
			
		}
		
		private void drag(MouseEvent e) {
			this.block.setLayoutX(e.getX());
			this.block.setLayoutY(e.getY());
			System.err.println("" + e.getX() + ", " + e.getY());
			
		}
		
		private void release(MouseEvent e) {
			
			
			
		}
		
		
	}
	
}
