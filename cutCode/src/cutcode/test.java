package cutcode;

import java.io.IOException;

public class test {

	public static void main(String[] args) {
		FileManager manager = new FileManager();
		
		try {
			
			manager.setOutput("program.java");
			manager.openWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manager.write("");
		manager.closeWriter();
	}

}
