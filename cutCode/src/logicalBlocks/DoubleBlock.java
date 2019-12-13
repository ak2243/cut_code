package logicalBlocks;

public class DoubleBlock extends VariableBlock<Double> {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Double " + super.getName() + " = " + super.getValue() + ";" + System.lineSeparator();
	}

}
