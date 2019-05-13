package application;

public class IsBlock<T> extends BooleanOperator<T> {
	
	@Override
	public Boolean execute() {
		try
		{
			return (super.getOperand1().equals(super.getOperand2()));
		}
		catch (NullPointerException e)
		{
			//TODO should this error
			return false;
		}
	}
}
