package cutcode;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FunctionBuilderRow extends HBox {
	private ComboBox<String> type;
	private TextField name;
	public FunctionBuilderRow(String[] types, double width, double height) {
		name = new TextField();
		name.setMaxSize(width / 2, height);
		name.setMinSize(width / 2, height);
		if(types != null) {
			type = new ComboBox<String>(FXCollections.observableArrayList(FXCollections.observableArrayList(types)));
			type.setMaxSize(width / 2, height);
			type.setMinSize(width / 2, height);
			this.getChildren().add(type);
		} else {
			name.setMaxSize(width, height);
			name.setMinSize(width, height);
		}

		this.getChildren().add(name);
	}
	
	public String getName() {
		return name.getText();
	}
	
	public String getType() {
		return type.getValue();
	}
	
	@Override
	public String toString() {
		String ret = getName();
		if(type != null) {
			ret = getType() + " " + ret;
		}
		return ret;
	}
}
