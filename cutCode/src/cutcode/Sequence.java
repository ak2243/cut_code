package cutcode;

public class Sequence implements Comparable<Sequence>{
	public double height; //TODO should this be public?
	
	
	/**
	 * @returns the comparison of the two sequences, compared by height
	 */
	@Override
	public int compareTo(Sequence o) {
		return Double.compare(this.height, o.height);
	}

}
