package graphics;

import cutcode.LList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class Sequence<E extends Node> extends LList<E> implements Comparable<Sequence<E>>{
	
	private VBox vbox;
	
	public void setVBox(VBox other) {
		vbox = other;
	}
	
	public VBox getVBox() {
		return vbox;
	}
	
	/**
	 * @returns the comparison of the two sequences, compared by height
	 */
	@Override
	public int compareTo(Sequence<E> o) {
		return Double.compare(this.get(0).getLayoutY(), o.get(0).getLayoutY());
	}
	
}
