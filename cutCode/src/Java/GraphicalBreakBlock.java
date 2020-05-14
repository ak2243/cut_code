package Java;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class GraphicalBreakBlock extends GraphicalBlock {
	public GraphicalBreakBlock() {
		super(200, 40, 3);
		Label label = new Label("break loop");
		this.getChildren().add(label);
		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(Color.web("#6366B8"), CornerRadii.EMPTY, Insets.EMPTY)));
	}
	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		return logicalFactory.createBreak(indentFactor);
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalBreakBlock();
	}

	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		lineLocations.put(getLineNumber(), this);
		return getLineNumber() + 1;
	}
}
