package logicalBlocks;

import cutcode.LList;

public class FunctionCallBlock implements Block {
	private String name;


	/**
	 * @return the code to call a function (no semicolons or line separators)
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 * @deprecated
	 */
	@Override
	public String toString() {
		return name + "()";
	}


	/**
	 * @deprecated
	 * @return
	 */
	public String getName() {
		return name;
	}


	/**
	 * @deprecated
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
