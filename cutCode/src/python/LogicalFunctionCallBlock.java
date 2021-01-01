package python;

import java.util.List;

import cutcode.LogicalBlock;

public class LogicalFunctionCallBlock extends LogicalBlock {
	private String name;
	private List<LogicalBlock> params;

	@Override
	public String toString() {
		String indents = "";
		for(int i = 0; i < getIndentFactor(); i++)
			indents += "	";
		String ret = indents + name + "(";
		if (params != null) {
			for (int i = 0; i < params.size() - 1; i++) {
				ret = ret + params.get(i) + ", ";
			}
			ret = ret + params.get(params.size() - 1);
		}
		ret = ret + ")";
		return ret;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParams(List<LogicalBlock> params) {
		this.params = params;
	}

}
