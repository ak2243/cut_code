package graphics;

import logicalBlocks.Block;

public class WhileBlock extends GraphicalBlock{

	public Sequence<GraphicalBlock> commands;
	private AndBlock condition;
	
	/**
	 * @apiNote method efficiency O(infinity)?
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.WhileBlock ret = new logicalBlocks.WhileBlock();
		ret.setCondition((logicalBlocks.OperatorBlock) condition.getLogicalBlock());
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

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
