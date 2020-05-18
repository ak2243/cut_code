package factories;

import python.*;
import cutcode.GraphicalBlock;

public class JavaGUIFactory implements GUIFactory{
	@Override
	public cutcode.GraphicalBlock[] getAllBlocks() {
		GraphicalBlock[] ret = {new GraphicalValueBlock(), new GraphicalPrintBlock(), new Java.GraphicalVariableBlock(), new GraphicalMathBinaryOperatorBlock(), new GraphicalBooleanBinaryOperatorBlock(), new GraphicalIfBlock(), new GraphicalElseBlock(), new GraphicalWhileBlock(), new GraphicalBreakBlock()};
		return ret;
	}
}
