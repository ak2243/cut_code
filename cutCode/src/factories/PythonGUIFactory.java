package factories;

import cutcode.GraphicalBlock;
import python.*;

public class PythonGUIFactory extends GUIFactory {
	@Override
	public cutcode.GraphicalBlock[] getAllBlocks() {
		GraphicalBlock[] ret = { new GraphicalValueBlock(blockWidth, blockHeight),
				new GraphicalPrintBlock(blockWidth, blockHeight), new GraphicalVariableBlock(),
				new GraphicalMathBinaryOperatorBlock(), new GraphicalBooleanBinaryOperatorBlock(),
				new GraphicalIfBlock(), new GraphicalElseBlock(), new GraphicalWhileBlock() };
		return ret;
	}
}
