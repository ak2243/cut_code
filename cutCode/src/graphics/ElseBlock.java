package graphics;

import logicalBlocks.Block;

public class ElseBlock extends GraphicalBlock {
	private Sequence<GraphicalBlock> commands;

	/**
	 * @return the logicalBlock for an else block
	 * @apiNote O(n)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.ElseBlock ret = new logicalBlocks.ElseBlock();
		for (GraphicalBlock g : commands) {
			ret.commands.add(g.getLogicalBlock());
		}
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub
		return null;
	}

}
