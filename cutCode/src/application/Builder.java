package application;

import java.util.*;

public class Builder {
	private ArrayList<Block<?>> allBlocks;
	private ArrayList<Block<?>> altBlocks;
	private boolean error;

	public Builder() {
		error = false;
		allBlocks = new ArrayList<Block<?>>(); // stores all blocks
		altBlocks = new ArrayList<Block<?>>(); // only used for blocks inside if statement
	}

	public Builder(ArrayList<Block<?>> altBlocks) { // Constructor for blocks inside if statements
		if (altBlocks != null || altBlocks.size() == 0) {
			this.altBlocks = altBlocks;
		}
		error = false;
		allBlocks = new ArrayList<Block<?>>();
	}

	public ArrayList<Block<?>> getBlocks() {
		return allBlocks; // used to create duplicate Builder objects
	}

	/**
	 * 
	 * @param type: the type of variable. Valid types: number, boolean, string
	 * @param value: the value of the variable
	 * @param varName: the name of the variable
	 */
	public void createVariable(String varName, String value) {

		if (value.equals("true") || value.equals("false")) { // For variable being a boolean
			VariableBlock<Boolean> v = new VariableBlock<Boolean>(); // parses the boolean
			v.setValue(Boolean.parseBoolean(value));
			if (getVariable(varName) != null) { // checks if variable already existed
				if (getVariable(varName).execute() instanceof Boolean) {
					((VariableBlock<Boolean>) getVariable(varName)).setValue(Boolean.parseBoolean(value)); // Changes
																											// value if
																											// var
																											// already
																											// exists
				} else {
					error();
				}
			} else {
				v.setName(varName); // Creates and then adds variable (in next line)
				allBlocks.add(v);
			}
		} else {
			try {
				Double.parseDouble(value); // Checks if variable is a Double
				VariableBlock<Double> v = new VariableBlock<Double>(); // creates and later sets variable accordingly
				v.setValue(Double.parseDouble(value));

				if (getVariable(varName) != null) {
					if (getVariable(varName).execute() instanceof Double) { // checks if pre-existing
						((VariableBlock<Double>) getVariable(varName)).setValue(Double.parseDouble(value)); // sets
																											// variable
																											// to parsed
																											// Double
					} else {
						error();
					}
				} else {
					v.setName(varName);
					allBlocks.add(v);
				}

			} catch (NumberFormatException e) {
				if (parseMath(value) != null) { // Checks if variable evaluates to a Double
					VariableBlock<Double> v = new VariableBlock<Double>(); // creates and later sets variable
																			// accordingly
					v.setValue(parseMath(value));

					if (getVariable(varName) != null) {
						if (getVariable(varName).execute() instanceof Double) {
							((VariableBlock<Double>) getVariable(varName)).setValue(parseMath(value)); // sets variable
																										// to parsed
																										// Math
						} else {
							error();
						}
					} else {
						v.setName(varName);
						allBlocks.add(v);
					}

				} else {
					VariableBlock<String> v = new VariableBlock<String>();
					v.setValue(value);

					if (getVariable(varName) != null) { // checks if variable already exists
						if (getVariable(varName).execute() instanceof String) {
							((VariableBlock<String>) getVariable(varName)).setValue(value);
						} else {
							error();
						}
					} else {
						v.setName(varName); // just sets a variable to pure string formed
						allBlocks.add(v);
					}
				}
			}
		}

	}

	public void print(String s) {
		PrintBlock p = new PrintBlock();
		System.err.println(s);
		if (getVariable(s) != null) { // checks if the thing to be printed is a variable
			p.setPrint("" + getVariable(s).execute());
		} else if (parseMath(s) != null) { // checks if it is a math statement
			p.setPrint("" + parseMath(s));
		} else {
			p.setPrint(s);
		}
		allBlocks.add(p); // adds blocks to the arrayList of blocks
	}

