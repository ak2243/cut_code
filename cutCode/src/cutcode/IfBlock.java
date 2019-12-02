package cutcode;

import java.util.ArrayList;

public class IfBlock extends Block{
	private String condition;

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String execute() {
		return "if(" + condition + ") {" + System.lineSeparator();
	}

}
