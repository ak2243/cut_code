package cutcode;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FunctionBuilderRow extends HBox {
	private TextField name;
	private ComboBox<String> type;

	/**
	 * 
	 * @param types  - an array of all the types that the user can pick for the
	 *               parameter. null if parameters are untyped. if not null, the
	 *               first should be 'void' or an equivalent
	 * @param width  - the width of this row
	 * @param height - the height of this row
	 */
	public FunctionBuilderRow(String[] types, double width, double height) {
		String[] paramTypes = null;
		if (types != null) {
			paramTypes = new String[types.length - 1];
			for (int i = 1; i < types.length; i++) {
				paramTypes[i - 1] = types[i]; // if paramTypes exists, the first one if 'void' or equivalent, and isn't
												// a valid variable type
			}
		}
		name = new TextField(); // the name of the parameter variable
		name.setMaxSize(width / 2, height);
		name.setMinSize(width / 2, height);
		if (types != null) { // sets up variable typing if thats a part of the language
			type = new ComboBox<String>(
					FXCollections.observableArrayList(FXCollections.observableArrayList(paramTypes)));
			type.setValue(paramTypes[0]);
			type.setMaxSize(width / 2, height);
			type.setMinSize(width / 2, height);
			this.getChildren().add(type);
		} else {
			name.setMaxSize(width, height);
			name.setMinSize(width, height);
		}

		this.getChildren().add(name);
	}

	/**
	 * 
	 * @return the name of this parameter
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * 
	 * @return the type of this parameter. null if no typing
	 */
	public String getType() {
		if(type == null) {
			return null;
		}
		return type.getValue();
	}

	@Override
	public String toString() {
		String ret = getName();
		if (type != null) {
			ret = getType() + " " + ret;
		}
		return ret;
	}
}
