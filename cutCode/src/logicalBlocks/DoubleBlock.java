package logicalBlocks;

public class DoubleBlock extends VariableBlock<Double> {

	/**
	 * @return the java code to make a double block with the given name and value
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "double " + super.getName() + " = " + super.getValue() + ";" + System.lineSeparator();
	}

}
