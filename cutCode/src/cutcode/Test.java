package cutcode;

import java.util.ArrayList;
import java.util.List;

import factories.JavaLogicalFactory;
import factories.LogicalFactory;
import factories.PythonLogicalFactory;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Java.LogicalFunctionBlock;

public class Test extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//new FunctionBuilderView();
		LogicalFactory factory = new PythonLogicalFactory();
		List<LogicalBlock> blocks = new ArrayList<LogicalBlock>();
		int fact = 1;
		String[] params = {"int x", "double y", "String z", "boolean b"};
		blocks.add(factory.createPrint(0, factory.createValue("\"hello world\"")));
		for(LogicalBlock b : blocks) {
			b.setIndentFactor(fact + 1);
		}
		LogicalBlock block = factory.createFunctionBlock(fact, "test", "double", params, blocks);
		System.err.println(block.toString());
		System.exit(0);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
