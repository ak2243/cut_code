package cutcode;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OutputView {
	public static void output(String output, Stage stage, double width, double height) {
		BorderPane root = new BorderPane();
		Label out = new Label(output);
		out.setWrapText(true);
		root.setCenter(out);
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.show();
	}
	public static void output(String output, Stage stage) {
		BorderPane root = new BorderPane();
		Label out = new Label(output);
		out.setWrapText(true);
		root.setCenter(out);
		Scene scene = new Scene(root, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
}
