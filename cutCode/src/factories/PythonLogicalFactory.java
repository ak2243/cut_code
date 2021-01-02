package factories;

import cutcode.LogicalBlock;

import java.util.HashMap;
import java.util.List;


public class PythonLogicalFactory implements LogicalFactory {
	public HashMap<String, String> operatorTranslation; //Used to go from graphical representation of operators to code

	public PythonLogicalFactory() {
		operatorTranslation = new HashMap<>();
		operatorTranslation.put("or", "|");
		operatorTranslation.put("and", "&");
		operatorTranslation.put(">", ">");
		operatorTranslation.put(">=", ">=");
		operatorTranslation.put("<", "<");
		operatorTranslation.put("<=", "<=");
		operatorTranslation.put("=", "==");
		operatorTranslation.put("+", "+");
		operatorTranslation.put("-", "-");
		operatorTranslation.put("÷", "÷");
		operatorTranslation.put("X", "X");
		operatorTranslation.put("%", "%");
		//Allows translating keywords to code keywords
	}

	@Override
	public int getEndingBrace() {
		return 0;
	}

	@Override
	public int getBaseIndent() {
		return 0;
	}

	@Override
	public LogicalBlock createPrint(int indentFactor, LogicalBlock print) {
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

	@Override
	public LogicalBlock createBinaryBooleanOperator(LogicalBlock firstOperand, String operator, LogicalBlock secondOperand) {
		python.LogicalBooleanBinaryOperatorBlock ret = new python.LogicalBooleanBinaryOperatorBlock();
		ret.setOperands(firstOperand, secondOperand);
		ret.setOperator(operatorTranslation.get(operator));
		return ret;
	}

	@Override
	public LogicalBlock createBinaryMathOperator(LogicalBlock firstOperand, String operator, LogicalBlock secondOperand) {
		python.LogicalBinaryMathOperatorBlock ret = new python.LogicalBinaryMathOperatorBlock();
		ret.setOperands(firstOperand, secondOperand);
		ret.setOperator(operatorTranslation.get(operator));
		return ret;
	}

	@Override
	public LogicalBlock createElseBlock(int indentFactor, List<LogicalBlock> executeBlocks) {
		python.LogicalElseBlock ret = new python.LogicalElseBlock();
		ret.setIndentFactor(indentFactor);
		ret.setExecuteBlocks(executeBlocks);
		return ret;
	}

	@Override
	public LogicalBlock createBreak(int indentFactor) {
		python.LogicalBreakBlock ret = new python.LogicalBreakBlock(); //deprecated but wanted to keep it here
		ret.setIndentFactor(indentFactor);
		return ret;
	}


	@Override
	public LogicalBlock createWhileLoop(int indentFactor, LogicalBlock condition, List<LogicalBlock> executeBlocks) {
		python.LogicalWhileBlock ret = new python.LogicalWhileBlock();
		ret.setCondition(condition);
		ret.setExecuteBlocks(executeBlocks);
		ret.setIndentFactor(indentFactor);
		return ret;
	}

	@Override
	public LogicalBlock createFunctionBlock(int indentFactor, String name, String retType, String[] parameters,
			List<LogicalBlock> executeBlocks) {
		python.LogicalFunctionBlock ret = new python.LogicalFunctionBlock();
		ret.setIndentFactor(indentFactor);
		ret.setName(name);
		ret.setParameters(parameters);
		ret.setExecuteBlocks(executeBlocks);
		return ret;
	}

	@Override
	public LogicalBlock createFunctionCallBlock(int indentFactor, String name, List<LogicalBlock> parameters,
			boolean independent) {
		python.LogicalFunctionCallBlock ret = new python.LogicalFunctionCallBlock();
		ret.setIndentFactor(indentFactor);
		ret.setName(name);
		ret.setIndependent(independent);
		ret.setParams(parameters);
		return ret;
	}

	@Override
	public LogicalBlock createReturnBlock(int indentFactor, LogicalBlock r) {
		python.LogicalReturnBlock ret = new python.LogicalReturnBlock();
		ret.setIndentFactor(indentFactor);
		ret.setRet(r);
		return ret;

	}


}
