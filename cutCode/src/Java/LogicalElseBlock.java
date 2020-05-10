package Java;

import cutcode.LogicalBlock;

import java.util.List;

public class LogicalElseBlock extends LogicalBlock {

	private List<LogicalBlock> executeBlocks;

	public void setExecuteBlocks(List<LogicalBlock> executeBlocks) {
		this.executeBlocks = executeBlocks;
	}
	public List<LogicalBlock> getExecuteBlocks() {return executeBlocks;}

	@Override
	public String toString() {
		String indents = "";
		for(int i = 0; i < getIndentFactor(); i++)
			indents += "	";
		String ret = indents + "else {" + System.lineSeparator();
		for(LogicalBlock l : executeBlocks)
			ret = ret +  l.toString();
		ret = ret + "}";
		return ret;
	}
}
