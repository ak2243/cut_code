package graphics;

import cutcode.Block;

public class DoubleBlock extends GraphicalBlock{
	
	private double value;
	
	public DoubleBlock() {
		super()
	}
	
	public DoubleBlock(double width, double height) {
		super(width,height);
	}
	
	@Override
	public Block getLogicalBlock() {
		// TODO Auto-generated method stub
		cutcode.DoubleBlock ret = new cutcode.DoubleBlock();
		ret.setValue(value);
		return ret;
	}
	
}
