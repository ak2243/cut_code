package cutcode;

import factories.LogicalFactory;
import factories.PythonLogicalFactory;

public class Test {
	public static void main(String[] args) {
		LogicalFactory factory = new PythonLogicalFactory();
		LogicalBlock backEndTest = factory.createValue("True");
		LogicalBlock test = factory.createVariable(1, "hi", backEndTest);
		System.err.println(test.toString());
	}
}
