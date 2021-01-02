package python;

import java.util.ArrayList;
import java.util.HashMap;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;
import cutcode.LogicalBlock;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GraphicalFunctionCallBlock extends GraphicalBlock {
	private double initWidth, initHeight;
	private VBox[] nestBoxes;
	private TextField field;
	private HashMap<VBox, double[]> nestDimensions;

	public GraphicalFunctionCallBlock(double width, double height) {
		super(width, height);
		this.initWidth = width;
		this.initHeight = height;
		nestBoxes = new VBox[1];
		nestDimensions = new HashMap<>();

		Label label = new Label("call");
		label.setTextFill(Color.WHITE);
		this.field = new TextField();
		field.setMinWidth(initWidth / 2);
		field.setMaxWidth(field.getMinWidth());
		field.setMinHeight(initHeight / 3);
		field.setMaxHeight(field.getMinHeight());
		HBox topLine = new HBox(label, field);

		VBox runSpace = new VBox();
		runSpace.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		runSpace.setMinWidth(initWidth * 0.88);
		runSpace.setMaxWidth(runSpace.getMinWidth());
		runSpace.setMinHeight(initHeight / 3);
		runSpace.setMaxHeight(runSpace.getMinHeight());
		nestBoxes[0] = runSpace;
		double[] dimensions = { runSpace.getMinWidth(), runSpace.getMinHeight() };
		nestDimensions.put(runSpace, dimensions);

		this.setBackground(new Background(new BackgroundFill(Color.web("#363CAB"), CornerRadii.EMPTY, Insets.EMPTY)));
		topLine.setSpacing(height / 5);
		this.setPadding(new Insets(height / 5));
		this.getChildren().addAll(topLine, runSpace);
	}

	@Override
	public Point2D[] getNestables() {
		Point2D[] ret = new Point2D[nestBoxes.length];
		double secondaryIncrementY = 0; // need to account for blocks already nested (makes it bottom right)
		for (Node n : nestBoxes[0].getChildren())
			secondaryIncrementY += ((GraphicalBlock) n).getHeight();
		ret[0] = nestBoxes[0].localToScene(nestBoxes[0].getLayoutBounds().getMinX(),
				nestBoxes[0].getLayoutBounds().getMinY() + secondaryIncrementY);
		return ret;
	}

	@Override
	public VBox[] getNestBoxes() {
		return nestBoxes;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new GraphicalFunctionCallBlock(this.initWidth, this.initHeight);
	}

	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		ArrayList<LogicalBlock> paramBlocks = null;
		if (nestBoxes[0].getChildren().size() > 0) {
			paramBlocks = new ArrayList<>();
			for (Node n : nestBoxes[0].getChildren()) {
				((GraphicalBlock) n).setIndentFactor(this.indentFactor + 1);
				paramBlocks.add(((GraphicalBlock) n).getLogicalBlock());
			}
		}
		boolean independent = this.getNestedIn() == null;
		System.err.println(independent + " " + this.getNestedIn());
		return logicalFactory.createFunctionCallBlock(getIndentFactor(), field.getText(), paramBlocks, independent);
	}

	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		if (index == 0) {
			VBox box = nestBoxes[0];
			increment(box, nest);
			box.getChildren().add(nest);
		} else
			throw new InvalidNestException();
		nest.setNestedIn(this);
	}

	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		lineLocations.put(getLineNumber(), this);
		if(this.getNestedIn() == null) {
			return getLineNumber() + 1;
		} else {
			return 0;
		}
	}

	@Override
	public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
		box.getChildren().remove(rem);
		double[] dimensions = nestDimensions.get(box);
		if (dimensions != null && dimensions.length == 2) {
			rem.minHeightProperty().removeListener(super.heightListeners.get(rem));
			rem.minWidthProperty().removeListener(super.widthListeners.get(rem));
			super.heightListeners.remove(rem);
			super.widthListeners.remove(rem);

			box.getChildren().remove(rem);
			double boxHeight = box.getMaxHeight();
			if (box.getChildren().size() > 0) {
				double newBoxWidth = 0;
				for (Node n : box.getChildren()) {
					GraphicalBlock b = (GraphicalBlock) n;
					if (b.getMaxWidth() > newBoxWidth) {
						newBoxWidth = b.getMaxWidth();
					}
				}
				double deltaWidth = box.getMaxWidth() - newBoxWidth;
				double deltaHeight = rem.getMinHeight(); // no need for subtraction since there's more than one nested
															// block
				box.setMaxWidth(newBoxWidth);
				box.setMinWidth(box.getMaxWidth());
				box.setMaxHeight(boxHeight - rem.getMinHeight());
				box.setMinHeight(box.getMaxHeight());
				this.setSize(this.getMaxWidth() - deltaWidth, this.getMinHeight() - deltaHeight);
			} else {
				box.setMaxWidth(dimensions[0]);
				box.setMinWidth(box.getMaxWidth());
				box.setMaxHeight(dimensions[1]);
				box.setMinHeight(box.getMaxHeight());
				this.setSize(initWidth, initHeight);
			}
		}

	}

}
