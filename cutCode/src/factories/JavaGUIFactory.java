package factories;

import python.*;
import cutcode.GraphicalBlock;

public class JavaGUIFactory extends GUIFactory {
	@Override
	public cutcode.GraphicalBlock[] getAllBlocks() {
		GraphicalBlock[] ret = { new GraphicalValueBlock(blockWidth, blockHeight),
				new GraphicalPrintBlock(blockWidth, blockHeight),
				new Java.GraphicalVariableBlock(blockWidth, blockHeight),
				new GraphicalMathBinaryOperatorBlock(blockWidth, blockHeight),
				new GraphicalBooleanBinaryOperatorBlock(blockWidth, blockHeight),
				new GraphicalIfBlock(blockWidth, blockHeight*2),
				new GraphicalElseBlock(blockWidth, blockHeight * 2),
				new GraphicalWhileBlock(blockWidth, blockHeight * 2),
				new Java.GraphicalFunctionBlock(blockWidth, blockHeight * 2),
				new GraphicalFunctionCallBlock(blockWidth, blockHeight * 2)};
		return ret;
	}
}