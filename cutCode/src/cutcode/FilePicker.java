package cutcode;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilePicker {
	/**
	 * 
	 * @param stage - the stage in which this save file picker should be set up
	 * @return the path for the file the user picked
	 */
	public static String chooseFile(Stage stage) {
		FileChooser fil_chooser = new FileChooser();
		File file = fil_chooser.showSaveDialog(stage); //Java takes care of the file picking for us
		if(file == null) //In case the user presses cancel
			return null;
		else
			return file.getPath();
	}
	/**
	 * 
	 * @param stage - the stage in which this open file picker should be set up
	 * @return the path for the file the user picked
	 */
	public static String pickFile(Stage stage) {
		FileChooser fil_chooser = new FileChooser();
		File file = fil_chooser.showOpenDialog(stage);
		if(file == null)
			return "";
		else
			return file.getPath();
	}
}
