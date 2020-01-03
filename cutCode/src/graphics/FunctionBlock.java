package graphics;

import java.util.HashMap;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logicalBlocks.Block;

public class FunctionBlock extends GraphicalBlock {
	private Sequence<GraphicalBlock> commands;
	private VBox bottomLine;
	private String declaration; // NOTE: this only contains return type and name
	int commandLength;

	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public FunctionBlock() {
		this(true);
	}

	public FunctionBlock(boolean inPalette) {
		super(200, 80);

		this.setPadding(new Insets(10));
		this.setBackground(new Background(new BackgroundFill(Color.web("#D06201"), CornerRadii.EMPTY, Insets.EMPTY)));

		HBox topLine = new HBox();
		topLine.getChildren().addAll(new Label("function"));
		bottomLine = new VBox();
		bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		bottomLine.setMinWidth(40);
		bottomLine.setMinHeight(40);
		this.getChildren().addAll(topLine, bottomLine);
		commands = new Sequence<GraphicalBlock>();

		this.setOnMouseClicked(new MouseHandler());

		if (!inPalette) {

		}
	}
	
	/**
	 * @apiNote O(n^2)
	 * @param commands - all the sequences of blocks to be put in the main method
	 * @author Arjun Khanna
	 */
	public void makeMain(List<Sequence<GraphicalBlock>> commands) {
		declaration = logicalBlocks.FunctionBlock.MAIN;
		for(Sequence<GraphicalBlock> blocks : commands) {
			for(GraphicalBlock block : blocks) {
				this.commands.add(block);
			}
		}
	}

	/**
	 * @apiNote method efficiency O(infinity)?
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.FunctionBlock ret = new logicalBlocks.FunctionBlock();
		ret.setSignature(declaration);
		for (GraphicalBlock g : commands) {
			ret.commands.add(g.getLogicalBlock());
		}
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		return new FunctionBlock(false);
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<GraphicalBlock> getCommands() {
		return commands;
	}

	public void add(GraphicalBlock block) {
		commands.add(block);
		double incrementWidth = block.getMinWidth() - bottomLine.getMinWidth();
		if (incrementWidth > 0) {
			bottomLine.setMinWidth(bottomLine.getMinWidth() + incrementWidth);
			this.setMinWidth(this.getMinWidth() + incrementWidth);
		}
		double incrementHeight = block.getMinHeight() - bottomLine.getMinWidth();
		if (incrementHeight > 0) {
			bottomLine.setMinHeight(bottomLine.getMinHeight() + incrementHeight);
			this.setMinHeight(this.getMinHeight() + incrementHeight);
		}
		bottomLine.getChildren().add(block);
	}

	private class MouseHandler implements EventHandler<MouseEvent> {

		/**
		 * @author Arjun Khanna
		 */
		@Override
		public void handle(MouseEvent event) {
			if (event.getButton() == MouseButton.SECONDARY) {
				BorderPane root = new BorderPane();
				TextField name = new TextField();
				HBox firstLine = new HBox(new Label("Name: "), name);
				HBox secondLine = new HBox(new Label("Return type: "));
				ComboBox<String> returnTypes = new ComboBox<String>();
				returnTypes.getItems().addAll("Number", "String", "Condition");
				secondLine.getChildren().add(returnTypes);

				HBox thirdLine = new HBox(new Label("First Parameter: "));
				ComboBox<String> firstParam = new ComboBox<String>();
				firstParam.getItems().addAll("None", "Number", "String", "Condition");
				firstParam.setValue("None");
				thirdLine.getChildren().add(firstParam);

				HBox fourthLine = new HBox(new Label("First Parameter: "));
				ComboBox<String> secondParam = new ComboBox<String>();
				secondParam.getItems().addAll("None", "Number", "String", "Condition");
				secondParam.setValue("None");
				fourthLine.getChildren().add(secondParam);

				root.setCenter(new VBox(firstLine, secondLine, thirdLine, fourthLine));
				Stage stage = new Stage();
				Scene scene = new Scene(root, 400, 400);
				stage.setScene(scene);
				stage.show();
				HashMap<String, String> returns = new HashMap<String, String>();
				returns.put("Number", "double");
				returns.put("String", "String");
				returns.put("Condition", "boolean");
				returns.put("None", null);
				Button done = new Button("DONE");
				VBox bottom = new VBox(done);
				bottom.setAlignment(Pos.BOTTOM_RIGHT);
				root.setBottom(bottom);
				done.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						stage.close();
						String parameterA = "";
						String parameterB = "";
						if (returns.get(firstParam.getValue()) != null) {
							parameterA = returns.get(firstParam.getValue()) + " $a";
							parameterB = ", ";
						}

						if (returns.get(secondParam.getValue()) != null) {
							parameterB += returns.get(secondParam.getValue()) + " $b";
						}

						declaration = "public static " + returns.get(returnTypes.getValue()) + " " + name.getText()
								+ "(" + parameterA + parameterB + ")";
					}
				});

			}

		}

	}

}
