package application;

import java.util.ArrayList;

public class IfBlock extends Block<String> {
	
	private ArrayList<Block<?>> contents;
	private ArrayList<Block<?>> elseContents;
	private boolean condition;
	
	public IfBlock(boolean condition)
	{
		this.condition =  condition;
		contents = new ArrayList<Block<?>>();
		elseContents = new ArrayList<Block<?>>();
	}

	public void addToContents(Block<?> b)
	{
		contents.add(b);
	}
	
	public void addToElse(Block<?> b)
	{
		elseContents.add(b);
	}
	
	@Override
	public String execute() {
		if(!condition)
		{
			String console = "";
			String newLine = System.getProperty("line.separator");
			
			for(Block<?> b : elseContents)
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
