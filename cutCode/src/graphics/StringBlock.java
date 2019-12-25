package graphics;

import logicalBlocks.Block;

public class StringBlock extends GraphicalBlock {
	public String value;
	public String name;
	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.StringBlock ret = new logicalBlocks.StringBlock();
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
