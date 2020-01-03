package cutcode;

import java.io.IOException;
import java.util.List;

import graphics.FunctionBlock;
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
		Workspace workspace = new Workspace(1000, 700);
		Scene scene = new Scene(workspace, 1000, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
		filename = "program.java";
	}

	/**
	 * 
	 * @param blocks: a list of the sequences of GraphicalBlocks that
	 * @author Arjun Khanna
	 */
	public static String run(List<FunctionBlock> blocks) throws BlockCodeCompilerErrorException {
		Executor executor = new Executor();
		String code = executor.getCode(blocks);
		System.err.println(code);
		FileManager manager = new FileManager();
		try {
			manager.setOutput(filename);
			manager.openWriter();
			manager.write(code);
			manager.closeWriter();
		} catch (IOException e) {
			return ERROR;
		}
		String s = executor.run(filename);
		manager.delete(filename);
		return s;
	}

}
