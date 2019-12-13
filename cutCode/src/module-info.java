module cutCode {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;

    opens cutcode to javafx.fxml;
    exports cutcode;
}