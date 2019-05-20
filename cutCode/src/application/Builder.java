package application;

import java.util.*;

public class Builder {
	ArrayList<Block<?>> allBlocks; 

	public Builder()
	{
		allBlocks = new ArrayList<Block<?>>();
	}
	
	/**
	 * 
	 * @param type: the type of variable. Valid types: number, boolean, string
	 * @param value: the value of the variable
	 * @param varName: the name of the variable
	 */
	public void createVariable( String varName, String value) {
		if(value.equals("true") || value.equals("false"))
		{
			
			VariableBlock<Boolean> v = new VariableBlock<Boolean>();
			v.setValue(Boolean.parseBoolean(value));
			allBlocks.add(v);
			v.setName(varName);
		}
		else
		{
			try
			{
				Double.parseDouble(value);
				VariableBlock<Double> v = new VariableBlock<Double>();
				v.setValue(Double.parseDouble(value));
				allBlocks.add(v);
				v.setName(varName);
			}
			catch(NumberFormatException e)
			{
				if(parseMath(value) != null)
				{
					VariableBlock<Double> v = new VariableBlock<Double>();
					v.setValue(parseMath(value));
					allBlocks.add(v);
					v.setName(varName);
				}
				else
				{
					VariableBlock<String> v = new VariableBlock<String>();
					v.setValue(value);
					allBlocks.add(v);
					v.setName(varName);
				}
			}
		}
		

	}
	
	public void print(String s)
	{
		PrintBlock p = new PrintBlock();
		if(getVariable(s) != null)
		{
			p.setPrint("" + getVariable(s).execute());
		}
		else
		{
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
	
	public void recompile()
	{
		allBlocks.clear();
	}
	
	public String run()
	{
		String console = "";
		String newLine = System.getProperty("line.separator");
		
		for(Block<?> b : allBlocks)
		{
			if(b instanceof PrintBlock || b instanceof IfBlock)
			{
				console = console + b.execute() + newLine;
			}
			else
			{
				b.execute();
			}
		}
		
		return console;
	}

	public void createIf(BooleanOperator<?> condition)
	{
		IfBlock i = new IfBlock(condition.execute());
		allBlocks.add(i);
	}
	
	public void setVar(VariableBlock<String> variable, String s)
	{
		//TODO: fix
		variable.setValue(s);
	}
	public void setVar(VariableBlock<Double> variable, double d)
	{
		//TODO: fix
		variable.setValue(d);
	}
	public void setVar(VariableBlock<Boolean> variable, boolean b)
	{
		//TODO: fix
		variable.setValue(b);
	}
	public Double parseMath(String s )
	{
		String input = s.replaceAll("\\s+","");
		if(input.contains("+"))
		{
			Double sum = 0.0;
			String[] operands = input.split("\\+");
			for(String str : operands)
			{
				try
				{
					Double d = Double.parseDouble(str);
					sum += d;
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
			return sum;
		}
		else if (input.contains("\\*"))
		{
			Double product = 1.0;
			String[] operands = input.split("\\*");
			for(String str : operands)
			{
				try
				{
					Double d = Double.parseDouble(str);
					product *= d;
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
			return product;
		}
		else if (input.contains("\\/"))
		{
			Double quotient = 0.0;
			String[] operands = input.split("\\/");
			for(String str : operands)
			{
				try
				{
					Double d = Double.parseDouble(str);
					if(str.equals(operands[0]))
					{
						quotient *= d;
					}
					else
					{
						quotient /= d;
					}
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
			return quotient;
		}
		else if (input.contains("\\-"))
		{
			Double difference = 0.0;
			String[] operands = input.split("\\-");
			for(String str : operands)
			{
				try
				{
					Double d = Double.parseDouble(str);
					difference -= d;
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
			return difference;
		}
		
		return null;
	}
	
}
