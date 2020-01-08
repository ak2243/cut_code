package logicalBlocks;

public class IntegerBlock extends VariableBlock<Integer> {

	/**
	 * @return the java code to make a double block with the given name and value
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "int " + super.getName() + " = " + super.getValue() + ";" + System.lineSeparator();
	}

}
