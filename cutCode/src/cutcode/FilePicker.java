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
	public static String chooseFile(Stage stage) {
		FileChooser fil_chooser = new FileChooser();
		File file = fil_chooser.showSaveDialog(stage);
		return file.getPath();
	}
}
