package application;

public abstract class Block <T>{
	private Block nestedIn;
	public abstract T execute();
	/**
	 * @return the nestedIn
	 */
	public Block getNestedIn() {
		return nestedIn;
	}
	/**
	 * @param nestedIn the nestedIn to set
	 */
	public void setNestedIn(Block nestedIn) {
		this.nestedIn = nestedIn;
		
	}
	
	
}
