package factories;

import java.util.List;

import cutcode.GraphicalBlock;
import python.GraphicalBooleanBinaryOperatorBlock;
import python.GraphicalElseBlock;
import python.GraphicalFunctionBlock;
import python.GraphicalFunctionCallBlock;
import python.GraphicalIfBlock;
import python.GraphicalMathBinaryOperatorBlock;
import python.GraphicalPrintBlock;
import python.GraphicalValueBlock;
import python.GraphicalVariableBlock;
import python.GraphicalWhileBlock;

public class PythonGUIFactory extends GUIFactory {
	@Override
	public cutcode.GraphicalBlock[] getAllBlocks() {
		GraphicalBlock[] ret = { new GraphicalValueBlock(blockWidth, blockHeight),
				new GraphicalPrintBlock(blockWidth, blockHeight),
				new GraphicalVariableBlock(blockWidth, blockHeight),
				new GraphicalMathBinaryOperatorBlock(blockWidth, blockHeight),
				new GraphicalBooleanBinaryOperatorBlock(blockWidth, blockHeight),
				new GraphicalIfBlock(blockWidth, blockHeight * 2),
				new GraphicalElseBlock(blockWidth, blockHeight * 2),
				new GraphicalWhileBlock(blockWidth, blockHeight * 2),
				new GraphicalFunctionBlock(blockWidth, blockHeight * 2),
				new GraphicalFunctionCallBlock(blockWidth, blockHeight * 2)};
		return ret;
	}

	@Override
	public List<GraphicalBlock> sortFunctions(List<GraphicalBlock> blocks, LogicalFactory factory) {
		return blocks;
	}
}
