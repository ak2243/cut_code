package factories;

import cutcode.LogicalBlock;

import java.util.List;

public interface LogicalFactory {
	/**
	 * @param indentFactor the amount of times this block should be indented
	 * @param print        the statement that should be printed. Quotes necessary if printing a string instead of referring to a value
	 * @return the Logical Block that prints the print parameter
	 */
	public LogicalBlock createPrint(int indentFactor, String print);

	/**
	 * @param value the value being referred to in this block
	 * @return
	 * @apiNote this blocks should be used as part of a large statement, and not independently
	 */
	public LogicalBlock createValue(String value);

	/**
	 * @param indentFactor the number of times this block should be indented
	 * @param name         the name of the variable
	 * @param value        the value assigned to the variable
	 * @return the Logical Block that creates the variable with the above parameters
	 * @apiNote This method should be used when making a variable in a language where types are not required, or when editing variables in languages like Java
	 */
	public LogicalBlock createVariable(int indentFactor, String name, LogicalBlock value);

	/**
	 * @param indentFactor the number of times this block should be indented
	 * @param type         required for some languages. If using a language such as python, use the other createVariable method without a type requirement. The other method should also be used for editing variables in languages like java
	 * @param name         the name of the variable
	 * @param value        the value assigned to the variable
	 * @return the logicalblock that creates the variable with the above parameters
	 */
	public LogicalBlock createVariable(int indentFactor, String type, String name, LogicalBlock value);

	/**
	 * @param indentFactor  the number of times the if statement should be indented (this does not influence indentFactor for executeBlocks, which should have separately set indentFactors)
	 * @param condition     the Logical Block that is the condition for this if statment
	 * @param executeBlocks the blocks that will be executed if the condition evaluates to true
	 * @return
	 */
	public LogicalBlock createIf(int indentFactor, LogicalBlock condition, List<LogicalBlock> executeBlocks);

	/**
	 * @param firstOperand  the logical block that is the first operand
	 * @param operator      the operator that is being used, in it's graphical form ("and", "or", etc.)
	 * @param secondOperand the logical block that is the second operand
	 * @return
	 */
	public LogicalBlock createBinaryBooleanOperator(LogicalBlock firstOperand, String operator, LogicalBlock secondOperand);

	/**
	 * @param indentFactor the number of times this break statement should be indented
	 * @return the LogicalBlock for a break statement
	 */
	public LogicalBlock createBreak(int indentFactor);

	/**
	 * @param indentFactor  the number of times the while loop should be indented (this does not influence indentFactor for executeBlocks, which should have separately set indentFactors)
	 * @param condition     the Logical Block that is the condition for this while loop
	 * @param executeBlocks the blocks that will be executed while the condition evaluates to true
	 * @return
	 */
	public LogicalBlock createWhileLoop(int indentFactor, LogicalBlock condition, List<LogicalBlock> executeBlocks);

	/**
	 * @param firstOperand  the logical block that is the first operand
	 * @param operator      the operator that is being used, in it's graphical form ("X", "÷", etc.)
	 * @param secondOperand the logical block that is the second operand
	 * @return
	 */
	public LogicalBlock createBinaryMathOperator(LogicalBlock firstOperand, String operator, LogicalBlock secondOperand);

	/**
	 *
	 * @param indentFactor the number of times that this block should be indented  (this does not influence indentFactor for executeBlocks, which should have separately set indentFactors)
	 * @param executeBlocks the statements that should be executed if the else statement is activated
	 * @return
	 */
	public LogicalBlock createElseBlock(int indentFactor, List<LogicalBlock> executeBlocks);
}
