package application;

public class LesserOperator extends BooleanOperator<Double>{

	@Override
	public Boolean execute() {
		// TODO Auto-generated method stub
		try
		{
			return super.getOperand1() < super.getOperand2();
		}
		catch (NullPointerException e)
		{
			//TODO should this error
			return false;
		}
	}
	

}
