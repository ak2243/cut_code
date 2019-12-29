package logicalBlocks;

public class VariableCallBlock implements Block {
	private String varName;

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
