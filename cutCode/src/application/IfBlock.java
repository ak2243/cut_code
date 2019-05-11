package application;

import java.util.ArrayList;

public class IfBlock extends Block<String> {
	
	private ArrayList<Block<?>> contents;
	private BooleanOperator<?> condition;
	
	public IfBlock()
	{
		contents = new ArrayList<Block<?>>();
	}

	@Override
	public String execute() {
		if(condition.execute())
		{
			return "";
		}
		String console = "";
		String newLine = System.getProperty("line.separator");
		
		for(Block<?> b : contents)
		{
			if(b instanceof PrintBlock)
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

}
