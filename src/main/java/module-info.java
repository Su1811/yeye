module Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens Application to javafx.fxml;
    exports Application;
}