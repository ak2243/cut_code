package logicalBlocks;

public class BooleanBlock extends VariableBlock<Boolean> {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Boolean " + super.getName() + " = " + super.getValue() + ";" + System.lineSeparator();
	}

}
