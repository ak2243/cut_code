package cutcode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Executor {
	private String filename;
	private String command;
	public Executor(String filename, String terminalCommand) {
		this.filename = filename;
		command = terminalCommand;
	}

	public String execute(String language) throws BlockCodeCompilerErrorException{
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
}
