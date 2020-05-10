package Java;

import cutcode.LogicalBlock;

public class LogicalBooleanBinaryOperatorBlock extends LogicalBlock {
	private LogicalBlock op1, op2;
	private String operator;
	public void setOperands(LogicalBlock firstOperand, LogicalBlock secondOperand) {
		op1 = firstOperand;
		op2 = secondOperand;
	}
	public LogicalBlock[] getOperands() {return new LogicalBlock[]{op1, op2};}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperator() {return this.operator;}
	@Override
	public String toString() {
		return op1.toString() + " " + operator + " " + op2.toString();
	}
}
