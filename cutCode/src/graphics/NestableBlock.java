package graphics;

import javafx.geometry.Point2D;

public interface NestableBlock {
	
	public Point2D getPrimaryNestPoint();
	public Point2D getSecondaryNestPoint();

	public void primaryNest(GraphicalBlock block);
	public void secondaryNest(GraphicalBlock block);
}
