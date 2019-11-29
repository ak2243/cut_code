package cutcode;

public abstract class VariableBlock<T> extends Block<String> { //Class for logical variable blocks
	T value; //can be of any type. In this program, only String, Double, or Boolean
	String varName; //the object stores the variable name as a String

	public void setName(String s) { //Setting the name
		varName = s;
	}

	public String getName() { //Getting the name
		return varName;
	}

	public void setValue(T val) { //sets the value
		value = val;
	}
	public T getValue()
	{
		return value;
	}

	@Override
	public abstract String execute();

}
