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
	private static String filename;
	public static final String ERROR = "An error occured, please try again later.";

	// This method is the one Java always runs first
	public static void main(String[] args) {
		launch(args);
	}

	public void setLanguage(String language) {
		if(language.equals("python")) {
			guiFactory = new factories.PythonGUIFactory();
			logicalFactory = new factories.PythonLogicalFactory();
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		guiFactory = new factories.PythonGUIFactory();
		logicalFactory = new factories.PythonLogicalFactory();
		Workspace workspace = new Workspace(1400, 700, guiFactory);
		Scene scene = new Scene(workspace, 1400, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
		filename = "program.java";
	}

}