package graphics;

import logicalBlocks.Block;

public class StringBlock extends GraphicalBlock {
	public String value;
	public String name;
	/**
	 * @author Arjun Khanna
	 * @apiNote O(1)
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
