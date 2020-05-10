package cutcode;

import java.io.IOException;
import java.util.List;

import factories.GUIFactory;
import factories.JavaExecutor;
import factories.LogicalFactory;
import factories.PythonExecutor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Arjun Khanna
 */
public class Main extends Application {
	private GUIFactory guiFactory;
	private LogicalFactory logicalFactory;
	private String filename;
	private String language;
	private Executor executor;
	public static final String ERROR = "An error occured, please try again later.";

	// This method is the one Java always runs first
	public static void main(String[] args) {
		launch(args);
	}

	public void setLanguage(String language) {
		this.language = language;
		if (language.equals("python")) {
			guiFactory = new factories.PythonGUIFactory();
			logicalFactory = new factories.PythonLogicalFactory();
			filename = "program.py";
			executor = new PythonExecutor(filename, "python3");
		} else if (language.equals("java")) {
			guiFactory = new factories.JavaGUIFactory();
			logicalFactory = new factories.JavaLogicalFactory();
			filename = "Program.java";
			executor = new JavaExecutor(filename);
		}

	}


	public String run(List<LogicalBlock> logicalBlocks) {
		try {
			executor.export(logicalBlocks, this.filename);
		} catch (IOException e) {
			return "An unexpected error occured when writing the code. Please ensure that permissions are enabled";
		}
		try {
			return executor.execute();
		} catch (BlockCodeCompilerErrorException e) {
			return "There was an error in your code";
		}
	}



	@Override
	public void start(Stage primaryStage) throws Exception {
		setLanguage("java");
		Workspace workspace = new Workspace(1400, 865.248, guiFactory, logicalFactory, this);
		Scene scene = new Scene(workspace, 1400, 865.248);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}