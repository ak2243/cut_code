package factories;

import cutcode.LogicalBlock;

public interface LogicalFactory {
	public LogicalBlock createPrint(int indentFactor, String print);
	public LogicalBlock createValue(String value);
	public LogicalBlock createVariable(int indentFactor, String name, LogicalBlock value);
	public LogicalBlock createVariable(int indentFactor, String type, String name, LogicalBlock value);
}
