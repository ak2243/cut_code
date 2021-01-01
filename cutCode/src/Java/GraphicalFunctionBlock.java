package Java;

import java.util.ArrayList;
import java.util.HashMap;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.FunctionBuilderView;
import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;
import cutcode.LogicalBlock;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GraphicalFunctionBlock extends GraphicalBlock {
	private double initWidth, initHeight;
	private VBox[] nestBoxes;
	private TextField field;
	private HashMap<VBox, double[]> nestDimensions;
	private FunctionBuilderView funcBuilder;
	public static HashMap<String, String> retTypes;
	private boolean inPalette;

	
	public GraphicalFunctionBlock(double width, double height) {
		super(width, height);
		this.initWidth = width;
		this.initHeight = height;
		nestBoxes = new VBox[1];
		nestDimensions = new HashMap<>();
		String[] types = {"void", "num", "T/F", "str"};
		funcBuilder = new FunctionBuilderView(types, width * 2.2, width * 2.2);
		inPalette = false;
		retTypes = new HashMap<>();
		retTypes.put("num", "double");
		retTypes.put("T/F", "boolean");
		retTypes.put("str", "String");
	

		Label label = new Label("func");
		label.setTextFill(Color.WHITE);
		this.field = new TextField();
		field.setMinWidth(initWidth / 2);
		field.setMaxWidth(field.getMinWidth());
		field.setMinHeight(initHeight / 3);
		field.setMaxHeight(field.getMinHeight());
		HBox topLine = new HBox(label, field);

		VBox runSpace = new VBox();
		runSpace.setBackground(
				new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		runSpace.setMinWidth(initWidth * 0.88);
		runSpace.setMaxWidth(runSpace.getMinWidth());
		runSpace.setMinHeight(initHeight / 3);
		runSpace.setMaxHeight(runSpace.getMinHeight());
		nestBoxes[0] = runSpace;
		double[] dimensions = {runSpace.getMinWidth(), runSpace.getMinHeight()};
		nestDimensions.put(runSpace, dimensions);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY) {
					if (inPalette) {
						funcBuilder.make(field.getText());
					}
				}
			}
		});

		this.setBackground(new Background(new BackgroundFill(Color.web("#545ac9"), CornerRadii.EMPTY, Insets.EMPTY)));
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
		GraphicalFunctionBlock ret = new GraphicalFunctionBlock(this.initWidth, this.initHeight);
		ret.inPalette = true;
		return ret;
	}

	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		ArrayList<LogicalBlock> execBlocks = new ArrayList<>();
		for(Node n : nestBoxes[0].getChildren()) {
			//((GraphicalBlock) n).setIndentFactor(this.indentFactor + 1);
			execBlocks.add(((GraphicalBlock) n).getLogicalBlock());
		}
		String[] params = new String[funcBuilder.getRows().size()];
		for(int i = 0; i < params.length; i++) {
			params[i] = retTypes.get(funcBuilder.getRows().get(i).getType()) + " " + funcBuilder.getRows().get(i).getName();
		}
		return logicalFactory.createFunctionBlock(getIndentFactor() - 1, this.field.getText(), retTypes.get(funcBuilder.getRetType()), params, execBlocks);
		

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
		int ret = getLineNumber() + 1;
		for (Node n : nestBoxes[0].getChildren()) {
			if (n instanceof GraphicalBlock) {
				((GraphicalBlock) n).setLineNumber(ret);
				ret = ((GraphicalBlock) n).putInHashMap(lineLocations);
			}
		}
		return ret + logicalFactory.getEndingBrace();
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
