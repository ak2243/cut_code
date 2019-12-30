package logicalBlocks;

import cutcode.LList;

public class FunctionCallBlock implements Block {
	public LList<Block> parameters;
	private String name;

	public FunctionCallBlock() {
		parameters = new LList<Block>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the code to call a function (no semicolons or line separators)
	 * @apiNote O(n^2)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		String params = "";
		for(Block b : parameters) {
			params += b.toString() + ", ";
		}
		params.substring(0, params.length() - 1);
		return name + "( " + params + ")";
	}
}
