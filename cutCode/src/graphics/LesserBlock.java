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

public class LesserBlock extends OperatorBlock {

	
	public LesserBlock() {
		super(30,50);
		HBox line = new HBox();
		op1 = new VBox();
		op1.setMinWidth(70);
		op1.setMinHeight(30);
		op1.setBackground(new Background(new BackgroundFill(Color.rgb(90, 150, 90),CornerRadii.EMPTY,Insets.EMPTY)));
		op2 = new VBox();
		op2.setMinWidth(70);
		op2.setMinHeight(30);
		op2.setBackground(new Background(new BackgroundFill(Color.rgb(90, 150, 90),CornerRadii.EMPTY,Insets.EMPTY)));
		Label l = new Label(" < ");
		l.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
		line.getChildren().addAll(op1, l ,op2);
		this.getChildren().add(line);
		this.setMaxWidth(200);
		this.setStyle("-fx-background-color: D90000");
		this.setPadding(new Insets(10));
	}
	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.LesserBlock ret = new logicalBlocks.LesserBlock();
		if(leftOperand == null)
			ret.setLeftOperand(null);
		else
			ret.setLeftOperand(leftOperand.getLogicalBlock());
		if(rightOperand == null)
			ret.setRightOperand(null);
		else
			ret.setRightOperand(rightOperand.getLogicalBlock());
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub
		return new LesserBlock();
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}


}
