package cutcode;

import java.io.IOException;
import java.util.List;

import graphics.GraphicalBlock;
import graphics.Sequence;
import graphics.Workspace;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private static String filename;
	public static final String ERROR = "An error occured, please try again later.";

	// This method is the one Java always runs first
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Workspace workspace = new Workspace(1000, 700, this);
		Scene scene = new Scene(workspace, 1000, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		filename = "program.java";
		Main.run(null);
	}

	/**
	 * 
	 * @param blocks: a list of the sequences of GraphicalBlocks that
	 * @author Arjun Khanna
	 */
	public static String run(List<Sequence<GraphicalBlock>> blocks) {
		Executor executor = new Executor();
		String code = executor.getCode(blocks);
		FileManager manager = new FileManager();
		try {
			manager.setOutput(filename);
			manager.openWriter();
			manager.write(code);
			manager.closeWriter();
		} catch (IOException e) {
			return ERROR;
		}
		return executor.run(filename);
	}

}
