package cutcode;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FunctionBuilderView extends Stage {
	private List<FunctionBuilderRow> rows;
	private double width, height;
	private String[] types;
	private BorderPane root;
	private Scene scene;
	private ComboBox<String> retType;

	public FunctionBuilderView(String[] types, double width, double height) {
		rows = new ArrayList<FunctionBuilderRow>();
		this.width = width;
		this.height = height;
		this.types = types;

		root = new BorderPane();
		scene = new Scene(root, width, height);
		this.setScene(scene);
	}

	public List<FunctionBuilderRow> getRows() {
		return rows;
	}
	
	public String getRetType() {
		return this.retType.getValue();
	}

	public void make(String title) {
		root.setPadding(new Insets(width * 0.06));

		// STEP 1: Need to add a title at the top with the name of the function
		Label label = new Label(title);
		label.setFont(new Font("Helvetica", height * 0.05));
		HBox top = new HBox(label);
		top.setAlignment(Pos.CENTER);
		root.setTop(top);

		// STEP 2: Center needs to be a scrollpane of rows
		ScrollPane middle = new ScrollPane();
		Label paramLabel = new Label("Parameters");
		paramLabel.setFont(new Font("Helvetica", height * 0.04));
		VBox rowBox = new VBox();
		if(types != null) {
			Label retLabel = new Label("Return: ");
			retLabel.setFont(paramLabel.getFont());
			retType = new ComboBox<String>(FXCollections.observableArrayList(FXCollections.observableArrayList(types)));
			HBox retBox = new HBox(retLabel, retType);
			rowBox.getChildren().add(retBox);
		}
		rowBox.getChildren().add(paramLabel);
		rowBox.setAlignment(Pos.TOP_LEFT);
		middle.setContent(rowBox);
		middle.setMaxSize(width * 0.88, height * 0.6);
		middle.setMinSize(width * 0.88, height * 0.6);
		root.setCenter(middle);

		// STEP 3: Need to set bottom to be buttons for adding and removing parameters
		Button add = new Button("+");
		Button rem = new Button("-");
		add.setMaxSize(width * 0.44, height * 0.2);
		add.setMinSize(width * 0.44, height * 0.2);
		rem.setMaxSize(width * 0.44, height * 0.2);
		rem.setMinSize(width * 0.44, height * 0.2);
		add.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				FunctionBuilderRow addRow = new FunctionBuilderRow(types, width * 0.84, height * 0.1);
				rows.add(addRow);
				rowBox.getChildren().add(addRow);
			}

		});
		
		rem.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (rows.size() > 0) {
					FunctionBuilderRow remRow = rows.remove(rows.size() - 1);
					rowBox.getChildren().remove(remRow);
				}
			}
		});

		HBox bottom = new HBox(add, rem);
		root.setBottom(bottom);

		this.setResizable(false);
		this.show();
	}

}
