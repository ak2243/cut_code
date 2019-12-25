package logicalBlocks;

public abstract class VariableBlock<T> implements Block { //Class for logical variable blocks
	private T value; //can be of any type. In this program, only String, Double, or Boolean
	private String varName; //the object stores the variable name as a String

	/**
	 * @param s - to become the name of the variable
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public void setName(String s) {
		varName = s;
	}

	/**
	 * 
	 * @return the name of the variable
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public String getName() { //Getting the name
		return varName;
	}

	/**
	 * 
	 * @param val - to become the value of the variable
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public void setValue(T val) { //sets the value
		value = val;
	}
	
	/**
	 * 
	 * @return the name of the variable
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	public T getValue()
	{
		return value;
	}

	@Override
	public abstract String toString();

}
