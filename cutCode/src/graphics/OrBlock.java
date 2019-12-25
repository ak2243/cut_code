package graphics;

import logicalBlocks.Block;

public class OrBlock extends GraphicalBlock {

	private GraphicalBlock leftOperand;
	private GraphicalBlock rightOperand;
	
	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.OrBlock ret = new logicalBlocks.OrBlock();
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
