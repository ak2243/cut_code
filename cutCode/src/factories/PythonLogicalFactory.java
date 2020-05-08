package factories;

import cutcode.LogicalBlock;

public class PythonLogicalFactory implements LogicalFactory {

	@Override
	public LogicalBlock createPrint(int indentFactor, String print) {
		python.LogicalPrintBlock ret = new python.LogicalPrintBlock();
		ret.setPrint(print);
		ret.setIndentFactor(indentFactor);
		return ret;
	}

	@Override
	public LogicalBlock createValue(String value) {
		python.LogicalValueBlock ret = new python.LogicalValueBlock();
		ret.setValue(value);
		return ret;
	}

	@Override
	public LogicalBlock createVariable(int indentFactor, String name, LogicalBlock value) {
		python.LogicalVariableBlock ret = new python.LogicalVariableBlock();
		ret.setName(name);
		ret.setIndentFactor(indentFactor);
		ret.setValue(value);
		return ret;
	}
}
