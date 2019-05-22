package application;

import java.util.*;

public class Builder {
	ArrayList<Block<?>> allBlocks;

	public Builder() {
		allBlocks = new ArrayList<Block<?>>();
	}

	/**
	 * 
	 * @param type: the type of variable. Valid types: number, boolean, string
	 * @param value: the value of the variable
	 * @param varName: the name of the variable
	 */
	public void createVariable(String varName, String value) {

		if (value.equals("true") || value.equals("false")) {
			VariableBlock<Boolean> v = new VariableBlock<Boolean>();
			v.setValue(Boolean.parseBoolean(value));
			if (getVariable(varName) != null) {
				if (getVariable(varName).execute() instanceof Boolean) {
					((VariableBlock<Boolean>) getVariable(varName)).setValue(Boolean.parseBoolean(value));
				} else {
					error();
				}
			} else {
				v.setName(varName);
				allBlocks.add(v);
			}
		} else {
			try {
				Double.parseDouble(value);
				VariableBlock<Double> v = new VariableBlock<Double>();
				v.setValue(Double.parseDouble(value));

				if (getVariable(varName) != null) {
					if (getVariable(varName).execute() instanceof Double) {
						((VariableBlock<Double>) getVariable(varName)).setValue(Double.parseDouble(value));
					} else {
						error();
					}
				} else {
					v.setName(varName);
					allBlocks.add(v);
				}

			} catch (NumberFormatException e) {
				if (parseMath(value) != null) {
					VariableBlock<Double> v = new VariableBlock<Double>();
					v.setValue(parseMath(value));

					if (getVariable(varName) != null) {
						if (getVariable(varName).execute() instanceof Double) {
							((VariableBlock<Double>) getVariable(varName)).setValue(parseMath(value));
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

					if (getVariable(varName) != null) {
						if (getVariable(varName).execute() instanceof String) {
							((VariableBlock<String>) getVariable(varName)).setValue(value);
						} else {
							error();
						}
					} else {
						v.setName(varName);
						allBlocks.add(v);
					}
				}
			}
		}

	}

	public void print(String s) {
		PrintBlock p = new PrintBlock();
		if (getVariable(s) != null) {
			p.setPrint("" + getVariable(s).execute());
		} else if (parseMath(s) != null) {
			p.setPrint("" + parseMath(s));
		} else {
			p.setPrint(s);
		}
		allBlocks.add(p);
	}

	/**
	 * 
	 * @param varName
	 * @return returns null if variable not found,
	 */
	public VariableBlock<?> getVariable(String varName) {
		for (int i = 0; i < allBlocks.size(); i++) {
			if (allBlocks.get(i) instanceof VariableBlock<?>) {
				if (((VariableBlock<?>) allBlocks.get(i)).getName().equals(varName)) {
					return ((VariableBlock<?>) allBlocks.get(i));
				}
			}
		}
		return null;
	}

	public void error() {
		System.err.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	public String run() {
		String console = "";
		String newLine = System.getProperty("line.separator");

		for (Block<?> b : allBlocks) {
			if (b instanceof PrintBlock || b instanceof IfBlock) {
				console = console + b.execute() + newLine;
			} else {
				b.execute();
			}
		}

		return console;
	}

	public void createIf(String operand1, String operator, String operand2, int id) {
		if (operator.equals("<")) {
			Double firstOperand = null;
			try {
				Double.parseDouble(operand1);
				firstOperand = Double.parseDouble(operand1);
			} catch (NumberFormatException e) {
				if (parseMath(operand1) != null) {
					firstOperand = parseMath(operand1);
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
			IfBlock i = new IfBlock(firstOperand < secondOperand);
			i.setId(id);
			allBlocks.add(i);
		} else if (operator.equals(">")) {
			Double firstOperand = null;
			try {
				Double.parseDouble(operand1);
				firstOperand = Double.parseDouble(operand1);
			} catch (NumberFormatException e) {
				if (parseMath(operand1) != null) {
					firstOperand = parseMath(operand1);
				}
			}
			Double secondOperand = null;
			try {
				Double.parseDouble(operand1);
				secondOperand = Double.parseDouble(operand1);
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
			IfBlock i = new IfBlock(firstOperand && secondOperand);
			i.setId(id);
			allBlocks.add(i);
		} else if (operator.equals("||")) {
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

			if (operand1.equals(operand2)) {
				IfBlock i = new IfBlock(true);
				i.setId(id);
				allBlocks.add(i);
			} else if (parseMath(operand1) != null && parseMath(operand2) != null) {
				IfBlock i = new IfBlock(parseMath(operand1).equals(parseMath(operand2)));
				i.setId(id);
				allBlocks.add(i);
			} else if (parseOperand(operand1) != null && parseOperand(operand2) != null) {
				IfBlock i = new IfBlock(parseOperand(operand1).equals(parseOperand(operand2)));
				i.setId(id);
				allBlocks.add(i);
			}
		}
	}

	public Double parseMath(String s) {
		String input = s.replaceAll(" ", "");
		if (input.contains("+")) {
			Double sum = 0.0;
			String[] operands = input.split("\\+");
			for (String str : operands) {
				try {
					Double d = Double.parseDouble(str);
					sum += d;
				} catch (NumberFormatException e) {
					if (getVariable(str) != null) {
						if (getVariable(str).execute() instanceof Double) {
							sum += (Double) getVariable(str).execute();
						}
					} else {
						return null;
					}
				}
			}
			return sum;
		} else if (input.contains("*")) {
			System.err.println("hello");
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
		} else if (input.contains("/")) {
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
		} else if (input.contains("-")) {
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

		return null;
	}

	public Boolean parseOperand(String str) {
		if (getVariable(str) != null) {
			if (getVariable(str).equals("true") || getVariable(str).equals("false")) {
				return Boolean.parseBoolean(((VariableBlock<String>) getVariable(str)).execute());
			}
			return null;
		}
		String operand = str.replaceAll(" ", "");
		if (operand.equals("<")) {
			System.err.println("HOLA");
			String[] operands = operand.split("<");
			if (operands.length <= 1 || operands.length > 2)// TODO: Maybe allow for more than one operand
				return null;
			Double firstOperand = null;
			try {
				Double.parseDouble(operands[1]);
			} catch (NumberFormatException e) {
				if (parseMath(operands[0]) != null) {
					firstOperand = parseMath(operands[0]);
				}
			}
			Double secondOperand = null;
			try {
				Double.parseDouble(operands[1]);
			} catch (NumberFormatException e) {
				if (parseMath(operands[1]) != null) {
					secondOperand = parseMath(operands[1]);
				} else {
					return null;
				}
			}

			return firstOperand < secondOperand;
		} else if (operand.equals(">")) {

			String[] operands = operand.split(">");
			if (operands.length <= 1 || operands.length > 2)// TODO: Maybe allow for more than one operand
				return null;
			Double firstOperand = null;
			try {
				Double.parseDouble(operands[1]);
			} catch (NumberFormatException e) {
				if (parseMath(operands[0]) != null) {
					firstOperand = parseMath(operands[0]);
				}
			}
			Double secondOperand = null;
			try {
				Double.parseDouble(operands[1]);
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

	public IfBlock getIf(int id)
	{
		for(Block b : allBlocks)
		{
			try
			{
				if(b instanceof IfBlock)
				{
					if(((IfBlock) b).getId() == id)
					{
						return (IfBlock) b;
					}
				}
			}
			catch (NullPointerException e) {}
		}
		return null;
	}
	public Block<?> get(int index)
	{
		try
		{
			return allBlocks.get(index);
		}
		catch(IndexOutOfBoundsException e)
		{
			return null;
		}
	}
}