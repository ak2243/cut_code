package cutcode;

import python.LogicalReturnBlock;
import factories.JavaLogicalFactory;
import factories.LogicalFactory;
import factories.PythonLogicalFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Test extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		LogicalFactory logicalFactory = new PythonLogicalFactory();
		LogicalBlock b = logicalFactory.createReturnBlock(1, null);
		System.err.print(b.toString());
		System.exit(0);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
