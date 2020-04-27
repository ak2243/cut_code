package graphics;



import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class PrintBlock extends GraphicalBlock {
	private TextField value;
	public PrintBlock() {
		this(200, 40);
	}
	public PrintBlock(double width, double height) {
		super(width, height);
		HBox firstLine = new HBox();
		firstLine.setSpacing(5);
		firstLine.setPadding(new Insets(8));
		Label label = new Label("print");
		value = new TextField();
		value.setMaxWidth(140);
		firstLine.getChildren().addAll(label, value);
		this.getChildren().add(firstLine);
		
		this.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));

	}
	
	@Override
	public Block getLogicalBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub
		return new PrintBlock();
	}

	@Override
	public Point2D[] getNestables() {
		return new Point2D[0];
	}

	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		throw new InvalidNestException();
		
	}

}
