package graphics;

import logicalBlocks.Block;

public class LesserEqualBlock extends OperatorBlock {

	/**
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public Block getLogicalBlock() {
		logicalBlocks.LesserEqualBlock ret = new logicalBlocks.LesserEqualBlock();
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
