package cutcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private File input;
	private File output;
	private BufferedReader reader;
	private BufferedWriter writer;

	/** 
	 * @return the next line in the file
	 */
	public String get() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * @param write: writes this String to the output file
	 */
	public void write(String write) {
		try {
			writer.write(write);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes all writer
	 */
	public void closeWriter() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the reader
	 */
	public void closeReader() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException if the filename is not valid
	 */
	public void openWriter() throws IOException {
		writer = new BufferedWriter(new FileWriter(output));
	}
	
	public void openReader() throws IOException {
		reader = new BufferedReader(new FileReader(input));
	}

	/**
	 * 
	 * @param out name of the output file. creates one if file does not exist
	 * @throws IOException may happen when creating the file
	 */
	public void setOutput(String out) throws IOException {
		output = new File(out);
		output.createNewFile();
	}
	
	public void setInput(String in) throws IOException {
		input = new File(in);
		input .createNewFile();
	}
	
	public void delete(String filename) {
		File f = new File(filename);
	}
}
