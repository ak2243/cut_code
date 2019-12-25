package logicalBlocks;

public class StringBlock extends VariableBlock<String> {

	/**
	 * @return the Java code to make a String variable with the given name and value
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "String " + super.getName() + " = " + super.getValue() + ";" + System.lineSeparator();
	}

}
