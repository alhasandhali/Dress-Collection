package com.ecommerce.sellerpage.controller;

import com.ecommerce.sellerpage.Classes.ConnectionSingleton;
import com.ecommerce.sellerpage.HelloApplication;
import com.ecommerce.sellerpage.Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogInUserController {

    @FXML
    public TextField emailField;

    @FXML
    public Label outputLabel;

    @FXML
    public TextField showPass;

    @FXML
    public CheckBox showCheck;

    @FXML
    public PasswordField passwordField;
    protected static String id;

    @FXML
    public void logIn(ActionEvent event) {
        id = emailField.getText();
        HashMap<String, String> userInfo = new HashMap<>();

        for (User user : getListUser()) {
            userInfo.put(user.getEmail(), user.getPassword());
            if (userInfo.containsKey(emailField.getText()) && userInfo.get(emailField.getText()).equals(passwordField.getText())) {
                outputLabel.setTextFill(Color.BLACK);
                outputLabel.setText("Login Successfully");
                HelloApplication.changeScene("homeScene");
            } else {
                outputLabel.setTextFill(Color.RED);
                outputLabel.setText("Invalid UserID or Password");
            }
        }
    }

    private List<User> getListUser(){
        List<User> userList = new ArrayList<>();

        try{
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM userInfo";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String mobile = resultSet.getString("mobile");
                String password = resultSet.getString("password");

                User user = new User(name, email, mobile, password);
                userList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
        return userList;
    }

    @FXML
    public void back(){
        HelloApplication.changeScene("homeScene");
    }
    @FXML
    public void register() {
        System.out.println("Amar kono kaj nai vai aijagay!!!");
        outputLabel.setText("Register");
        HelloApplication.changeScene("registration");
    }
    @FXML
    public void sellerAction(){
        HelloApplication.changeScene("logInSeller");
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
