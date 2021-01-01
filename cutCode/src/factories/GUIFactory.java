package factories;

import java.util.List;

import cutcode.GraphicalBlock;

public abstract class GUIFactory {
    public abstract cutcode.GraphicalBlock[] getAllBlocks();
    protected double blockHeight, blockWidth;
    public void setBlockSize (double blockWidth, double blockHeight) {
    	this.blockHeight = blockHeight;
    	this.blockWidth = blockWidth;
    }
    public abstract List<GraphicalBlock> sortFunctions(List<cutcode.GraphicalBlock> blocks, LogicalFactory factory);
}
