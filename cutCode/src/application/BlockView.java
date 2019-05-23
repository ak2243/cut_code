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
	private BlockView nextBlock; //The block attached to the current one
	private BlockView nestedIn; //used to store the blocks inside an if statement
	private BlockView blockAbove;

	public BlockView(int width, int height, String ID, boolean inPalette) {

		this.setId(ID); //determines the kind of block so that it can be styled by the css
		this.setAlignment(Pos.CENTER); //centers the text in the BlockView
		this.setMinHeight(height); //sets height and width
		this.setMinWidth(width);
		if (!inPalette) { //Blocks in the palette only have the kind of block as text in them
			if (ID.equals("if")) { //sets up the design for if blocks
				makeIf();
			} else if (ID.equals("print")) { //sets up design for print blocks
				makePrint();
			} else if (ID.equals("variable")) { //sets up design for variable blocks
				makeVar();
			}
		} else {
			Label label = new Label(ID);
			this.getChildren().add(label);
		}
	}

	public BlockView getNextBlock() { //returns the block that this is attached to
		return nextBlock;
	}

	public void setNextBlock(BlockView nextBlock) { //sets the block attached to this one. Null if none
		this.nextBlock = nextBlock;
	}

	public BlockView getBlockAbove() { //gets the block that this attached to
		return blockAbove;
	}

	public void setBlockAbove(BlockView blockAbove) { //sets the block that this is attached to. Null if none
		this.blockAbove = blockAbove;
	}

	public void makeIf() { //sets up design for if statements
		Label label = new Label("if"); //To show that it's an if-block
		this.getChildren().add(label);
		
		TextField firstOperand = new TextField();
		firstOperand.setText("1st Operand"); //If statements in Cut_Code most have two and only two operands
		firstOperand.setMaxWidth(100); //makes sure the text field is not too wide
		this.getChildren().add(firstOperand); //makes TextField visible in the BlockView
		
		ComboBox<String> c = new ComboBox<String>(); //Drop-down for the operator
		c.getItems().addAll("==", "&&", "||", "<", ">"); //different options for the operator

		this.getChildren().add(c);//Same reasoning as the first TextField
		TextField secondOperand = new TextField();
		secondOperand.setText("2nd Operand");
		secondOperand.setMaxWidth(100);
		this.getChildren().add(secondOperand);
	}

	public void makePrint() {
		Label label = new Label("Print:"); //To show that it's a print block
		TextField toPrint = new TextField(); //To get what is to be printed

		this.getChildren().addAll(label, toPrint); //Adds the two nodes to the block
	}

	public void makeVar() {
		Label label = new Label("Variable"); //To show that it's a variable
		TextField name = new TextField("Name"); //Input for variable name
		name.setMaxWidth(75); //makes sure name input field is not too wide
		Label label2 = new Label("="); 
		TextField val = new TextField("Value"); //Field for the 
		val.setMaxWidth(75);
		this.getChildren().addAll(label, name, label2, val);
	}

	public BlockView getNestedIn() { //used for if statements
		return nestedIn;
	}

	public void setNestedIn(BlockView nestedIn) { //used for if statements
		this.nestedIn = nestedIn;
	}
}
