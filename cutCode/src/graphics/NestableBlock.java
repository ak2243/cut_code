package graphics;

import javafx.geometry.Point2D;

public abstract class NestableBlock extends GraphicalBlock {
	
	protected NestableBlock(double width, double height) {
		super(width, height);

	}
	
	public abstract Point2D getPrimaryNestPoint();
	public abstract Point2D getSecondaryNestPoint();

	public abstract void primaryNest(GraphicalBlock block);
	public abstract void secondaryNest(GraphicalBlock block);
	
}
