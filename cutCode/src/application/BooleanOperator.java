package application;

public abstract class BooleanOperator<T> extends Block<Boolean> {
	private T firstOperand;
	private T secondOperand;
	public void setOperands(T firstOperand, T secondOperand)
	{
		this.firstOperand = firstOperand;
		this.secondOperand = secondOperand;
	}
	
	public T getOperand1()
	{
		return firstOperand;
	}
	
	public T getOperand2()
	{
		return secondOperand;
	}
}
