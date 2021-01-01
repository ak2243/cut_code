package cutcode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import factories.GUIFactory;
import factories.JavaExecutor;
import factories.LogicalFactory;
import factories.PythonExecutor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Arjun Khanna
 */
public class Main extends Application {
	private GUIFactory guiFactory; //Used for graphical factory design
	private LogicalFactory logicalFactory; //Used for logical factory design
	private String filename; //The filename is "program.*", but it may be changed in the future, so I'm making it a variable
	private Executor executor; //Used to run and export code
	private int baseLineNumber; //Languages like java require class and method declarations, so the initial line number is not always 1
	private Stage workspaceStage;
	private Scene workspaceScene;
	private Workspace workspace;
	private Stage langStage;
	private LanguagePicker langPicker;
	private double maxX, maxY; // max bounds of the screen
	// This method is the one Java always runs first
	public static void main(String[] args) {
		launch(args);
	}

	public void setLanguage(String language,  String compileKeyword, String runKeyword) {
		langStage.close();
		if (language.equals("python")) {
			baseLineNumber = 1; //no class or method declarations in python
			guiFactory = new factories.PythonGUIFactory();
			guiFactory.setBlockSize(maxX * 0.1, maxY/25);
			logicalFactory = new factories.PythonLogicalFactory();
			filename = "program.py";
			executor = new PythonExecutor(filename, runKeyword); //Only a run command in python
		} else if (language.equals("java")) {
			baseLineNumber = 2; 
			guiFactory = new factories.JavaGUIFactory();
			guiFactory.setBlockSize(maxX * 0.1, maxY/25);
			logicalFactory = new factories.JavaLogicalFactory();
			filename = "Program.java";
			executor = new JavaExecutor(filename, compileKeyword, runKeyword); //Run and compile commands in java
		}

	}


	public String run(List<LogicalBlock> logicalBlocks, HashMap<Integer, GraphicalBlock> lineLocations) {
		try {
			executor.export(logicalBlocks, this.filename); //the code needs to be exported to a file first
		} catch (IOException e) {
			return "An unexpected error occured when writing the code. Please ensure that permissions are enabled";
		}
		try {
			return executor.execute(lineLocations); //Run the code in the file written to
		} catch (BlockCodeCompilerErrorException e) {
			return "There was an error in your code";
		}
	}



	@Override
	public void start(Stage primaryStage) throws Exception {
		maxX = Screen.getScreens().get(0).getBounds().getWidth();
		maxY = Screen.getScreens().get(0).getBounds().getHeight();
		langPicker = new LanguagePicker(maxX/5, maxY/4, this, primaryStage);
		Scene scene = new Scene(langPicker, maxX/5, maxY/4);
		langStage = primaryStage;
		langStage.setScene(scene);
		//primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	public void setUpWorkspace() {
		workspaceStage = new Stage();
		if(workspace == null) {
			workspace = new Workspace(maxX - maxX/3, maxY - maxY/3, guiFactory, logicalFactory, this, baseLineNumber);
			workspaceStage.setMaximized(false);
			workspaceStage.setResizable(false); 
			workspaceScene = new Scene(workspace, maxX - maxX/3, maxY - maxY/3);
		} else
			workspace.reset(guiFactory, logicalFactory,  this, baseLineNumber);
		workspaceStage.setScene(workspaceScene);
		workspaceStage.show();

	}

	public void reset() {
		workspaceStage.close();
		langPicker.reset();
		langStage.show();
	}

	public String export(List<LogicalBlock> blocks, String file) throws IOException {
		executor.export(blocks, file);
		return "Export to " + file + " was successful";
	}
}