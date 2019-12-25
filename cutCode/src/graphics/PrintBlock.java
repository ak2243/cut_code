package graphics;

import logicalBlocks.Block;

public class PrintBlock extends GraphicalBlock {
	private String statement;
	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.PrintBlock ret = new logicalBlocks.PrintBlock();
		ret.setPrint(statement);
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub
		return null;
	}

}
