package cutcode;

public class Sequence<E> extends LList<E> implements Comparable<Sequence<E>>{
	public double height; //TODO should this be public?
	
	/**
	 * @returns the comparison of the two sequences, compared by height
	 */
	@Override
	public int compareTo(Sequence<E> o) {
		return Double.compare(this.height, o.height);
	}

}
