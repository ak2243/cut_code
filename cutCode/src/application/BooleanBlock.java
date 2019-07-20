package application;

public class BooleanBlock extends VariableBlock<Boolean> {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return "boolean " + super.getName() + " = " + super.getValue() + ";";
	}

}
