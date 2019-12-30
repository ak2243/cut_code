package logicalBlocks;

public class VariableCallBlock implements Block {
	private String varName;

	/**
	 * @return the code to call the variable with the given name
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		return varName;
	}

	public String getName() {
		return varName;
	}

	public void setName(String varName) {
		this.varName = varName;
	}
}
