package Java;

import cutcode.LogicalBlock;

import java.util.List;

public class LogicalElseBlock extends LogicalBlock {

	private List<LogicalBlock> executeBlocks;

	/**
	 * 
	 * @param executeBlocks - a list of the logical blocks that will be executed when the else is run
	 */
	public void setExecuteBlocks(List<LogicalBlock> executeBlocks) {
		this.executeBlocks = executeBlocks;
	}

	/**
	 * 
	 * @return a list of the logical blocks that will be executed when the else is run
	 */
	public List<LogicalBlock> getExecuteBlocks() {
		return executeBlocks;
	}

	@Override
	public String toString() {
		String indents = "";
		for (int i = 0; i < getIndentFactor(); i++)
			indents += "	";
		String ret = indents + "else {" + System.lineSeparator();
		for (LogicalBlock l : executeBlocks)
			ret = ret + l.toString();
		ret = ret + indents + "}" + System.lineSeparator();
		return ret;
	}
}
