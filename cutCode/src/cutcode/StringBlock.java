package cutcode;

public class StringBlock extends VariableBlock<String> {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return "String " + super.getName() + " = " + super.getValue() + ";";
	}

}
