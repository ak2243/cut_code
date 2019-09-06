package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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
		VariableBlock t;
		if (value.contains("\"")) {
			t = new StringBlock();
			t.setValue(value);
			t.setName(varName);
		} else {
			try {
				Double.parseDouble(value);
				t = new DoubleBlock();
				t.setValue(Double.parseDouble(value));
				t.setName(varName);
			} catch (Exception e) {
				Boolean.parseBoolean(value);
				t = new BooleanBlock();
				t.setValue(Boolean.parseBoolean(value));
				t.setName(varName);
			}
		}
		allBlocks.add(t);

	}

	public void print(String s) {
		PrintBlock p = new PrintBlock();
		p.setPrint(s);
		allBlocks.add(p);
	}

	/**
	 * 
	 * @param varName
	 * @return returns null if variable not found,
	 */

	public void error() {
		error = true;
		// In the future, there may be error reporting stating the cause of the error
	}

	public String run() {
		// TODO: SEND TO WRITING MECHANISM
		File f = new File("Program.java");
		f.delete();
		String code = "public class Program{ public static void main(String[] args) {";
		for (Block<?> b : allBlocks) {
			code = code + b.execute();
		}
		code = code + "}}";

		String output = "";
		Process p;
		Process p2;
		try {
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("Program.java"), "utf-8"));
			writer.write(code);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		try
		{
			p = Runtime.getRuntime().exec("javac Program.java");
			p.waitFor();
			p2 = Runtime.getRuntime().exec("java Program");
			
			BufferedReader stream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			boolean error = false;
			String inn = stream.readLine();
			while (inn != null) {
				System.err.println(inn);
				inn = stream.readLine();
				error = true;
			}
			if(error)
			{
				stream.close();
				return "Error";
			}
			BufferedReader inStream = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			p2.getOutputStream().flush();
			String in = inStream.readLine();
			while (in != null) {
				output = output + in + System.lineSeparator();
				in = inStream.readLine();
			}
			inStream.close();
			
		}
		catch(IOException e)
		{
			
		}
		catch(InterruptedException ex)
		{
			
		}

		return output;
	}

	public void createIf(String operand1, String operator, String operand2, int id) {
		// TODO
		IfBlock i = new IfBlock(operand1 + operator + operand2);
		i.setId(id);
		allBlocks.add(i);
	}

	public IfBlock getIf(int id) { // Just returns the first if statement with the given id
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

	public Block<?> get(int index) { // returns the ArrayList with the given index
		try {
			return allBlocks.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

}