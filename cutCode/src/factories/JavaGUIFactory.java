package factories;

import Java.*;

import java.util.ArrayList;
import java.util.List;

import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;

public class JavaGUIFactory extends GUIFactory {
	@Override
	public cutcode.GraphicalBlock[] getAllBlocks() {
		GraphicalBlock[] ret = { new python.GraphicalValueBlock(blockWidth, blockHeight),
				new python.GraphicalPrintBlock(blockWidth, blockHeight),
				new GraphicalVariableBlock(blockWidth, blockHeight),
				new python.GraphicalMathBinaryOperatorBlock(blockWidth, blockHeight),
				new python.GraphicalBooleanBinaryOperatorBlock(blockWidth, blockHeight),
				new python.GraphicalIfBlock(blockWidth, blockHeight*2),
				new python.GraphicalElseBlock(blockWidth, blockHeight * 2),
				new python.GraphicalWhileBlock(blockWidth, blockHeight * 2),
				new GraphicalFunctionBlock(blockWidth, blockHeight * 2),
				new python.GraphicalReturnBlock(blockWidth, blockHeight),
				new python.GraphicalFunctionCallBlock(blockWidth, blockHeight * 2)};
		return ret;
	}

	@Override
	public List<GraphicalBlock> sortFunctions(List<GraphicalBlock> blocks, LogicalFactory factory) {
		List<GraphicalBlock> ret = new ArrayList<GraphicalBlock>();
		MainFunctionBlock firstFunc = new MainFunctionBlock(0,0);
		firstFunc.setLogicalFactory(factory);
		for(GraphicalBlock block : blocks) {
			if(block instanceof GraphicalFunctionBlock) {
				ret.add(block);
			} else {
				try {
					for (GraphicalBlock curr = block; curr != null; curr = curr.getBelow())
						firstFunc.nest(0, curr);

				} catch (InvalidNestException e) {
					return null;
				}
			}
		}
		ret.add(0, firstFunc);
		return ret;
	}
}