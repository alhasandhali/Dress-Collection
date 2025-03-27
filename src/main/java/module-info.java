module com.ecommerce.sellerpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.ecommerce.sellerpage to javafx.fxml;
    exports com.ecommerce.sellerpage;
    exports com.ecommerce.sellerpage.controller;
    exports com.ecommerce.sellerpage.Classes;
    opens com.ecommerce.sellerpage.Classes to javafx.fxml;
    exports com.ecommerce.sellerpage.Interfaces;
    opens com.ecommerce.sellerpage.Interfaces to javafx.fxml;
}