package graphics;

import logicalBlocks.Block;

public class AndBlock extends GraphicalBlock {
	private GraphicalBlock leftOperand;
	private GraphicalBlock rightOperand;
	
	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.AndBlock ret = new logicalBlocks.AndBlock();
		ret.setLeftOperand(leftOperand.getLogicalBlock());
		ret.setRightOperand(rightOperand.getLogicalBlock());
		return ret;
	}

	@Override
	public GraphicalBlock cloneBlock() {
		// TODO Auto-generated method stub
		return null;
	}

}
