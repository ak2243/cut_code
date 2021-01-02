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
		python.GraphicalPrintBlock b = new python.GraphicalPrintBlock(0,0);
		b.nest(0, new python.GraphicalValueBlock(0, 0));
		GraphicalBlock t = new Java.MainFunctionBlock(0,0);
		t.nest(0, b);
		System.err.println(b.getNestedIn());
		
		System.exit(0);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
