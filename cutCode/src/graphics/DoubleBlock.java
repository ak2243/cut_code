package graphics;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class DoubleBlock extends GraphicalBlock{
	
	public double value;
	public String name;
	
	private TextField box1,box2;
	
	public DoubleBlock() {
		super(200,40);
		
		box1 = new TextField();
		box1.setMaxWidth(30);
		box2 = new TextField();
		box2.setMaxWidth(30);
		HBox line = new HBox();
		line.setSpacing(5);
		line.setPadding(new Insets(8));
		line.getChildren().add(new Label("double"));
		line.getChildren().add(box1);
		line.getChildren().add(new Label("="));
		line.getChildren().add(box2);
		
		this.setBackground(new Background(new BackgroundFill(Color.DARKGREEN,CornerRadii.EMPTY,Insets.EMPTY)));
		System.err.println(this.getMinWidth() + "f");
		this.getChildren().add(line);
		
		
	}
	
	public DoubleBlock(double width, double height) {
		super(width,height);
	}
	
	@Override
	public Block getLogicalBlock() {
		// TODO Auto-generated method stub
		logicalBlocks.DoubleBlock ret = new logicalBlocks.DoubleBlock();
		ret.setValue(value);
		ret.setName(name);
		return ret;
	}
	
	@Override
	public GraphicalBlock cloneBlock() {
		return new DoubleBlock();
	}
	
}
