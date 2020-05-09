package cutcode;

import factories.LogicalFactory;
import factories.PythonLogicalFactory;

import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		LogicalFactory factory = new PythonLogicalFactory();
		ArrayList<LogicalBlock> prints = new ArrayList<>();
		System.err.print(factory.createVariable(0, "hola", factory.createValue("2")));
		prints.add(factory.createPrint(1, "\"printers\""));
		LogicalBlock firstOperand = factory.createBinaryMathOperator(factory.createValue("10"), "%", factory.createValue("3"));
		LogicalBlock secondOperand = factory.createValue("hola");
		LogicalBlock test = factory.createIf(0, factory.createBinaryBooleanOperator(firstOperand, ">=", secondOperand), prints);
		ArrayList<LogicalBlock> print = new ArrayList<>();
		print.add(factory.createPrint(1, "\"printer\""));
		LogicalBlock tester = factory.createElseBlock(0, print);
		System.err.print(test.toString());
		System.err.print(tester.toString());
	}
}
