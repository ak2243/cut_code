package cutcode;

public interface Block { //Abstract class for the generic class
	/**
	 * 
	 * @return python code for this block
	 */
	@Override
	public String toString(); //All blocks must be executable
	
	public static final int PYTHON = 1;
	public static final int JAVA = 2;
	
	/**
	 * 
	 * @param language - should be one of the constants in the Block class
	 * @return the code for this block in that language
	 */
	public String toCode(int language);
}
