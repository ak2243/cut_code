package application;

public class VariableBlock<T> extends Block<T> {
	T value;
	String varName;

	public void setName(String s) {
		varName = s;
	}

	public String getName() {
		return varName;
	}

	public void setValue(T val) {
		value = val;
	}

	@Override
	public T execute() {
		// TODO Auto-generated method stub
		return value;
	}

}
