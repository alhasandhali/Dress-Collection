package com.ecommerce.sellerpage.controller;

import com.ecommerce.sellerpage.*;
import com.ecommerce.sellerpage.Classes.ConnectionSingleton;
import com.ecommerce.sellerpage.Classes.Product;
import com.ecommerce.sellerpage.Classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BuyController implements Initializable {

    @FXML
    public TextArea addressArea;

    @FXML
    public ChoiceBox<String> cityBox;

    @FXML
    public TextArea commentArea;

    @FXML
    public TextField couponField;

    @FXML
    public TextField emaildField;

    @FXML
    public TextField mobileField;

    @FXML
    public TextField nameField;

    @FXML
    public Label nameLabel;

    @FXML
    public Label outputLabel;

    @FXML
    public Label priceLabel;

    @FXML
    public Label quantityLabel;

    @FXML
    public ToggleGroup toggleGroup;

    @FXML
    public Label totalLabel;

    @FXML
    public TextField voucherField;
    @FXML
    public Label confirmOutput;

    @FXML
    public void confirm(ActionEvent event) {
        String name = nameField.getText();
        String address = addressArea.getText();
        String mobile = mobileField.getText();
        String email = emaildField.getText();
        String city = cityBox.getValue();
        String comment = commentArea.getText();
        RadioButton genderRadio = (RadioButton) toggleGroup.getSelectedToggle();
        String payment = genderRadio.getText();
        String date = String.valueOf(LocalDate.now());

        if (name.isEmpty()){
            outputLabel.setText("Enter your name");
            return;
        }
        if (address.isEmpty()){
            outputLabel.setText("Enter your address");
            return;
        }
        if (mobile.isEmpty()){
            outputLabel.setText("Enter your number");
            return;
        }
        if (email.isEmpty()){
            outputLabel.setText("Enter your e-mail");
            return;
        }
        if (city == null){
            outputLabel.setText("Select your city");
            return;
        }

        User user = new User(name, email, mobile, address, city, comment);

//        insertIntoDB(user);
//
//        insertIntoDB2(HomeSceneController.productB, email, date, payment);

        updateFromDB(HomeSceneController.productU, HomeSceneController.productB, HomeSceneController.id);

        outputLabel.setText("Confirm Order");
        outputLabel.setTextFill(Color.GREEN);
        confirmOutput.setText("Congratulation!!! Waiting for 2-3 working days");
    }

    @FXML
    public void coupon(ActionEvent event) {
        List<Product> codeList = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM dresscollection";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");

                Product pro = new Product(id, code);

                codeList.add(pro);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Somossa ace");
        }

        String cupon = couponField.getText();
        for (Product pro : codeList){
            if (cupon.equals(pro.getDiscountCode()) && HomeSceneController.id == pro.getDressId()){
                totalLabel.setText("Total price: " + ((HomeSceneController.productB.getDressPrice()) * (HomeSceneController.productB.getDressQuantity()) / 0.15) + " BDT");
            } else {
                outputLabel.setText("No Coupon exist");
            }
        }
        System.out.println(codeList);
    }

    @FXML
    public void voucher(ActionEvent event) {
        List<Product> codeList = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM dresscollection";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");

                Product pro = new Product(id, code);

                codeList.add(pro);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Somossa ace");
        }

        String cupon = couponField.getText();
        for (Product pro : codeList){
            if (cupon.equals(pro.getDiscountCode()) && HomeSceneController.id == pro.getDressId()){
                totalLabel.setText("Total price: " + ((HomeSceneController.productB.getDressPrice()) * (HomeSceneController.productB.getDressQuantity()) / 0.2) + " BDT");
            } else {
                outputLabel.setText("No Vaucher exist");
            }
        }
        System.out.println(codeList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getList();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("Dhaka");
        observableList.add("Barishal");
        observableList.add("Chattogram");
        observableList.add("Khulna");
        observableList.add("Rajshahi");
        observableList.add("Rangpur");
        observableList.add("Mymensingh");
        observableList.add("Sylhet");
        cityBox.setItems(observableList);
        nameLabel.setText("Name: " + HomeSceneController.productB.getDressName());
        priceLabel.setText("Price: " + HomeSceneController.productB.getDressPrice());
        quantityLabel.setText("Quantity: " + HomeSceneController.productB.getDressQuantity());
        totalLabel.setText("Total price: " + (HomeSceneController.productB.getDressPrice()) * (HomeSceneController.productB.getDressQuantity()) + " BDT");

        System.out.println(HomeSceneController.id);
    }
//    public void insertIntoDB(User user) {
//        try {
//            Connection connection = ConnectionSingleton.getConnection();
//            Statement statement = connection.createStatement();
//            String query = "INSERT INTO customer VALUES('"+ user.getName() + "', '" + user.getEmail() + "', '" + user.getMobile() + "', '" + user.getCity() + "', '" + user.getAddress() + "', '" + user.getComment() + "');";
//            statement.execute(query);
//            System.out.println("ADDED");
//        } catch (SQLException ex){
//            ex.printStackTrace();
//            System.out.println("Error");
//        }
//    }
//    public void insertIntoDB2(Product product, String email, String date, String payment) {
//        try {
//            Connection connection = ConnectionSingleton.getConnection();
//            Statement statement = connection.createStatement();
//            String query = "INSERT INTO orders VALUES('"+ email + "', '" + product.getDressName() + "', " + product.getDressQuantity() + ", " + (product.getDressQuantity() * product.getDressPrice()) + ", '" + date + "', '" + payment +"');";
//            statement.execute(query);
//            System.out.println("ADDED");
//        } catch (SQLException ex){
//            ex.printStackTrace();
//            System.out.println("Error");
//        }
//    }
    public void updateFromDB(Product product1, Product product2, int id) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "UPDATE dresscollection SET quantity=" + (product1.getDressQuantity() - product2.getDressQuantity()) + " WHERE id=" + id + ";";
            statement.execute(query);
            System.out.println("UPDATE");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
    }

    @FXML
    public void back(){
        HelloApplication.changeScene("homeScene");
    }

    public void getList() {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM userinfo WHERE email='" + LogInUserController.id + "';"; //Table name
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("mobile");

                nameField.setText(name);
                mobileField.setText(phone);
                emaildField.setText(email);
                nameField.setDisable(true);
                mobileField.setDisable(true);
                emaildField.setDisable(true);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
    }
}
