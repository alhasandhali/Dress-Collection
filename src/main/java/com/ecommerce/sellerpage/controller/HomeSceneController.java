package com.ecommerce.sellerpage.controller;

import com.ecommerce.sellerpage.Interfaces.CRUDInterface;
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
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class HomeSceneController implements Initializable, CRUDInterface {

    @FXML
    public Spinner<Integer> spinnerField;
    @FXML
    public TextField searchField;
    @FXML
    public Label showIdLabel;
    @FXML
    public Label showNameLabel;
    @FXML
    public Label showTypeLabel;
    @FXML
    public Label showSizeLabel;
    @FXML
    public Label showColorLabel;
    @FXML
    public Label showPriceLabel;
    @FXML
    public Label showDetailsLabel;
    @FXML
    public Label stockOut;
    @FXML
    public Label showGenderLabel;
    @FXML
    public Label outputLabel;
    @FXML
    public ObservableList<Product> productObservableList;
    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, String> nameCol;
    @FXML
    public TableColumn<Product, String> typeCol;
    @FXML
    public TableColumn<Product, String> colorCol;
    @FXML
    public TableColumn<Product, Number> priceCol;
    @FXML
    public TableColumn<Product, Number> quantityCol;
    @FXML
    public TableColumn<Product, String> detailsCol;
    @FXML
    public TableColumn<Product, String> sizeCol;
    protected List<Product> productList;
    protected static Product productU;
    protected static Product productB;
    protected static Product productC;
    protected Product pro;
    protected Product pro1;
    protected Product selectedRow;
    protected static int id;
    private double total;
    @FXML
    public void buyNow() {
        String name = selectedRow.getDressName();
        double price = selectedRow.getDressPrice();
        int quantity = spinnerField.getValue();
        id = selectedRow.getDressId();
        if (quantity <= 0) {
            outputLabel.setTextFill(Color.RED);
            outputLabel.setText("Enter Quantity");
            return;
        }
        productB = new Product(name, price, quantity);
        outputLabel.setTextFill(Color.BLACK);
        outputLabel.setText("Buy Now");
        HelloApplication.changeScene("buyScene");
    }
    @FXML
    public void addToCart() {
        int quantity = spinnerField.getValue();
        if (quantity <= 0) {
            outputLabel.setTextFill(Color.RED);
            outputLabel.setText("Enter Quantity");
            return;
        }
        int id = selectedRow.getDressId();
        String name = selectedRow.getDressName();
        String size = selectedRow.getDressSize();
        String color = selectedRow.getDressColor();
        double price = selectedRow.getDressPrice();
        total = selectedRow.getDressPrice() * quantity;

        productC = new Product(id, name, size, color, price, quantity);

        insertIntoDB(productC);

//        System.out.println("Amar kono kaj nai vai aijagay!!!");
        outputLabel.setText("Add to Cart");
//        System.out.println(productC.getDressQuantity());
    }
    @FXML
    public void logOut(){
        HelloApplication.changeScene("logInUser");
    }
    @FXML
    public void goCart(){
        HelloApplication.changeScene("add-cart");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productObservableList = FXCollections.observableArrayList();
        productList = getList();
        List<Product> assendingList = productList.stream().sorted(Comparator.comparing(Product::getDressId)).toList();
        productObservableList.addAll(assendingList);
        productTable.setItems(productObservableList);

        nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressName()));
        typeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressType()));
        colorCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressColor()));
        priceCol.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getDressPrice()));
        quantityCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getDressQuantity()));
        detailsCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressDetails()));
        sizeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressSize()));

//        showIdLabel.setText(pro.getDressId() + "");
//        showNameLabel.setText(pro.getDressName());
//        showTypeLabel.setText(pro.getDressType());
//        showSizeLabel.setText(pro.getDressSize());
//        showColorLabel.setText(pro.getDressColor());
//        showPriceLabel.setText(pro.getDressPrice() + " BDT");
//        showDetailsLabel.setText(pro.getDressDetails());
//        showGenderLabel.setText(pro.getCustomerGender());
//        if (pro.getDressQuantity() <= 10){
//            showQuantityLabel.setTextFill(Color.RED);
//            showQuantityLabel.setText(pro.getDressQuantity() + " Unit");
//        } else{
//            showQuantityLabel.setText(pro.getDressQuantity() + " Unit");
//        }
//        CREATE USER 'root'@'%' IDENTIFIED BY 'dhali';
//        GRANT SELECT, INSERT, UPDATE, DELETE ON dress.* TO 'root'@'%';
//
//
//        SELECT * FROM customer LEFT JOIN orders ON customer.email = orders.email;

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500);
        spinnerField.setValueFactory(valueFactory);

        //For searchbar
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {filterData(newValue);});

        }
    //Get info from database
    public List<Product> getList() {
        List<Product> productList = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM dresscollection"; //Table name
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String size = resultSet.getString("size");
                String color = resultSet.getString("color");
                double price = resultSet.getDouble("price");
                String details = resultSet.getString("details");
                String date = resultSet.getString("date");
                int quantity = resultSet.getInt("quantity");
                String code = resultSet.getString("code");
                String gender = resultSet.getString("gender");
                boolean checkBox = resultSet.getBoolean("button");

                pro = new Product(id, name, type, size, color, price, details, date, quantity, code, gender, checkBox);
                pro1 = new Product(id, name, type, size, color, price, details, quantity);
                productList.add(pro1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
        return productList;
    }

    @Override
    public void insertIntoDB(Product product) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO addcart VALUES('"+ LogInUserController.id + "', '" + productC.getDressId() + "', '" + productC.getDressName() + "', '" + productC.getDressSize() + "', '" + productC.getDressColor() + "', " + productC.getDressPrice() + ", " + productC.getDressQuantity() + ", '" + total + "');";
            statement.execute(query);
            System.out.println("ADDED");
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Error");
        }
    }

    @Override
    public void updateFromDB(Product product) {
        System.out.println("Amar kono kaj nai vai aijagay!!!");
    }

    @Override
    public void deleteFromDB(Product product) {
        System.out.println("Amar kono kaj nai vai aijagay!!!");
    }

    //For Table Row select
    public void onClickTable(){
        selectedRow = productTable.getSelectionModel().getSelectedItem();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM dresscollection WHERE id=" + selectedRow.getDressId() + ";"; //Table name
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String size = resultSet.getString("size");
                String color = resultSet.getString("color");
                double price = resultSet.getDouble("price");
                String details = resultSet.getString("details");
                String date = resultSet.getString("date");
                int quantity = resultSet.getInt("quantity");
                String code = resultSet.getString("code");
                String gender = resultSet.getString("gender");
                boolean checkBox = resultSet.getBoolean("button");

                productU = new Product(id, name, type, size, color, price, details, date, quantity, code, gender, checkBox);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }

        showIdLabel.setText(productU.getDressName());
        showNameLabel.setText(productU.getDressSize());
        showTypeLabel.setText(productU.getDressType());
        showSizeLabel.setText(productU.getDressSize());
        showColorLabel.setText(productU.getDressColor());
        showPriceLabel.setText(String.valueOf(productU.getDressPrice()));
        showDetailsLabel.setText(productU.getDressDetails());
        showGenderLabel.setText(productU.getCustomerGender());
        if (productU.getDressQuantity() <= 0){
            stockOut.setVisible(true);
            spinnerField.setVisible(false);
        } else {
            stockOut.setVisible(false);
            spinnerField.setVisible(true);
        }

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
}

