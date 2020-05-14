package python;



import cutcode.BlockCodeCompilerErrorException;
import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class GraphicalPrintBlock extends GraphicalBlock {
	private TextField value;
	public GraphicalPrintBlock() {
		this(200, 40);
	}
	public GraphicalPrintBlock(double width, double height) {
		super(width, height, 0);
		HBox firstLine = new HBox();
		firstLine.setSpacing(5);
		firstLine.setPadding(new Insets(8));
		Label label = new Label("print");
		value = new TextField();
		value.setMaxWidth(140);
		firstLine.getChildren().addAll(label, value);
		this.getChildren().add(firstLine);
		
		this.setBackground(new Background(new BackgroundFill(Color.web("#F0A3FF"), CornerRadii.EMPTY, Insets.EMPTY)));

	}
	
	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		return logicalFactory.createPrint(indentFactor, value.getText());
	}

	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub
		return new GraphicalPrintBlock();
	}

	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		lineLocations.put(getLineNumber(), this);
		return getLineNumber() + 1;
	}


}
