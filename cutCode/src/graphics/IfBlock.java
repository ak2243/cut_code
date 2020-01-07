package graphics;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class IfBlock extends GraphicalBlock implements NestableBlock{

	public Sequence<GraphicalBlock> commands;
	private OperatorBlock condition; //TODO make this OperatorBlock
	
	

	public IfBlock() {
		super(200,80);
		
		
		
		
		
		this.setPadding(new Insets(10));
		this.setBackground(new Background(new BackgroundFill(Color.web("#D06201"),CornerRadii.EMPTY,Insets.EMPTY)));
		
		HBox topLine = new HBox();
		topLine.getChildren().addAll(new Label("if"));
		VBox bottomLine = new VBox();
			bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
		bottomLine.setMinWidth(40);
		bottomLine.setMinHeight(40);
		this.getChildren().addAll(topLine,bottomLine);
		commands = new Sequence<GraphicalBlock>();
	}

	public IfBlock(double width, double height) {
		super(width, height);
		commands = new Sequence<GraphicalBlock>();
	}

	/**
	 * @apiNote O(infinity)
	 * @author Peter Timpane
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.IfBlock ret = new logicalBlocks.IfBlock();
		ret.setCondition((logicalBlocks.OperatorBlock) condition.getLogicalBlock());
		for (GraphicalBlock g : commands) {
			ret.commands.add(g.getLogicalBlock());
		}
		return ret;
	}
	
	@Override
	public GraphicalBlock cloneBlock() {
		return new IfBlock();
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point2D getPrimaryNestPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point2D getSecondaryNestPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void primaryNest(GraphicalBlock block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void secondaryNest(GraphicalBlock block) {
		// TODO Auto-generated method stub
		
	}


}
