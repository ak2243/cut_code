module cutCode {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens cutcode to javafx.fxml;
    exports cutcode;
}