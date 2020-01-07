package graphics;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class PrintBlock extends GraphicalBlock {
	private TextField text;
	
	/**
	 * @apiNote O(n)
	 * @author Arjun Khanna
	 */
	public PrintBlock() { //TODO is this O(n) or O(1)
		super(200,40);
		HBox line = new HBox();
		Label label = new Label("print ");
		text = new TextField();
		text.setMaxWidth(140);
		text.setMinWidth(140);
		line.getChildren().addAll(label, text);
		line.setSpacing(5);
		line.setPadding(new Insets(8));
		this.setBackground(new Background(new BackgroundFill(Color.AQUA,CornerRadii.EMPTY,Insets.EMPTY)));
		this.getChildren().add(line);
	}
	
	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.PrintBlock ret = new logicalBlocks.PrintBlock();
		ret.setPrint(text.getText());
		return ret;
	}

	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public GraphicalBlock cloneBlock() {
		return new PrintBlock();
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}


}
