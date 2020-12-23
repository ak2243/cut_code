package factories;

public abstract class GUIFactory {
    public abstract cutcode.GraphicalBlock[] getAllBlocks();
    protected double blockHeight, blockWidth;
    public void setBlockSize (double blockWidth, double blockHeight) {
    	this.blockHeight = blockHeight;
    	this.blockWidth = blockWidth;
    }
}
