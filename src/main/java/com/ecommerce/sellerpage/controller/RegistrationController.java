package com.ecommerce.sellerpage.controller;

import com.ecommerce.sellerpage.*;
import com.ecommerce.sellerpage.Classes.ConnectionSingleton;
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
import java.util.List;

public class RegistrationController {

    @FXML
    public TextField mailField;

    @FXML
    public Label mailLabel;

    @FXML
    public TextField nameField;

    @FXML
    public Label nameLabel;

    @FXML
    public TextField noField;

    @FXML
    public Label noLabel;

    @FXML
    public PasswordField passField;

    @FXML
    public Label passLabel;

    @FXML
    public PasswordField rePassField;

    @FXML
    public Label rePassLabel;

    @FXML
    public Label outputLabel;
    @FXML
    public CheckBox showCheck;
    @FXML
    public TextField passShow;
    @FXML
    public TextField repassShow;


    @FXML
    public void regiAction(ActionEvent event) {
        List<String> userEmail = getListEmail();
        List<String> userMobile = getListMobile();

        String name = nameField.getText();
        String email = mailField.getText();
        String mobile = noField.getText();
        String password = passField.getText();
        String rePass = rePassField.getText();
        if (name.isEmpty()){
            nameLabel.setTextFill(Color.RED);
            nameLabel.setText("Enter your name");
            return;
        } else {
            nameLabel.setText("");
        }
        if (email.isEmpty()){
            mailLabel.setTextFill(Color.RED);
            mailLabel.setText("Enter your email");
            return;
        } else {
            mailLabel.setText("");
        }
        for (String x : userEmail){
            if (email.equals(x)){
                mailLabel.setTextFill(Color.RED);
                mailLabel.setText("Already exist");
                return;
            } else {
                mailLabel.setText("");
            }
        }
        if (mobile.isEmpty()){
            noLabel.setTextFill(Color.RED);
            noLabel.setText("Enter your mobile number");
            return;
        } else {
            noLabel.setText("");
        }
        for (String x : userMobile){
            if (mobile.equals(x)){
                noLabel.setTextFill(Color.RED);
                noLabel.setText("Already exist");
                return;
            } else {
                noLabel.setText("");
            }
        }
        if (password.isEmpty()){
            passLabel.setTextFill(Color.RED);
            passLabel.setText("Enter your password");
            return;
        } else {
            passLabel.setText("");
        }
        if (rePass.isEmpty()){
            rePassLabel.setTextFill(Color.RED);
            rePassLabel.setText("Enter re-type password");
            return;
        } else {
            rePassLabel.setText("");
        }

        if (password.equals(rePass)){
            User user = new User(name, email, mobile, password);

            insertIntoDB(user);

            outputLabel.setText("Register Successfully, Now your able to log in");

            nameField.setText("");
            mailField.setText("");
            noField.setText("");
            passField.setText("");
            rePassField.setText("");

//            HelloApplication.changeScene("homeScene");
        } else {
            rePassLabel.setTextFill(Color.RED);
            rePassLabel.setText("Not Match");
        }
    }

    public void insertIntoDB(User user) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO userInfo VALUES('"+ user.getName() + "', '" + user.getEmail() + "', '" + user.getMobile() + "', '" + user.getPassword() + "');";
            statement.execute(query);
            System.out.println("ADDED");
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Error");
        }
    }

    List<String> getListEmail(){
        List<String> emailList = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM userInfo"; //Table name
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String email = resultSet.getString("email");

                emailList.add(email);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
        return emailList;
    }
    List<String> getListMobile(){
        List<String> mobileList = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM userInfo"; //Table name
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String mobile = resultSet.getString("mobile");

                mobileList.add(mobile);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
        return mobileList;
    }

    @FXML
    public void back(){
        HelloApplication.changeScene("logInUser");
    }
    @FXML
    public void showPass(){
        if (showCheck.isSelected()){
            passShow.setVisible(true);
            passShow.setText(passField.getText());
            repassShow.setVisible(true);
            repassShow.setText(rePassField.getText());
            passField.setVisible(false);
            rePassField.setVisible(false);
        } else {
            passShow.setVisible(false);
            repassShow.setVisible(false);
            passField.setVisible(true);
            rePassField.setVisible(true);
        }
    }


}
