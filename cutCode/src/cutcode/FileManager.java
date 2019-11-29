package cutcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private File input;
	private File output;
	private BufferedReader reader;
	private BufferedWriter writer;

	/**
	 * @param filename the file to be read
	 */
	public FileManager(String filename) {
		input = new File(filename);
		try {
			reader = new BufferedReader(new FileReader(input));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

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

	/**
	 * 
	 * @param out name of the output file. creates one if file does not exist
	 * @throws IOException may happen when creating the file
	 */
	public void setOutput(String out) throws IOException {
		output = new File(out);
		output.createNewFile();
	}
}
