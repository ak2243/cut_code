package application;

public class OrBlock extends BooleanOperator<Boolean> {
	
	
	@Override
	public Boolean execute() {
		try
		{
			return super.getOperand1() || super.getOperand2();
		}
		catch (NullPointerException e)
		{
			//TODO should this return error?
			return false;
		}
	}
	
}
