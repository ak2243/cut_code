package factories;

import cutcode.LogicalBlock;

import java.util.List;

public interface LogicalFactory {

	/**
	 *
	 * @return 1 if the language has an ending brace and 0 if not
	 */
	public int getEndingBrace();

	/**
	 *
	 * @return the base number of indents
	 */
	public int getBaseIndent();

	/**
	 * @param indentFactor the amount of times this block should be indented
	 * @param print        the statement that should be printed. Quotes necessary if
	 *                     printing a string instead of referring to a value
	 * @return the Logical Block that prints the print parameter
	 */
	public LogicalBlock createPrint(int indentFactor, LogicalBlock print);

	/**
	 * @param value the value being referred to in this block
	 * @return the logical block to refer to this value
	 */
	public LogicalBlock createValue(String value);

	/**
	 * @param indentFactor the number of times this block should be indented
	 * @param name         the name of the variable
	 * @param value        the value assigned to the variable
	 * @return the Logical Block that creates the variable with the above parameters
	 */
	public LogicalBlock createVariable(int indentFactor, String name, LogicalBlock value);

	/**
	 * @param indentFactor the number of times this block should be indented
	 * @param type         required for some languages. If using a language such as
	 *                     python, use the other createVariable method without a
	 *                     type requirement. The other method should also be used
	 *                     for editing variables in languages like java
	 * @param name         the name of the variable
	 * @param value        the value assigned to the variable
	 * @return the logicalblock that creates the variable with the above parameters
	 */
	public LogicalBlock createVariable(int indentFactor, String type, String name, LogicalBlock value);

	/**
	 * @param indentFactor  the number of times the if statement should be indented
	 *                      (this does not influence indentFactor for executeBlocks,
	 *                      which should have separately set indentFactors)
	 * @param condition     the Logical Block that is the condition for this if
	 *                      statment
	 * @param executeBlocks the blocks that will be executed if the condition
	 *                      evaluates to true
	 * @return the logical block for this if statement
	 */
	public LogicalBlock createIf(int indentFactor, LogicalBlock condition, List<LogicalBlock> executeBlocks);

	/**
	 * @param firstOperand  the logical block that is the first operand
	 * @param operator      the operator that is being used, in it's graphical form
	 *                      ("and", "or", etc.)
	 * @param secondOperand the logical block that is the second operand
	 * @return the logical block for this boolean operator
	 */
	public LogicalBlock createBinaryBooleanOperator(LogicalBlock firstOperand, String operator,
			LogicalBlock secondOperand);

	/**
	 * @param indentFactor the number of times this break statement should be
	 *                     indented
	 * @return the LogicalBlock for a break statement
	 */
	public LogicalBlock createBreak(int indentFactor);

	/**
	 * @param indentFactor  the number of times the while loop should be indented
	 *                      (this does not influence indentFactor for executeBlocks,
	 *                      which should have separately set indentFactors)
	 * @param condition     the Logical Block that is the condition for this while
	 *                      loop
	 * @param executeBlocks the blocks that will be executed while the condition
	 *                      evaluates to true
	 * @return the logical block for this while loop
	 */
	public LogicalBlock createWhileLoop(int indentFactor, LogicalBlock condition, List<LogicalBlock> executeBlocks);

	/**
	 * @param firstOperand  the logical block that is the first operand
	 * @param operator      the operator that is being used, in it's graphical form
	 *                      ("X", "÷", etc.)
	 * @param secondOperand the logical block that is the second operand
	 * @return the logical block for this math operator
	 */
	public LogicalBlock createBinaryMathOperator(LogicalBlock firstOperand, String operator,
			LogicalBlock secondOperand);

	/**
	 *
	 * @param indentFactor  the number of times that this block should be indented
	 *                      (this does not influence indentFactor for executeBlocks,
	 *                      which should have separately set indentFactors)
	 * @param executeBlocks the statements that should be executed if the else
	 *                      statement is activated
	 * @return the logical block for this else statement
	 */
	public LogicalBlock createElseBlock(int indentFactor, List<LogicalBlock> executeBlocks);
	
	/**
	 * @param indentFactor - the number of times this block should be indented
	 * @param name - the name of the function
	 * @param retType - return type of function. ignored if python
	 * @param parameters - all the parameters for the function, null if none
	 * @param executeBlocks - all the blocks inside the 
	 * @return the logical block for this function definition
	 */
	public LogicalBlock createFunctionBlock(int indentFactor, String name, String retType, String[] parameters, List<LogicalBlock> executeBlocks);
	
	
	/**
	 * 
	 * @param indentFactor - the number of times this block should be indented
	 * @param parameters - the parameter blocks for this function call
	 * @param independent - true if this function is called as an independent block (has a new line after), false if not.
	 * @return
	 */
	public LogicalBlock createFunctionCallBlock(int indentFactor, String name, List<LogicalBlock> parameters, boolean independent);
	
	/**
	 * 
	 * @param indentFactor - the number of times this block should be indented
	 * @param r - the block this return block will return
	 * @return the logical block for this return
	 */
	public LogicalBlock createReturnBlock(int indentFactor, LogicalBlock r);
}
