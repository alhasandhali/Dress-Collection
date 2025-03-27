package com.ecommerce.sellerpage.controller;

import com.ecommerce.sellerpage.Classes.Admin;
import com.ecommerce.sellerpage.Classes.ConnectionSingleton;
import com.ecommerce.sellerpage.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogInSellerController {

    @FXML
    public TextField emailField;

    @FXML
    public Label outputLabel;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField showPass;

    @FXML
    public CheckBox showCheck;

    @FXML
    public void logIn(ActionEvent event) {
        HashMap<String, String> userInfo = new HashMap<>();

        for (Admin admin : getListAdmin()) {
            userInfo.put(admin.getId(), admin.getPass());
            if (userInfo.containsKey(emailField.getText()) && userInfo.get(emailField.getText()).equals(passwordField.getText())) {
                outputLabel.setTextFill(Color.BLACK);
                outputLabel.setText("Login Successfully");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Log In Successfully");
                HelloApplication.changeScene("sellerDashboardScene");
            } else {
                outputLabel.setTextFill(Color.RED);
                outputLabel.setText("Invalid UserID or Password");
            }
        }
    }
    private List<Admin> getListAdmin(){
        List<Admin> adminList = new ArrayList<>();

        try{
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM admin";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String password = resultSet.getString("pass");

                Admin admin = new Admin(id, password);
                adminList.add(admin);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
        return adminList;
    }

    @FXML
    public void back(){
        HelloApplication.changeScene("logInUser");
    }
    @FXML
    public void passShow(){
        if (showCheck.isSelected()){
            showPass.setVisible(true);
            showPass.setText(passwordField.getText());
            passwordField.setVisible(false);
        } else {
            showPass.setVisible(false);
            passwordField.setVisible(true);
        }
    }

}
