package Java;

import cutcode.LogicalBlock;

import java.util.List;

public class LogicalWhileBlock extends LogicalBlock {
	private LogicalBlock condition;
	private List<LogicalBlock> executeBlocks;

	public void setCondition(LogicalBlock condition) {
		this.condition = condition;
	}
	public LogicalBlock getCondition() {return condition;}

	public void setExecuteBlocks(List<LogicalBlock> executeBlocks) {
		this.executeBlocks = executeBlocks;
	}
	public List<LogicalBlock> getExecuteBlocks() {return executeBlocks;}

	@Override
	public String toString() {
		String indents = "";
		for(int i = 0; i < getIndentFactor(); i++)
			indents += "	";
		String ret = indents + "while (" + condition.toString() + ") {" + System.lineSeparator();
		for(LogicalBlock l : executeBlocks)
			ret = ret +  l.toString();
		ret = ret + indents + "}" + System.lineSeparator();
		return ret;
	}
}

