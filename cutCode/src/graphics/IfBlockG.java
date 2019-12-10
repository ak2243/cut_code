package graphics;

import cutcode.*;

public class IfBlockG extends GraphicalBlock {

	private Sequence<GraphicalBlock> commands;
	
	public IfBlockG() {
		super();
		commands = new Sequence<GraphicalBlock>();
	}
	
	public IfBlockG(double width, double height) {
		super(width,height);
		commands = new Sequence<GraphicalBlock>();
	}
	
	@Override
	public Block getLogicalBlock() {
		IfBlock ret = new IfBlock();
		
		return null;
	}

}
