package factories;

import cutcode.LogicalBlock;

import java.util.HashMap;
import java.util.List;

public class PythonLogicalFactory implements LogicalFactory {
	public HashMap<String, String> operatorTranslation; //Used to go from graphical representation of operators to code

	public PythonLogicalFactory() {
		operatorTranslation = new HashMap<>(); //"+", "-", "÷", "X", "%"
		operatorTranslation.put("or", "|");
		operatorTranslation.put("and", "&");
		operatorTranslation.put(">", ">");
		operatorTranslation.put(">=", ">=");
		operatorTranslation.put("<", "<");
		operatorTranslation.put("<=", "<=");
		operatorTranslation.put("+", "+");
		operatorTranslation.put("-", "-");
		operatorTranslation.put("÷", "÷");
		operatorTranslation.put("X", "X");
		operatorTranslation.put("%", "%");
	}

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

	@Override
	public LogicalBlock createVariable(int indentFactor, String type, String name, LogicalBlock value) {
		return createVariable(indentFactor, name, value);
	}

	@Override
	public LogicalBlock createIf(int indentFactor, LogicalBlock condition, List<LogicalBlock> executeBlocks) {
		python.LogicalIfBlock ret = new python.LogicalIfBlock();
		ret.setCondition(condition);
		ret.setExecuteBlocks(executeBlocks);
		ret.setIndentFactor(indentFactor);
		return ret;
	}

	/**
	 * @param firstOperand  the logical block that is the first operand
	 * @param operator      the operator that is being used, in it's graphical form ("and", "or", etc.)
	 * @param secondOperand the logical block that is the second operand
	 * @return
	 */
	@Override
	public LogicalBlock createBinaryBooleanOperator(LogicalBlock firstOperand, String operator, LogicalBlock secondOperand) {
		python.LogicalBooleanBinaryOperatorBlock ret = new python.LogicalBooleanBinaryOperatorBlock();
		ret.setOperands(firstOperand, secondOperand);
		ret.setOperator(operatorTranslation.get(operator));
		return ret;
	}

	/**
	 * @param firstOperand  the logical block that is the first operand
	 * @param operator      the operator that is being used, in it's graphical form ("and", "or", etc.)
	 * @param secondOperand the logical block that is the second operand
	 * @return
	 */
	@Override
	public LogicalBlock createBinaryMathOperator(LogicalBlock firstOperand, String operator, LogicalBlock secondOperand) {
		python.LogicalBinaryMathOperatorBlock ret = new python.LogicalBinaryMathOperatorBlock();
		ret.setOperands(firstOperand, secondOperand);
		ret.setOperator(operatorTranslation.get(operator));
		return ret;
	}

	/**
	 * @param indentFactor  the number of times that this block should be indented  (this does not influence indentFactor for executeBlocks, which should have separately set indentFactors)
	 * @param executeBlocks the statements that should be executed if the else statement is activated
	 * @return
	 */
	@Override
	public LogicalBlock createElseBlock(int indentFactor, List<LogicalBlock> executeBlocks) {
		python.LogicalElseBlock ret = new python.LogicalElseBlock();
		ret.setIndentFactor(indentFactor);
		ret.setExecuteBlocks(executeBlocks);
		return ret;
	}

	/**
	 * @param indentFactor the number of times this break statement should be indented
	 * @return the LogicalBlock for a break statement
	 */
	@Override
	public LogicalBlock createBreak(int indentFactor) {
		python.LogicalBreakBlock ret = new python.LogicalBreakBlock();
		ret.setIndentFactor(indentFactor);
		return ret;
	}

	/**
	 * @param indentFactor  the number of times the while loop should be indented (this does not influence indentFactor for executeBlocks, which should have separately set indentFactors)
	 * @param condition     the Logical Block that is the condition for this while loop
	 * @param executeBlocks the blocks that will be executed while the condition evaluates to true
	 * @return
	 */
	@Override
	public LogicalBlock createWhileLoop(int indentFactor, LogicalBlock condition, List<LogicalBlock> executeBlocks) {
		python.LogicalWhileBlock ret = new python.LogicalWhileBlock();
		ret.setCondition(condition);
		ret.setExecuteBlocks(executeBlocks);
		ret.setIndentFactor(indentFactor);
		return ret;
	}


}
