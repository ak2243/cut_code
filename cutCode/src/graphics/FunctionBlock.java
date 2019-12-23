package graphics;

import logicalBlocks.Block;

public class FunctionBlock extends GraphicalBlock{
	private Sequence<GraphicalBlock> commands;
	private String declaration; //NOTE: this only contains return type and 

	/**
	 * @apiNote method efficiency O(infinity)?
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.FunctionBlock ret = new logicalBlocks.FunctionBlock();
		ret.setSignature("public static " + declaration);
		for (GraphicalBlock g : commands) {
			ret.commands.add(g.getLogicalBlock()); //TODO doesn't this have big O(infinity)
		}
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
