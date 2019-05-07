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
	public void createVariable(String type, String varName, String value) {
		String varType = type.toLowerCase();
		if(type == null || varName == null || value == null)
		{
			return;
		}
		if(getVariable(varName) != null)
		{
			error();
			return;
		}
		switch (varType) {
			case "number": {
				VariableBlock<Double> v = new VariableBlock<Double>();
				v.setName(varName);
				try {
					v.setValue(Double.parseDouble(value));
				} catch (NumberFormatException e) {
					error();
					return;
				}
				allBlocks.add(v);
				break;
			}
			case "boolean": {
				VariableBlock<Boolean> v = new VariableBlock<Boolean>();
				v.setName(varName);
				v.setValue(Boolean.parseBoolean(value));
				allBlocks.add(v);
				break;
		 	}
			case "string": {
				VariableBlock<String> v = new VariableBlock<String>();
				v.setName(varName);
				v.setValue(value);
				break;
			}
		}

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
		System.err.println("ERROR");
	}
	
	public void recompile()
	{
		allBlocks.clear();
	}
}
