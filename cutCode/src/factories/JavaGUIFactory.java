package factories;

import Java.*;
import cutcode.GraphicalBlock;

public class JavaGUIFactory implements GUIFactory{
	@Override
	public cutcode.GraphicalBlock[] getAllBlocks() {
		GraphicalBlock[] ret = {new GraphicalPrintBlock(), new GraphicalValueBlock(), new GraphicalVariableBlock(), new GraphicalMathBinaryOperatorBlock(), new GraphicalBooleanBinaryOperatorBlock(), new GraphicalIfBlock(), new GraphicalElseBlock(), new GraphicalWhileBlock(), new GraphicalBreakBlock()};
		return ret;
	}
}
