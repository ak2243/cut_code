package cutcode;

import java.io.IOException;
import logicalBlocks.Block;
import graphics.Sequence;
public class test {
	public static void main(String[] args) throws IOException {
		Executor e = new Executor();
		FileManager manager = new FileManager();
		manager.setOutput("Program.java");
		LList<Sequence<Block>> list = new LList<Sequence<Block>>();
		Sequence<Block> sequence = new Sequence<Block>();
		logicalBlocks.FunctionBlock function = new logicalBlocks.FunctionBlock();
		function.setSignature(logicalBlocks.FunctionBlock.MAIN);
		logicalBlocks.PrintBlock print = new logicalBlocks.PrintBlock();
		print.setPrint("\"hello wolrd\"");
		logicalBlocks.IfBlock iffer = new logicalBlocks.IfBlock();
		logicalBlocks.AndBlock and = new logicalBlocks.AndBlock();
		logicalBlocks.BooleanBlock truth = new logicalBlocks.BooleanBlock();
		truth.setName("hiya");
		truth.setValue(true);
		and.setLeftOperand(truth.getVariableCall());
		and.setRightOperand(truth.getVariableCall());
		iffer.setCondition(and);
		function.commands.add(truth);
		function.commands.add(iffer);
		function.commands.add(print);
		manager.openWriter();
		sequence.add(function);
		list.add(sequence);
		manager.write(e.getCode(list));
		manager.closeWriter();
		System.err.println(e.run("Program.java"));
		
	}
}