package graphics;

import logicalBlocks.Block;

public class GreaterEqualBlock extends OperatorBlock {

	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.GreaterEqualBlock ret = new logicalBlocks.GreaterEqualBlock();
		ret.setLeftOperand(getLeftOperand().getLogicalBlock());
		ret.setRightOperand(getRightOperand().getLogicalBlock());
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
