package com.ecommerce.sellerpage.controller;

import com.ecommerce.sellerpage.Classes.ConnectionSingleton;
import com.ecommerce.sellerpage.HelloApplication;
import com.ecommerce.sellerpage.Classes.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddCartController implements Initializable {

    @FXML
    public TextField searchField;
    @FXML
    public Label outputLabel;
    @FXML
    public ObservableList<Product> productObservableList;
    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, String> nameCol;
    @FXML
    public TableColumn<Product, String> sizeCol;
    @FXML
    public TableColumn<Product, String> colorCol;
    @FXML
    public TableColumn<Product, Number> priceCol;
    @FXML
    public TableColumn<Product, Number> quantityCol;
    @FXML
    public TableColumn<Product, Number> totalCol;
    @FXML
    public Label totalLabel;
    protected Product product;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productObservableList = FXCollections.observableArrayList();
        List<Product> proList = getList();
        productObservableList.addAll(proList);
        productTable.setItems(productObservableList);

        nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressName()));
        sizeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressSize()));
        colorCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressColor()));
        priceCol.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getDressPrice()));
        quantityCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getDressQuantity()));
        totalCol.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getTotalPrice()));
        //For searchbar
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {filterData(newValue);});

        List<Double> totalPriceList = priceGetList();
        double price = totalPriceList.stream().mapToDouble(cell -> cell.doubleValue()).sum();
        totalLabel.setText(price + " BDT");
    }

    public List<Product> getList() {
        List<Product> productList = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM addcart WHERE id='" + LogInUserController.id + "';"; //Table name
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                String size = resultSet.getString("size");
                String color = resultSet.getString("color");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                double total = resultSet.getDouble("total_price");

                product = new Product(id, name, size, color, price, quantity, total);
                productList.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
        return productList;
    }
    //For searchbar
    private void filterData(String keyword) {
        ObservableList<Product> filteredData = FXCollections.observableArrayList();
        for (Product person : getList()) {
            if (person.getDressName().toLowerCase().contains(keyword.toLowerCase()) || person.getDressType().toLowerCase().contains(keyword.toLowerCase()) || String.valueOf(person.getDressId()).contains(keyword)) {
                filteredData.add(person);
            }
        }
        productTable.setItems(filteredData);
    }

    @FXML
    public void back(){
        HelloApplication.changeScene("homeScene");
    }
    @FXML
    public void onClickTable(){
        System.out.println("pore");
    }
    @FXML
    public void buy(){
        HelloApplication.changeScene("buyScene");
    }

    public List<Double> priceGetList() {
        List<Double> priceList = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM addcart WHERE id='" + LogInUserController.id + "';"; //Table name
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                double total = resultSet.getDouble("total_price");
                priceList.add(total);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
        return priceList;
    }

}

