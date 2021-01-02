package python;
import cutcode.LogicalBlock;

public class LogicalReturnBlock extends LogicalBlock {
	private LogicalBlock ret;
	
	/**
	 * 
	 * @param ret - the return block 
	 */
	public void setRet(LogicalBlock ret) {
		this.ret = ret;
	}
	
	@Override
	public String toString() {
		String indents = "";
		for(int i = 0; i < getIndentFactor(); i++)
			indents += "	";
		String ret = indents + "return";
		if(this.ret != null) {
			ret = ret + " " + this.ret.toString();
		}
		ret = ret + System.lineSeparator();
		return ret;
	}

}
