package application;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;

import javafx.geometry.*;

public class BlockView extends HBox {
	private BlockView nextBlock;
	private BlockView nestedIn;
	private BlockView blockAbove;
	
	public BlockView(int width, int height, String ID, boolean inPalette) {
		// Paint p = new Paint();
		// this.setFill(p);
		this.setId(ID);
		this.setAlignment(Pos.CENTER);
		this.setMinHeight(height);
		this.setMinWidth(width);
		if(!inPalette)
		{
			if(ID.equals("if"))
			{
				makeIf();
			}
		}
	}

	public BlockView getNextBlock() {
		return nextBlock;
	}

	public void setNextBlock(BlockView nextBlock) {
		this.nextBlock = nextBlock;
	}


	public BlockView getBlockAbove() {
		return blockAbove;
	}

	public void setBlockAbove(BlockView blockAbove) {
		this.blockAbove = blockAbove;
	}
	
	public void makeIf()
	{
		Label label = new Label("if");
		this.getChildren().add(label);
		TextField firstOperand = new TextField();
		firstOperand.setText("1st Operand");
		firstOperand.setMaxWidth(100);;
		this.getChildren().add(firstOperand);
		ComboBox<String> c = new ComboBox<String>();
		c.getItems().addAll("==", "&&", "||");
		this.getChildren().add(c);
		TextField secondOperand = new TextField();
		secondOperand.setText("2nd Operand");
		secondOperand.setMaxWidth(100);
		this.getChildren().add(secondOperand);
	}
}
