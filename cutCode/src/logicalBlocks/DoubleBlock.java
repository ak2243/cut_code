package logicalBlocks;

public class DoubleBlock extends VariableBlock<Double> {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "double " + super.getName() + " = " + super.getValue() + ";" + System.lineSeparator();
	}

}
