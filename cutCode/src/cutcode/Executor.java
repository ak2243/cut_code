package cutcode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public abstract class Executor {

	public abstract String execute(HashMap<Integer, GraphicalBlock> lineLocations) throws BlockCodeCompilerErrorException;
	public abstract void export(String code, String filename) throws IOException;
	public abstract void export(List<LogicalBlock> logicalBlocks, String filename) throws IOException;
	public abstract int extractError(String error);
}
