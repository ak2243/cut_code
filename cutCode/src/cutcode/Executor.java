package cutcode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public abstract class Executor {
	/**
	 * 
	 * @param lineLocations a hashmap where the key is the line number for the corresponding graphical block (value in hashmap)
	 * @return the output of the program
	 * @throws BlockCodeCompilerErrorException thrown when the user's code doesn't compile
	 */
	public abstract String execute(HashMap<Integer, GraphicalBlock> lineLocations) throws BlockCodeCompilerErrorException;
	/**
	 * 
	 * @param code - the syntactic code to export to a file
	 * @param filename - the file to which this code will be exported
	 * @throws IOException
	 */
	public abstract void export(String code, String filename) throws IOException;
	/**
	 * 
	 * @param logicalBlocks - the block code to export to a file
	 * @param filename - the file to which this code will be exported
	 * @throws IOException
	 */
	public abstract void export(List<LogicalBlock> logicalBlocks, String filename) throws IOException;
	/**
	 * 
	 * @param error - an error message
	 * @return the line number indicated in the error message
	 */
	public abstract int extractError(String error);
}
