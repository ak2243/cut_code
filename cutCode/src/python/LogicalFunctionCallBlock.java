package python;

import java.util.List;

import cutcode.LogicalBlock;

public class LogicalFunctionCallBlock extends LogicalBlock {
	private String name;
	private List<LogicalBlock> params;
	private boolean independent;

	@Override
	public String toString() {
		String indents = "";
		if (independent) {
			for (int i = 0; i < getIndentFactor(); i++)
				indents += "	";
		}
		String ret = indents + name + "(";
		if (params != null) {
			for (int i = 0; i < params.size() - 1; i++) {
				ret = ret + params.get(i) + ", ";
			}
			ret = ret + params.get(params.size() - 1);
		}
		ret = ret + ")";
		if (independent)
			ret = ret + System.lineSeparator();
		return ret;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParams(List<LogicalBlock> params) {
		this.params = params;
	}

	public void setIndependent(boolean independent) {
		this.independent = independent;
	}

}
