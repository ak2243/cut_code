package factories;

import cutcode.GraphicalBlock;
import python.*;

import java.util.ArrayList;

public class PythonGUIFactory implements GUIFactory {
    @Override
    public cutcode.GraphicalBlock[] getAllBlocks() {
        GraphicalBlock[] ret = {new GraphicalBooleanBinaryOperatorBlock(), new GraphicalIfBlock(), new GraphicalPrintBlock(), new GraphicalVariableBlock(), new GraphicalVariableCallBlock()};
        return ret;
    }
}
