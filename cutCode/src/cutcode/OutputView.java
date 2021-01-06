package cutcode;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class OutputView {
	/**
	 * 
	 * @param output - the string to be outputted
	 * @param stage - the stage for this output window
	 * @param width - the width of the output view
	 * @param height - the height of the output view
	 */
	public static void output(String output, Stage stage, double width, double height) {
		BorderPane root = new BorderPane();
		Label out = new Label(output);
		out.setWrapText(true);
		out.setTextAlignment(TextAlignment.CENTER);
		out.setAlignment(Pos.CENTER);
		root.setCenter(out);
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.show();
	}

}
