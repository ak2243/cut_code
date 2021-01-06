package python;

import java.util.List;

import cutcode.LogicalBlock;

public class LogicalFunctionBlock extends LogicalBlock {
	private String name;
	private String[] parameters;
	private List<LogicalBlock> executeBlocks;

	@Override
	public String toString() {
		String indents = "";
		for (int i = 0; i < getIndentFactor(); i++)
			indents += "	";
		String ret = indents + "def " + name + "(";
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) { // processes parameters
				if (i == parameters.length - 1) {
					ret = ret + parameters[i];
				} else {
					ret = ret + parameters[i] + ", ";
				}
			}
		}
		ret = ret + "):" + System.lineSeparator();
		for (LogicalBlock l : executeBlocks)
			ret = ret + l.toString();
		return ret;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	public void setExecuteBlocks(List<LogicalBlock> executeBlocks) {
		this.executeBlocks = executeBlocks;
	}

	public void setName(String name) {
		this.name = name;
	}

}