	/**
	 * 
	 * @param varName
	 * @return returns null if variable not found,
	 */
	public VariableBlock<?> getVariable(String varName) {
		for (int i = 0; i < allBlocks.size(); i++) { // checks if the variable is in the main arrayList of Blocks
			if (allBlocks.get(i) instanceof VariableBlock<?>) {
				if (((VariableBlock<?>) allBlocks.get(i)).getName().equals(varName)) {
					return ((VariableBlock<?>) allBlocks.get(i));
				}
			}
		}
		try {
			for (int i = 0; i < altBlocks.size(); i++) { // checks if the variable is in the secondary arrayList of
															// Blocks
				if (altBlocks.get(i) instanceof VariableBlock<?>) {
					if (((VariableBlock<?>) altBlocks.get(i)).getName().equals(varName)) {
						return ((VariableBlock<?>) altBlocks.get(i));
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
			// In case there are no blocks in altBlocks
		}
		return null;
	}

	public void error() {
		error = true;
		// In the future, there may be error reporting stating the cause of the error
	}

	public String run() {
		if (error)
			return "error"; // prevents any prints if there are errors
		String console = "";
		String newLine = System.getProperty("line.separator");

		for (Block<?> b : allBlocks) {
			if (b instanceof PrintBlock || b instanceof IfBlock) {
				console = console + b.execute() + newLine; // That way we get only the things that need to be printed to
															// show up as outputs
			} else {
				b.execute(); // Executes all non-prints & non-ifs
			}
		}

		return console;
	}

	public void createIf(String operand1, String operator, String operand2, int id) {
		if (operator == null || operand1 == null || operand2 == null) {
			error();
			return;
		}
		if (operator.equals("<")) {
			Double firstOperand = null; //operators can only be Doubles
			try {
				Double.parseDouble(operand1); //checks that operand is a double
				firstOperand = Double.parseDouble(operand1); 
			} catch (NumberFormatException e) {
				if (parseMath(operand1) != null) {
					firstOperand = parseMath(operand1); //checks if operand evaluates to double
				} else {
					error(); //There's a problem if not
					return;
				}
			}
			//Comments not repeated below because same things
			Double secondOperand = null;
			try {
				Double.parseDouble(operand2);
				secondOperand = Double.parseDouble(operand2);
			} catch (NumberFormatException e) {
				if (parseMath(operand2) != null) { 
					secondOperand = parseMath(operand2);
				} else {
					error();
					return;
				}
			}
			IfBlock i = new IfBlock(firstOperand < secondOperand);
			i.setId(id);
			allBlocks.add(i);
			//creates and adds if statement
		} else if (operator.equals(">")) {
			//Reasoning same as previous part of if construct
			Double firstOperand = null;
			try {
				Double.parseDouble(operand1);
				firstOperand = Double.parseDouble(operand1);
			} catch (NumberFormatException e) {
				if (parseMath(operand1) != null) {
					firstOperand = parseMath(operand1);
				} else {
					error();
					return;
				}
			}
			Double secondOperand = null;
			try {
				Double.parseDouble(operand2);
				secondOperand = Double.parseDouble(operand2);
			} catch (NumberFormatException e) {
				if (parseMath(operand2) != null) {
					secondOperand = parseMath(operand2);
				} else {
					error();
					return;
				}
			}
			IfBlock i = new IfBlock(firstOperand > secondOperand);
			i.setId(id);
			allBlocks.add(i);
		} else if (operator.equals("&&")) {
			Boolean firstOperand = null; //each operand must be a boolean
			if (operand1.replaceAll(" ", "").equals("true") || operand1.replaceAll(" ", "").equals("false")) {
				firstOperand = Boolean.parseBoolean(operand1);
			} else if (parseOperand(operand1) != null) { //Operand might be something like 1>2, this accounts for that possibility
				firstOperand = parseOperand(operand1);
			} else {
				error(); //No non-error options left
				return;
			}
			//same reasoning for second operand
			Boolean secondOperand = null;
			if (operand2.replaceAll(" ", "").equals("true") || operand2.replaceAll(" ", "").equals("false")) {
				secondOperand = Boolean.parseBoolean(operand2);
			} else if (parseOperand(operand1) != null) {
				secondOperand = parseOperand(operand2);
			} else {
				error();
				return;
			}
			IfBlock i = new IfBlock(firstOperand && secondOperand);
			i.setId(id);
			allBlocks.add(i);
		} else if (operator.equals("||")) {//essentially functions the same as the previous portion f the construct, so I didn't comment
			Boolean firstOperand = null;
			if (operand1.replaceAll(" ", "").equals("true") || operand1.replaceAll(" ", "").equals("false")) {
				firstOperand = Boolean.parseBoolean(operand1);
			} else if (parseOperand(operand1) != null) {
				firstOperand = parseOperand(operand1);
			} else {
				error();
				return;
			}

			Boolean secondOperand = null;
			if (operand2.replaceAll(" ", "").equals("true") || operand2.replaceAll(" ", "").equals("false")) {
				secondOperand = Boolean.parseBoolean(operand2);
			} else if (parseOperand(operand1) != null) {
				secondOperand = parseOperand(operand2);
			} else {
				error();
				return;
			}
			IfBlock i = new IfBlock(firstOperand || secondOperand);
			i.setId(id);
			allBlocks.add(i);
		}

		else if (operator.equals("==")) {
			if (parseMath(operand1) != null && parseMath(operand2) != null) { //if they both evaluate to a double, they are comparable
				IfBlock i = new IfBlock(parseMath(operand1).equals(parseMath(operand2)));
				i.setId(id);
				allBlocks.add(i);
			} else if (parseOperand(operand1) != null && parseOperand(operand2) != null) { //if they both evaluate to booleans, they are comparable
				IfBlock i = new IfBlock(parseOperand(operand1).equals(parseOperand(operand2)));
				i.setId(id);
				allBlocks.add(i);
			} else { //otherwise it just compares the strings
				IfBlock i = new IfBlock(operand1.equals(operand2));
				i.setId(id);
				allBlocks.add(i);
			}
		}
	}

	public Double parseMath(String s) {
		
		if (getVariable(s) != null && getVariable(s).execute() instanceof Double) { //if it's just a variable, it is returned
			return ((VariableBlock<Double>) getVariable(s)).execute();
		}
		String input = s.replaceAll(" ", ""); //removes whitespace
		if (input.contains("+")) { //checks if it is addition
			Double sum = 0.0;
			String[] operands = input.split("\\+");
			for (String str : operands) {
				try {
					Double d = Double.parseDouble(str); //if the first operand is a double, it is accounted for
					sum += d;
				} catch (NumberFormatException e) {
					if (getVariable(str) != null) {
						if (getVariable(str).execute() instanceof Double) { //if the first operand is a variable of type double, it is accounted for
							sum += (Double) getVariable(str).execute();
						}
					} else {
						return null;
					}
				}
			}
			return sum;
		} else if (input.contains("*")) { //this is essentially the same but with multiplication
			Double product = 1.0;
			String[] operands = input.split("\\*");
			for (String str : operands) {
				try {
					Double d = Double.parseDouble(str);
					product *= d;
				} catch (NumberFormatException e) {
					if (getVariable(str) != null) {
						if (getVariable(str).execute() instanceof Double) {
							product *= (Double) getVariable(str).execute();
						}
					} else {
						return null;
					}
				}
			}
			return product;
		} else if (input.contains("/")) { //this is essentially the same but with multiplication
			Double quotient = 1.0;
			String[] operands = input.split("/");
			for (int i = 0; i < operands.length; i++) {
				try {
					Double d = Double.parseDouble(operands[i]);
					if (i == 0) {
						quotient *= d;
					} else {
						quotient /= d;
					}
				} catch (NumberFormatException e) {
					if (getVariable(operands[i]) != null) {
						if (getVariable(operands[i]).execute() instanceof Double) {
							if (i == 0) {
								quotient *= (Double) getVariable(operands[i]).execute();
								;
							} else {
								quotient /= (Double) getVariable(operands[i]).execute();
								;
							}
						}
					} else {
						return null;
					}
				}
			}
			return quotient;
		} else if (input.contains("-")) { ///this is essentially the same but with subtraction
			Double difference = 0.0;
			String[] operands = input.split("-");
			for (int i = 0; i < operands.length; i++) {
				try {
					Double d = Double.parseDouble(operands[i]);
					if (i == 0) {
						difference += d;
					} else {
						difference -= d;
					}
				} catch (NumberFormatException e) {
					if (getVariable(operands[i]) != null) {
						if (getVariable(operands[i]).execute() instanceof Double) {
							if (i == 0) {
								difference += (Double) getVariable(operands[i]).execute();
								;
							} else {
								difference -= (Double) getVariable(operands[i]).execute();
								;
							}
						}
					} else {
						return null;
					}
				}
			}
			return difference;
		}

		return null; //if nothing works, the method returns null to signal that the input is not a mathematical statement
	}

	public Boolean parseOperand(String str) {
		if (getVariable(str) != null) {
			if (getVariable(str).execute() instanceof Boolean) { //if iit's a variable of type boolean, it evaluates to one
				return ((VariableBlock<Boolean>) getVariable(str)).execute();
			}
			return null;
		}
		String operand = str.replaceAll(" ", "");
		if (operand.contains("<")) {
			String[] operands = operand.split("<");
			if (operands.length != 2)// We currently allow for one operand
				return null;
			Double firstOperand = null; //With less than, both operands are doubles
			try {
				Double.parseDouble(operands[0]); //The operand could parse to a double
				firstOperand = Double.parseDouble(operands[0]);
			} catch (NumberFormatException e) {
				if (parseMath(operands[0]) != null) { //The operand could evaluate to a double
					firstOperand = parseMath(operands[0]);
				}
			}
			//Didn't comment because the second operand is the same
			Double secondOperand = null;
			try {
				Double.parseDouble(operands[1]);
				secondOperand = Double.parseDouble(operands[1]);
			} catch (NumberFormatException e) {
				if (parseMath(operands[1]) != null) {
					secondOperand = parseMath(operands[1]);
				} else {
					return null;
				}
			}

			return firstOperand < secondOperand;
		} else if (operand.contains(">")) { //Didn't comment because the reasoning is the same as the previous part of the construct

			String[] operands = operand.split(">");
			if (operands.length <= 1 || operands.length > 2)// We currently only allow one operand
				return null;
			Double firstOperand = null;
			try {
				Double.parseDouble(operands[0]);
				firstOperand = Double.parseDouble(operands[0]);
			} catch (NumberFormatException e) {
				if (parseMath(operands[0]) != null) {
					firstOperand = parseMath(operands[0]);
				}
			}
			Double secondOperand = null;
			try {
				Double.parseDouble(operands[1]);
				secondOperand = Double.parseDouble(operands[1]);
			} catch (NumberFormatException e) {
				if (parseMath(operands[1]) != null) {
					secondOperand = parseMath(operands[1]);
				} else {
					return null;
				}
			}

			return firstOperand > secondOperand;
		}
		return null;
	}

	public IfBlock getIf(int id) { //Just returns the first if statement with the given id
		for (Block b : allBlocks) {
			try {
				if (b instanceof IfBlock) {
					if (((IfBlock) b).getId() == id) {
						return (IfBlock) b;
					}
				}
			} catch (NullPointerException e) {
			}
		}
		return null;
	}

	public Block<?> get(int index) { //returns the ArrayList with the given index
		try {
			return allBlocks.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
}