package cutcode;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OutputView {
	public static void output(String output, Stage stage) {
		BorderPane root = new BorderPane();
		root.setCenter(new Label(output));
		Scene scene = new Scene(root, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
}
