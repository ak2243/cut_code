package graphics;

import javafx.geometry.*;

public abstract class AttachmentPoint extends Point2D{
	
	
	
	public AttachmentPoint(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void attach();

}
