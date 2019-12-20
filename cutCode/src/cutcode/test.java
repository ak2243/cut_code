package cutcode;

import java.util.ArrayList;
import java.util.List;

import logicalBlocks.Block;
import logicalBlocks.PrintBlock;

public class test {
	public static void main(String[] args) {
		Executor e = new Executor();
		System.err.println(e.run("program.java"));
		
	}
}