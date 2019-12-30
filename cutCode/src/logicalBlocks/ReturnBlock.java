package logicalBlocks;

public class ReturnBlock implements Block {
	public Block value;
	
	
	/**
	 * @return the java code to return a given value
	 * @apiNote O(1)
	 * @author Arjun Khanna
	 */
	@Override
	public String toString() {
		return "return " + value.toString();
	}
}
