package cutcode;

import javafx.application.Application;
import graphics.Workspace;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	// This method is the one Java always runs first
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Workspace workspace = new Workspace(1000, 700);
		Scene scene = new Scene(workspace, 1000, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
