package com.ecommerce.sellerpage.controller;

import com.ecommerce.sellerpage.Interfaces.CRUDInterface;
import com.ecommerce.sellerpage.Classes.ConnectionSingleton;
import com.ecommerce.sellerpage.HelloApplication;
import com.ecommerce.sellerpage.Classes.Product;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class SellerDashboardController implements Initializable, CRUDInterface {
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
    public Label showDateLabel;
    @FXML
    public Label showQuantityLabel;
    @FXML
    public Label showDiscountLabel2;
    @FXML
    public Label showGenderLabel;
    @FXML
    public Label showCheckLabel;
    @FXML
    public ImageView showImage;
    @FXML
    public ObservableList<Product> productObservableList;
    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, Number> idCol;
    @FXML
    public TableColumn<Product, String> nameCol;
    @FXML
    public TableColumn<Product, String> typeCol;
    @FXML
    public TableColumn<Product, String> colorCol;
    @FXML
    public TableColumn<Product, Number> priceCol;
    @FXML
    public TableColumn<Product, String> purchaseCol;
    @FXML
    public TableColumn<Product, Number> quantityCol;
    @FXML
    public TableColumn<Product, Boolean> boostedCol;
    protected List<Product> productList;
    protected static Product productU;
    protected Product pro;
    protected Product pro1;
    protected Product selectedRow;
    @FXML
    public void passShow() {
        showDiscountLabel2.setText(pro.getDiscountCode());
        System.out.println("Password Show");
    }
    @FXML
    public void delete() {
        onClickTable();

        deleteFromDB(selectedRow);

        productObservableList.clear();
        List<Product> productList = getList();
        productObservableList.addAll(productList);
    }
    @FXML
    public void edit() {
        onClickTable();
        HelloApplication.changeScene("sellerScene");
        System.out.println(productU.getDressId());
    }
    @FXML
    public void back() {
        HelloApplication.changeScene("sellerScene");
    }
    @FXML
    public void logOut() {
        HelloApplication.changeScene("logInSeller");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productObservableList = FXCollections.observableArrayList();
        List<Product> productList = getList();
        List<Product> assendingList = productList.stream().sorted(Comparator.comparing(Product::getDressId)).toList();
        productObservableList.addAll(assendingList);
        productTable.setItems(productObservableList);

        idCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getDressId()));
        nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressName()));
        typeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressType()));
        colorCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDressColor()));
        priceCol.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getDressPrice()));
        purchaseCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPurchaseDate()));
        quantityCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getDressQuantity()));
        boostedCol.setCellValueFactory(cell -> new SimpleBooleanProperty(cell.getValue().isCheckBox()));

        showIdLabel.setText(pro.getDressId() + "");
        showNameLabel.setText(pro.getDressName());
        showTypeLabel.setText(pro.getDressType());
        showSizeLabel.setText(pro.getDressSize());
        showColorLabel.setText(pro.getDressColor());
        showPriceLabel.setText(pro.getDressPrice() + " BDT");
        showDetailsLabel.setText(pro.getDressDetails());
        showDateLabel.setText(pro.getPurchaseDate());
        if (pro.getDressQuantity() <= 10){
            showQuantityLabel.setTextFill(Color.RED);
            showQuantityLabel.setText(pro.getDressQuantity() + " Unit");
        } else{
            showQuantityLabel.setText(pro.getDressQuantity() + " Unit");
        }
        showGenderLabel.setText(pro.getCustomerGender());
        showCheckLabel.setText((pro.isCheckBox()) ? "Enable" : "Disable");
        showImage.setImage(SellerController.imageShow);

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
                pro1 = new Product(id, name, type, color, price, date, quantity, checkBox);
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
        System.out.println("Amar kono kaj nai vai aijagay!!!");
    }

    @Override
    public void updateFromDB(Product product) {
        System.out.println("Amar kono kaj nai vai aijagay!!!");
    }

    @Override
    public void deleteFromDB(Product product) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "DELETE FROM dresscollection WHERE id=" + selectedRow.getDressId() + ";";
            statement.execute(query);
            System.out.println("DELETED");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
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

        showIdLabel.setText(String.valueOf(productU.getDressId()));
        showNameLabel.setText(productU.getDressName());
        showTypeLabel.setText(productU.getDressType());
        showSizeLabel.setText(productU.getDressSize());
        showColorLabel.setText(productU.getDressColor());
        showPriceLabel.setText(String.valueOf(productU.getDressPrice()));
        showDetailsLabel.setText(productU.getDressDetails());
        showDateLabel.setText(productU.getPurchaseDate());
        showDiscountLabel2.setText(productU.getDiscountCode());
        showGenderLabel.setText(productU.getCustomerGender());
        showCheckLabel.setText(String.valueOf(productU.isCheckBox()));
        showQuantityLabel.setText(String.valueOf(productU.getDressQuantity()));

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
    public void orderList(){
        System.out.println("Wait");
    }
}
