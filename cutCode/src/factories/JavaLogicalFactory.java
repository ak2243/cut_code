package factories;

import Java.*;
import cutcode.LogicalBlock;

import java.util.HashMap;
import java.util.List;

public class JavaLogicalFactory implements LogicalFactory {
	public HashMap<String, String> keywordTranslation; //Used to go from graphical representation of operators to code

	public JavaLogicalFactory() {
		keywordTranslation = new HashMap<>();
		keywordTranslation.put("or", "||");
		keywordTranslation.put("and", "&&");
		keywordTranslation.put("==", "==");
		keywordTranslation.put(">", ">");
		keywordTranslation.put(">=", ">=");
		keywordTranslation.put("<", "<");
		keywordTranslation.put("<=", "<=");
		keywordTranslation.put("=", "==");
		keywordTranslation.put("+", "+");
		keywordTranslation.put("-", "-");
		keywordTranslation.put("÷", "÷");
		keywordTranslation.put("X", "X");
		keywordTranslation.put("%", "%");
		keywordTranslation.put("num", "double ");
		keywordTranslation.put("T/F", "boolean" );
		keywordTranslation.put("str", "String ");
		keywordTranslation.put("edit", "");
		//Allows changing keywords to code keywords
	}

	@Override
	public int getEndingBrace() {
		return 1; //there *are* ending braces in java
	}

	@Override
	public int getBaseIndent() {
		return 2;
	}

	@Override
	public LogicalBlock createPrint(int indentFactor, LogicalBlock print) {
		LogicalPrintBlock ret = new LogicalPrintBlock();
		ret.setPrint(print);
		ret.setIndentFactor(indentFactor);
		return ret;
	}

	@Override
	public LogicalBlock createValue(String value) {
		LogicalValueBlock ret = new LogicalValueBlock();
		ret.setValue(value);
		return ret;
	}

	@Override
	public LogicalBlock createVariable(int indentFactor, String name, LogicalBlock value) {
		LogicalVariableBlock ret = new LogicalVariableBlock();
		ret.setName(name);
		ret.setIndentFactor(indentFactor);
		ret.setValue(value);
		return ret;
	}

	@Override
	public LogicalBlock createVariable(int indentFactor, String type, String name, LogicalBlock value) {
		LogicalVariableBlock ret = new LogicalVariableBlock();
		ret.setName(name);
		ret.setIndentFactor(indentFactor);
		ret.setValue(value);
		ret.setType(keywordTranslation.get(type));
		return ret;

	}

	@Override
	public LogicalBlock createIf(int indentFactor, LogicalBlock condition, List<LogicalBlock> executeBlocks) {
		LogicalIfBlock ret = new LogicalIfBlock();
		ret.setCondition(condition);
		ret.setExecuteBlocks(executeBlocks);
		ret.setIndentFactor(indentFactor);
		return ret;
	}

	@Override
	public LogicalBlock createBinaryBooleanOperator(LogicalBlock firstOperand, String operator, LogicalBlock secondOperand) {
		LogicalBooleanBinaryOperatorBlock ret = new LogicalBooleanBinaryOperatorBlock();
		ret.setOperands(firstOperand, secondOperand);
		ret.setOperator(keywordTranslation.get(operator));
		return ret;
	}

	@Override
	public LogicalBlock createBinaryMathOperator(LogicalBlock firstOperand, String operator, LogicalBlock secondOperand) {
		LogicalBinaryMathOperatorBlock ret = new LogicalBinaryMathOperatorBlock();
		ret.setOperands(firstOperand, secondOperand);
		ret.setOperator(keywordTranslation.get(operator));
		return ret;
	}

	@Override
	public LogicalBlock createElseBlock(int indentFactor, List<LogicalBlock> executeBlocks) {
		LogicalElseBlock ret = new LogicalElseBlock();
		ret.setIndentFactor(indentFactor);
		ret.setExecuteBlocks(executeBlocks);
		return ret;
	}

	@Override
	public LogicalBlock createBreak(int indentFactor) {
		LogicalBreakBlock ret = new LogicalBreakBlock(); //deprecated but wanted to keep it here
		ret.setIndentFactor(indentFactor);
		return ret;
	}


	@Override
	public LogicalBlock createWhileLoop(int indentFactor, LogicalBlock condition, List<LogicalBlock> executeBlocks) {
		LogicalWhileBlock ret = new LogicalWhileBlock();
		ret.setCondition(condition);
		ret.setExecuteBlocks(executeBlocks);
		ret.setIndentFactor(indentFactor);
		return ret;
	}

	@Override
	public LogicalBlock createFunctionBlock(int indentFactor, String name, String retType, String[] parameters,
			List<LogicalBlock> executeBlocks) {
		Java.LogicalFunctionBlock ret = new Java.LogicalFunctionBlock();
		ret.setIndentFactor(indentFactor);
		ret.setName(name);
		ret.setRetType(retType);
		ret.setParameters(parameters);
		ret.setExecuteBlocks(executeBlocks);
		return ret;
	}

	@Override
	public LogicalBlock createFunctionCallBlock(int indentFactor, String name, List<LogicalBlock> parameters, boolean independent) {
		LogicalFunctionCallBlock ret = new LogicalFunctionCallBlock();
		ret.setIndentFactor(indentFactor);
		ret.setName(name);
		ret.setIndependent(independent);
		ret.setParams(parameters);
		return ret;
	}

	@Override
	public LogicalBlock createReturnBlock(int indentFactor, LogicalBlock r) {
		LogicalReturnBlock ret = new LogicalReturnBlock();
		ret.setIndentFactor(indentFactor);
		ret.setRet(r);
		return ret;
	}
}
