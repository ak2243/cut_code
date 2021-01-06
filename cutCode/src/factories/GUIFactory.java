package factories;

import java.util.List;

import cutcode.GraphicalBlock;

public abstract class GUIFactory {
	/**
	 * 
	 * @return a list of graphical blocks, one for each kind of block
	 */
    public abstract cutcode.GraphicalBlock[] getAllBlocks();
    protected double blockHeight, blockWidth;
    /**
     * 
     * @param blockWidth - the standard block width
     * @param blockHeight - the standard block height
     */
    public void setBlockSize (double blockWidth, double blockHeight) {
    	this.blockHeight = blockHeight;
    	this.blockWidth = blockWidth;
    }
    /**
     * @param blocks - the list of graphical blocks to sort
     * @param factory - the logical factory for the language the user is running
     * @return the sorted list of blocks
     */
    public abstract List<GraphicalBlock> sortFunctions(List<cutcode.GraphicalBlock> blocks, LogicalFactory factory);
}
