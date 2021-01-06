package Java;

import cutcode.LogicalBlock;

public class LogicalBinaryMathOperatorBlock extends LogicalBlock {
	private LogicalBlock op1, op2;
	private String operator;

	/**
	 * 
	 * @param firstOperand - the first operand for the math operation
	 * @param secondOperand - the second operand for the math operation
	 */
	public void setOperands(LogicalBlock firstOperand, LogicalBlock secondOperand) {
		op1 = firstOperand;
		op2 = secondOperand;
	}

	/**
	 * 
	 * @return an array of the operands for this operator
	 */
	public LogicalBlock[] getOperands() {
		return new LogicalBlock[] { op1, op2 };
	}

	/**
	 * 
	 * @param operator - the math operator selected by the user
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 
	 * @return the math operator selected by the user
	 */
	public String getOperator() {
		return this.operator;
	}

	@Override
	public String toString() {
		return op1.toString() + " " + operator + " " + op2.toString();
	}
}
