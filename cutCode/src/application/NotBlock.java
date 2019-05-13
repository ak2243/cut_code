package application;

public class NotBlock extends BooleanOperator<Boolean> {
	
	
	@Override
	public Boolean execute() {
		try
		{
			return super.getOperand1() == super.getOperand2();
		}
		catch (NullPointerException e)
		{
			//TODO should this return error?
			return false;
		}
	}
	/**
	 * The operator's value is the opposite of the first operand
	 */
	@Override
	public void setOperands(Boolean firstOperand, Boolean secondOperand)
	{
		super.setOperands(firstOperand, false);
	}
	
	public void setOperand(Boolean firstOperand)
	{
		super.setOperands(firstOperand, false);
	}
	
}
