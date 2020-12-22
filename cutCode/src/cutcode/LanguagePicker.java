package cutcode;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class LanguagePicker extends BorderPane {
	private TextField compileInput, runInput;
	private ComboBox<String> languageChoice;
	private String javaDefault, pythonDefault;
	private String language;

	/**
	 * 
	 * @param height desired height of the language picker screen when not in fullscreen mode
	 * @param width desired width of the language picker screen when not in fullscreen mode
	 * @param mainClass the main class which calls this language picker constructor
	 * @param stage the stage for the language picker screen
	 */
	public LanguagePicker(double height, double width, Main mainClass, Stage stage) {
		//Need to check if the default values work
		javaDefault = "java";
		pythonDefault = "python3";

		//Setting up the scene
		Label label = new Label("Welcome to Cut Code");
		label.setFont(new Font("Helvetica", 24));
		Label label2 = new Label("Please pick a programming language below");
		label2.setFont(new Font("Helvetica", 14));
		VBox top = new VBox(label, label2);
		top.setAlignment(Pos.CENTER);

		//Setting up drop down
		String[] langChoice = {"java", "python"};
		languageChoice = new ComboBox<>(FXCollections.observableArrayList(FXCollections.observableArrayList(langChoice)));
		VBox options = new VBox(languageChoice);
		options.setAlignment(Pos.CENTER);
		languageChoice.setValue("Pick Language");
		VBox content = new VBox();
		content.setAlignment(Pos.CENTER);
		content.getChildren().add(options);
		languageChoice.valueProperty().addListener(new ChangeListener<String>() {
			private VBox keywordInput;
			@Override
			public void changed(ObservableValue ov, String t, String t1) { //t1 is language choice
				language = t1;
				keywordInput = new VBox();
				keywordInput.setAlignment(Pos.CENTER);
				if (content.getChildren().size() > 1)
					content.getChildren().remove(1);
				switch (t1) { //Gets keywords from user when they pick a drop down option
					case "java":
						Label javaCompileKeyword = new Label("Compile keyword:");
						compileInput = new TextField(javaDefault + "c");
						Button javaCompilePicker = new Button("...");
						javaCompilePicker.addEventHandler(MouseEvent.MOUSE_CLICKED, new FilePickHandler(compileInput));
						HBox javaFirstLine = new HBox(javaCompileKeyword, compileInput, javaCompilePicker);
						javaFirstLine.setAlignment(Pos.CENTER);
						Label javaRunKeyword = new Label("Run keyword:");
						runInput = new TextField(javaDefault);
						Button javaRunPicker = new Button("...");
						///allows user to use a filepicker to pick executable
						javaRunPicker.addEventHandler(MouseEvent.MOUSE_CLICKED, new FilePickHandler(runInput));
						HBox javaSecondLine = new HBox(javaRunKeyword, runInput, javaRunPicker);
						keywordInput.getChildren().clear();
						keywordInput.getChildren().addAll(javaFirstLine, javaSecondLine);
						javaSecondLine.setAlignment(Pos.CENTER);
						break;
					case "python":
						Label pythonCompileKeyword = new Label("Compile keyword:");
						runInput = new TextField(pythonDefault);
						Button pythonRunPicker = new Button("...");
						pythonRunPicker.addEventHandler(MouseEvent.MOUSE_CLICKED, new FilePickHandler(runInput));
						//this allows the user to use a filepicker to pick the executable
						HBox pythonFirstLine = new HBox(pythonCompileKeyword, runInput, pythonRunPicker);
						pythonFirstLine.setAlignment(Pos.CENTER);
						keywordInput.getChildren().clear();
						keywordInput.getChildren().add(pythonFirstLine);
						break;
				}
				
				content.getChildren().add(keywordInput);
			}
		});

		// Button for the user to launch the program
		Button run = new Button("Run Cutcode");
		VBox bottom = new VBox(run);
		bottom.setAlignment(Pos.BOTTOM_CENTER);
		run.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (languageChoice.getValue() != null) {
					switch (language) {
						//first, figure out what language was picked, then check that the run commands are valid
						case "java":
							if (compileInput != null && runInput != null) {
								if (checkValidity(compileInput.getText(), runInput.getText())) {
									// Language valid. Need to set language and run
									mainClass.setLanguage(language, compileInput.getText(), runInput.getText());
									mainClass.setUpWorkspace();
								}
								else
									err(); //outputs an error message telling tha user to input valid commands
							} else
								err();
							break;
						case "python":
							if (compileInput == null && runInput != null) {
								if (checkValidity(null, runInput.getText())) {
									mainClass.setLanguage(language, null, runInput.getText());
									mainClass.setUpWorkspace();
								}
								else
									err();
							} else
								err();
							break;
					}
					
				}
			}
		});

		
		top.getChildren().add(content);
		setBottom(bottom);
		setTop(top);
	}


	/**
	 * 
	 * @param compile the compile command. If not applicable for the language, use null
	 * @param run the run command. 
	 * @return true if the command is found and false if the command is not found. 
	 * Note: this method will not check if the command points to the necessary language, just that the command exists.
	 * 
	 * 
	 */
	private boolean checkValidity(String compile, String run) { //Need to ensure that the default commands work
		if (compile != null) {
			Runtime rt = Runtime.getRuntime();
			Process p1;
			boolean valid;
			try {
				p1 = rt.exec("which " + compile);
				p1.waitFor();
				int exit = p1.exitValue();
				valid = exit == 0;
				if (!valid) {
					p1 = rt.exec("where " + compile);
					p1.waitFor();
					exit = p1.exitValue();
					valid = exit == 0;
				}
				if (!valid)
					return false;
			} catch (IOException | InterruptedException e) {
				return false;
			}
		}
		Runtime rt = Runtime.getRuntime();
		Process p1;
		boolean valid;
		try {
			p1 = rt.exec("which " + run);
			p1.waitFor();
			int exit = p1.exitValue();
			valid = exit == 0;
			if (!valid) {
				p1 = rt.exec("where " + run);
				p1.waitFor();
				exit = p1.exitValue();
				valid = exit == 0;
			}
			if (!valid)
				return false;
		} catch (IOException | InterruptedException e) {
			return false;
		}		
		
		return true;
	}

	public void reset() { //Resets the language picker
		setRight(null);
		languageChoice.setValue("Pick Language");
	}
	
	public void err() {
		OutputView.output("Please pick valid commands", new Stage());
	}
	private class FilePickHandler implements EventHandler<MouseEvent> {
		private TextField field;
		public FilePickHandler(TextField field) {
			this.field = field;
		}
		@Override
		public void handle(MouseEvent event) {
			FilePicker pick = new FilePicker();
			this.field.setText(pick.pickFile(new Stage()));
			
		}
		
	}
}