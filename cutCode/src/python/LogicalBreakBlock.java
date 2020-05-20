package python;

import cutcode.LogicalBlock;

/**
 * @deprecated
 */
public class LogicalBreakBlock extends LogicalBlock { //The block was deemed too complex (for the user) for the current version of cut code

	@Override
	public String toString() {
		String indents = "";
		for(int i = 0; i < getIndentFactor(); i++)
			indents += "	";
		return indents + "break" + System.lineSeparator();
	}
}
