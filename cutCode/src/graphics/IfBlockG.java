package graphics;

import cutcode.*;
import logicalBlocks.Block;
import logicalBlocks.IfBlock;

public class IfBlockG extends GraphicalBlock {

	private Sequence<GraphicalBlock> commands;

	public IfBlockG() {
		super();
		commands = new Sequence<GraphicalBlock>();
	}

	public IfBlockG(double width, double height) {
		super(width, height);
		commands = new Sequence<GraphicalBlock>();
	}

	@Override
	public Block getLogicalBlock() {
		IfBlock ret = new IfBlock();
		for (GraphicalBlock g : commands) {
			ret.commands.add(g.getLogicalBlock());
		}
		return ret;
	}
}
