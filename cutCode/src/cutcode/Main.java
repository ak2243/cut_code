package cutcode;

import java.io.IOException;
import java.util.List;

import factories.GUIFactory;
import factories.LogicalFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Arjun Khanna
 *
 */
public class Main extends Application {
	private GUIFactory guiFactory;
	private LogicalFactory logicalFactory;
	private String filename;
	private String language;
	public static final String ERROR = "An error occured, please try again later.";

	// This method is the one Java always runs first
	public static void main(String[] args) {
		launch(args);
	}

	public void setLanguage(String language) {
		this.language = language;
		if(language.equals("python")) {
			guiFactory = new factories.PythonGUIFactory();
			logicalFactory = new factories.PythonLogicalFactory();
			filename = "program.py";
		}

	}

	public String run(List<LogicalBlock> logicalBlocks) {
		try {
			export(logicalBlocks, this.filename);
		} catch (IOException e) {
			return "An unexpected error occured when writing the code. Please ensure that permissions are enabled";
		}
		try {
			Executor executor = new Executor(this.filename, "python3");
			return executor.execute("python");
		} catch(BlockCodeCompilerErrorException e) {
			return "There was an error in your code";
		}
	}


	public void export(String code, String filename) throws IOException {
		FileManager manager = new FileManager();
		manager.delete(filename);
		manager.setOutput(filename);
		manager.openWriter();
		manager.write(code);
		manager.closeWriter();
	}
	public void export(List<LogicalBlock> logicalBlocks, String filename) throws IOException {
		FileManager manager = new FileManager();
		manager.setOutput(filename);
		manager.openWriter();
		for(LogicalBlock block : logicalBlocks)
			manager.write(block.toString());
		manager.closeWriter();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		setLanguage("python");
		Workspace workspace = new Workspace(1400, 865.248, guiFactory, logicalFactory, this);
		Scene scene = new Scene(workspace, 1400, 865.248);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}