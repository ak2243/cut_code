package factories;

import cutcode.GraphicalBlock;
import python.*;

public class PythonGUIFactory implements GUIFactory {
    @Override
    public cutcode.GraphicalBlock[] getAllBlocks() {
        GraphicalBlock[] ret = {new GraphicalValueBlock(), new GraphicalPrintBlock(), new GraphicalVariableBlock(), new GraphicalMathBinaryOperatorBlock(), new GraphicalBooleanBinaryOperatorBlock(), new GraphicalIfBlock(), new GraphicalElseBlock(), new GraphicalWhileBlock(), new GraphicalBreakBlock()};
        return ret;
    }
}
