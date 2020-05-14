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

import java.io.File;
import java.io.IOException;

public class LanguagePicker extends BorderPane {
	private TextField compileInput, runInput;
	private ComboBox<String> languageChoice;
	private String javaDefault, pythonDefault;

	public LanguagePicker(double height, double width, Main mainClass, Stage stage) {
		if(checkValidity("java --help"))
			javaDefault = "java";
		if(checkValidity("python3 --help"))
			pythonDefault = "python3";
		else if(checkValidity("python --help"))
			pythonDefault = "python";

		Label label = new Label("Welcome to Cut Code");
		label.setFont(new Font("Helvetica", 24));
		Label label2 = new Label("Please pick a programming language below");
		label2.setFont(new Font("Helvetica", 14));
		VBox top = new VBox(label, label2);
		top.setAlignment(Pos.TOP_CENTER);

		String[] langChoice = {"java", "python"};
		languageChoice = new ComboBox<>(FXCollections.observableArrayList(FXCollections.observableArrayList(langChoice)));
		VBox options = new VBox(languageChoice);
		options.setAlignment(Pos.CENTER_LEFT);
		languageChoice.setValue("Pick Language");
		languageChoice.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) { //t1 is language choice

				VBox right = new VBox();
				switch (t1) {
					case "java":
						Label javaCompileKeyword = new Label("Compile keyword:");
						compileInput = new TextField(javaDefault + "c");
						HBox javaFirstLine = new HBox(javaCompileKeyword, compileInput);
						Label javaRunKeyword = new Label("Run keyword:");
						runInput = new TextField(javaDefault);
						HBox javaSecondLine = new HBox(javaRunKeyword, runInput);
						right.getChildren().addAll(javaFirstLine, javaSecondLine);
						break;
					case "python":
						Label pythonCompileKeyword = new Label("Compile keyword:");
						runInput = new TextField(pythonDefault);
						HBox pythonFirstLine = new HBox(pythonCompileKeyword, runInput);
						right.getChildren().add(pythonFirstLine);
						break;
				}
				right.setAlignment(Pos.CENTER_RIGHT);
				setRight(right);
			}
		});

		Button run = new Button("Run Cutcode");
		VBox bottom = new VBox(run);
		bottom.setAlignment(Pos.BOTTOM_RIGHT);
		run.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (languageChoice.getValue() != null) {

					if (compileInput == null && checkValidity(runInput.getText() + " --help"))
						mainClass.setLanguage(languageChoice.getValue(), null, runInput.getText());
					else if (checkValidity(runInput.getText() + " --help"))
						mainClass.setLanguage(languageChoice.getValue(), compileInput.getText(), runInput.getText());
					else {
						//TODO consider giving the user a message here
						return;
					}
					mainClass.setUpWorkspace();
				}
			}
		});


		setLeft(options);
		setBottom(bottom);
		setTop(top);
	}

	private boolean checkValidity(String command) { //Need to ensure that the default commands work
		Runtime rt = Runtime.getRuntime();
		Process proc;
		boolean valid;
		try {
			proc = rt.exec(command);
			proc.waitFor();
			int exitVal = proc.exitValue();
			valid = exitVal == 0;
		} catch (IOException | InterruptedException e) {
			valid = false;
		}
		return valid;
	}

	public void reset() {
		setRight(null);
		languageChoice.setValue("Pick Language");
	}

}