package graphics;

import logicalBlocks.Block;

public class BooleanBlock extends GraphicalBlock{
	public boolean value;
	public String name;
	/**
	 * @author Arjun Khanna
	 * @apiNote O(1)
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.BooleanBlock ret = new logicalBlocks.BooleanBlock();
		ret.setValue(value);
		ret.setName(name);
		return ret;
	}
	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub
		return null;
	}
}
