package cutcode;

import factories.LogicalFactory;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;

public abstract class GraphicalBlock extends VBox implements Comparable<GraphicalBlock> {
    private GraphicalBlock boundTo;
    private GraphicalBlock bound;
    private GraphicalBlock nestedIn;
    private LogicalFactory logicalFactory;
    private boolean ignoreNext;

    public boolean ignoreStatus() {
        return ignoreNext;
    }

    public void ignoreNextAction() {
        ignoreNext = true;
    }

    public void actionIgnored() {
        ignoreNext = false;
    }

    @Override
    public int compareTo(GraphicalBlock other) {
        int compare = Double.compare(this.getLayoutY(), other.getLayoutY());
        if (compare == 0)
            return Double.compare(this.getLayoutX(), other.getLayoutX());
        else
            return compare;
    }


    public GraphicalBlock() {
        this(200, 40);
    }

    public GraphicalBlock(double width, double height) {
        setMinWidth(width);
        setMinHeight(height);
        setMaxWidth(width);
        setMaxHeight(height);
    }

    /**
     * @param nestedIn the block that this is nested in
     */
    public void setNestedIn(GraphicalBlock nestedIn) {
        this.nestedIn = nestedIn;
    }

    /**
     * @return the block that this block is nested in
     */
    public GraphicalBlock getNestedIn() {
        return nestedIn;
    }

    /**
     * @param b the block that is bound to this block (b is below)
     */
    public void setBoundTo(GraphicalBlock b) {
        boundTo = b;
        if(b == null) {
            this.layoutXProperty().unbind();
            this.layoutYProperty().unbind();
        }
        this.layoutXProperty().bind(b.layoutXProperty());
        this.layoutYProperty().bind(b.layoutYProperty().add(b.getHeight()));
    }

    /**
     * @return the block that is bound to this block (below)
     */
    public GraphicalBlock getBoundTo() {
        return boundTo;
    }


    /**
     * @param b the block this block is bound to (b is above)
     */
    public void setBound(GraphicalBlock b) {
        bound = b;
    }

    /**
     * @return the block this block is bound to (above)
     */
    public GraphicalBlock getBound() {
        return bound;
    }


    /**
     * @return the logical block object for the graphical block
     */
    public abstract Block getLogicalBlock();

    public abstract GraphicalBlock cloneBlock();


    public void tagErrorOnBlock() {
        String cssLayout = "-fx-border-color: white;\n" + "-fx-border-width: 5;\n" + "-fx-border-style: dashed;\n";
        this.setStyle(cssLayout);
    }

    public void untag() {
        this.setStyle(null);
    }

    /**
     * @return an array of points where a graphical block can nest
     */
    public Point2D[] getNestables() {
        return new Point2D[0];
    }

    /**
     * @param index the index from getNestables() to which the given block must be nested
     * @param nest  the block to be nested in the index
     * @throws InvalidNestException - thrown if the index does not correspond to a valid nest location
     */
    public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
        throw new InvalidNestException();
    }

    public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
        throw new InvalidNestException();
    }

    public double getX() {
        double x = this.getLayoutX();
        if (this.getNestedIn() != null) {
            System.err.println(this + " is allegedly nested in " + this.getNestedIn());
            x += this.getNestedIn().getX() + this.getParent().getLayoutX();
        }
        return x;
    }

    public double getY() {
        double y = this.getLayoutY();
        if (this.getNestedIn() != null)
            y += this.getNestedIn().getY() + this.getParent().getLayoutY();
        return y;
    }

    /**
     * @param incrementBox    - the box to be incremented
     * @param heightIncrement - the increase in height
     * @param widthIncrement  - the increase in width
     */
    public void increment(VBox incrementBox, double heightIncrement, double widthIncrement) {
        if (widthIncrement > 0) {
            incrementBox.setMaxWidth(incrementBox.getWidth() + widthIncrement);
            this.setMaxWidth(this.getWidth() + widthIncrement);
        }
        if (heightIncrement > 0) {
            incrementBox.setMinHeight(incrementBox.getHeight() + heightIncrement);
            this.setMaxHeight(this.getHeight() + heightIncrement);
        }
        if (this.getNestedIn() != null)
            this.getNestedIn().increment((VBox) this.getParent(), heightIncrement, widthIncrement);
    }

}