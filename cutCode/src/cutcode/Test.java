package cutcode;

import java.util.ArrayList;
import java.util.List;

import factories.JavaExecutor;
import factories.JavaLogicalFactory;
import factories.LogicalFactory;
import factories.PythonLogicalFactory;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Java.LogicalFunctionBlock;
import Java.LogicalFunctionCallBlock;

public class Test extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Executor exec = new JavaExecutor("Program.java", "java", "javac");
		LogicalFactory factory = new JavaLogicalFactory();
		List<LogicalBlock> blocks = new ArrayList<LogicalBlock>();
		blocks.add(factory.createPrint(0, factory.createValue("2")));
		exec.export(blocks, "program.py");
	}
	public static void main(String[] args) {
		launch(args);
	}
}
