package graphics;

import cutcode.LList;
import javafx.scene.Node;

public class Sequence<E extends Node> extends LList<E> implements Comparable<Sequence<E>>{
	
	
	/**
	 * @returns the comparison of the two sequences, compared by height
	 */
	@Override
	public int compareTo(Sequence<E> o) {
		return Double.compare(this.get(0).getLayoutY(), o.get(0).getLayoutY());
	}
	
}
