package python;

import cutcode.LogicalBlock;
import cutcode.GraphicalBlock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class GraphicalBreakBlock extends GraphicalBlock {
	public GraphicalBreakBlock() {
		super(200, 40);
		Label label = new Label("break loop");
		this.getChildren().add(label);
		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(Color.web("#6F73D2"), CornerRadii.EMPTY, Insets.EMPTY)));
	}
	@Override
	public LogicalBlock getLogicalBlock() {
		return null;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return null;
	}
}
