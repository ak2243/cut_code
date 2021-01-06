package Java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cutcode.BlockCodeCompilerErrorException;
import cutcode.GraphicalBlock;
import cutcode.InvalidNestException;
import cutcode.LogicalBlock;
import cutcode.OutputView;
import javafx.stage.Stage;

public class MainFunctionBlock extends GraphicalFunctionBlock {
	List<GraphicalBlock> insideGraphicalBlocks;
	List<LogicalBlock> inside;

	/**
	 * 
	 * A backend block used so that we can put all the play area blocks inside a main function when necessary.
	 * @param width - the width of this block (irrelevant since it's a backend block)
	 * @param height - the height of this block (also irrelevant since it's a backend block)
	 */
	public MainFunctionBlock(double width, double height) {
		super(width, height);
		inside = new ArrayList<>();
		insideGraphicalBlocks = new ArrayList<>();
	}

	@Override
	public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
		try {
			inside.add(nest.getLogicalBlock());
			insideGraphicalBlocks.add(nest);
		} catch (BlockCodeCompilerErrorException e) {
			throw new InvalidNestException();
		}
	}

	@Override
	public LogicalBlock getLogicalBlock() throws BlockCodeCompilerErrorException {
		String[] params = { "String[] args" };
		return logicalFactory.createFunctionBlock(getIndentFactor() - 1, "main", "void", params, inside);
	}

	@Override
	public int putInHashMap(HashMap<Integer, GraphicalBlock> lineLocations) {
		lineLocations.put(getLineNumber(), this);
		int ret = getLineNumber() + 1;
		for (GraphicalBlock b : insideGraphicalBlocks) {
			b.setLineNumber(ret);
			ret = b.putInHashMap(lineLocations);
		}
		return ret + logicalFactory.getEndingBrace();
	}

}
