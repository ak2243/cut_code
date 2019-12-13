package logicalBlocks;

public class StringBlock extends VariableBlock<String> {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "String " + super.getName() + " = " + super.getValue() + ";" + System.lineSeparator();
	}

}
