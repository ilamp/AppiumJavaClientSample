module com.cx.ibot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.client;
    requires org.seleniumhq.selenium.remote_driver;


    opens com.cx.ibot to javafx.fxml;
    exports com.cx.ibot;
}