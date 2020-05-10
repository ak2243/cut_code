package factories;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.Executor;
import cutcode.FileManager;
import cutcode.LogicalBlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PythonExecutor extends Executor {
	private String filename, command;
	public PythonExecutor(String filename, String terminalCommand) {
		this.filename = filename;
		command = terminalCommand;
	}


	@Override
	public String execute() throws BlockCodeCompilerErrorException {
		Process p;
		try {

			p = Runtime.getRuntime().exec(command + " " + filename); // this will run the program

			BufferedReader errorStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// this is where the process outputs error messages


			String errorLine = errorStream.readLine(); // takes the error statement. null if no errors
			if(errorLine != null) {
				throw new BlockCodeCompilerErrorException();
			}

			errorStream.close();
			BufferedReader outputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = outputStream.readLine();
			String output = "";
			while(line != null) {
				output += line + System.lineSeparator();
				line = outputStream.readLine();
			}
			outputStream.close();
			return output;

		} catch (IOException e) {
			e.printStackTrace();
			return "An unexpected error occured";
		}
	}

	@Override
	public void export(String code, String filename) throws IOException {
		FileManager manager = new FileManager();
		manager.delete(filename);
		manager.setOutput(filename);
		manager.openWriter();
		manager.write(code);
		manager.closeWriter();
	}

	@Override
	public void export(List<LogicalBlock> logicalBlocks, String filename) throws IOException {
		FileManager manager = new FileManager();
		manager.setOutput(filename);
		manager.openWriter();
		for (LogicalBlock block : logicalBlocks)
			manager.write(block.toString());
		manager.closeWriter();
	}
}
