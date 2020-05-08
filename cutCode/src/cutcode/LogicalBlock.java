package cutcode;

public abstract class LogicalBlock { //Abstract class for the generic class
	private int indentFactor;

	@Override
	public abstract String toString();

	/**
	 *
	 * @return the number of times the code for this block should be indented (operator blocks should never be indented, their parent blocks should be indented)
	 */
	public int getIndentFactor() {
		return indentFactor;
	}

	/**
	 *
	 * @param indentFactor an integer representing the number of times the code for this block should be indented (operator blocks should never be indented, their parent blocks should be indented)
	 */
	public boolean setIndentFactor(int indentFactor) {
		if(indentFactor < 0) {
			return false;
		}
		this.indentFactor = indentFactor;
		return true;
	}
}
