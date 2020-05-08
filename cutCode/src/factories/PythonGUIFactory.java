package factories;

import cutcode.GraphicalBlock;
import python.*;

public class PythonGUIFactory implements GUIFactory {
    @Override
    public cutcode.GraphicalBlock[] getAllBlocks() {
        GraphicalBlock[] ret = {new GraphicalPrintBlock(), new GraphicalValueBlock(), new GraphicalVariableBlock(), new GraphicalMathBinaryOperatorBlock(), new GraphicalBooleanBinaryOperatorBlock(), new GraphicalIfBlock(), new GraphicalWhileBlock(), new GraphicalBreakBlock()};
        return ret;
    }
}
