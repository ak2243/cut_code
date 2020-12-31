package cutcode;

import javafx.application.Application;
import javafx.stage.Stage;

public class Test extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//new FunctionBuilderView();
		String[] types = {"num", "T/F", "str"};
		double width = 193.2;
		FunctionBuilderView funcBuilder = new FunctionBuilderView(types, width * 2.2, width * 2.2);
		funcBuilder.make("test");
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
