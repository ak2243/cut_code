package factories;

import python.*;
import cutcode.GraphicalBlock;

public class JavaGUIFactory extends GUIFactory {
	@Override
	public cutcode.GraphicalBlock[] getAllBlocks() {
		GraphicalBlock[] ret = { new GraphicalValueBlock(blockWidth, blockHeight),
				new GraphicalPrintBlock(blockWidth, blockHeight), new Java.GraphicalVariableBlock(),
				new GraphicalMathBinaryOperatorBlock(), new GraphicalBooleanBinaryOperatorBlock(),
				new GraphicalIfBlock(), new GraphicalElseBlock(), new GraphicalWhileBlock() };
		return ret;
	}
}
