package factories;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.Executor;
import cutcode.FileManager;
import cutcode.LogicalBlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JavaExecutor extends Executor {
	private String filename;
	public static final String PREFIX = "public class Program {" + System.lineSeparator() + "	public static void main (String[] args) {" + System.lineSeparator();
	public static final String SUFFIX = "	}" + System.lineSeparator() + "}";
	public JavaExecutor(String filename) {
		this.filename = filename;
	}


	@Override
	public String execute() throws BlockCodeCompilerErrorException {
		Process p;
		Process p2;
		try {
			p = Runtime.getRuntime().exec("javac Program.java"); // this will compile the program
			p.waitFor();
			p2 = Runtime.getRuntime().exec("java Program"); // this will run the program

			BufferedReader errorStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// this is where the process outputs error messages


			String errorLine = errorStream.readLine(); // takes the error statement. null if no errors
			if(errorLine != null) {
				throw new BlockCodeCompilerErrorException();
			}

			errorStream.close();
			BufferedReader outputStream = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			String line = outputStream.readLine();
			String output = "";
			while(line != null) {
				output += line + System.lineSeparator();
				line = outputStream.readLine();
			}
			outputStream.close();
			return output;

		} catch (InterruptedException | IOException e) {
			return "An unexpected error occured"; // No way to forsee this
		}
	}

	@Override
	public void export(String code, String filename) throws IOException {
		FileManager manager = new FileManager();
		manager.delete(filename);
		manager.setOutput(filename);
		manager.openWriter();
		manager.write(PREFIX);

		manager.write(code);
		manager.write(SUFFIX);
		manager.closeWriter();
	}

	@Override
	public void export(List<LogicalBlock> logicalBlocks, String filename) throws IOException {
		FileManager manager = new FileManager();
		manager.setOutput(filename);
		manager.openWriter();
		manager.write(PREFIX);
		for (LogicalBlock block : logicalBlocks)
			manager.write(block.toString());
		manager.write(SUFFIX);
		manager.closeWriter();
	}


}
