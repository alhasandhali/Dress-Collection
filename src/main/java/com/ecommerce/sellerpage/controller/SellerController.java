package com.ecommerce.sellerpage.controller;

import com.ecommerce.sellerpage.Interfaces.CRUDInterface;
import com.ecommerce.sellerpage.Classes.ConnectionSingleton;
import com.ecommerce.sellerpage.HelloApplication;
import com.ecommerce.sellerpage.Classes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SellerController implements Initializable, CRUDInterface {
    @FXML
    public TextField dressIdField;
    @FXML
    public TextField dressNameField;
    @FXML
    public ChoiceBox<String> dressTypeChoice;
    @FXML
    public ComboBox<String> sizeCombo;
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Slider priceSlider;
    @FXML
    public TextArea dressDetailsArea;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Spinner<Integer> quantitySpinner;
    @FXML
    public PasswordField discountField;
    public ToggleGroup genderToggle;
    @FXML
    public CheckBox checkBox;
    @FXML
    public ImageView imageViewer;
    @FXML
    public Label saveOutput;
    @FXML
    public Label idError;
    @FXML
    public Label nameError;
    @FXML
    public Label typeError;
    @FXML
    public Label sizeError;
    @FXML
    public Label colorError;
    @FXML
    public Label priceError;
    @FXML
    public Label detailsError;
    @FXML
    public Label dateError;
    @FXML
    public Label quantityError;
    @FXML
    public Label codeError;
    @FXML
    public Label genderError;
    @FXML
    public Label imageError;
    @FXML
    public Button saveButton;
    @FXML
    public Button updateButton;
    protected static Product product;
    protected static  Product product1;
    protected static Image imageShow;
    @FXML
    public void showList() {
        HelloApplication.changeScene("sellerDashboardScene");
    }
    @FXML
    public void save() {
        //Take this for Discount Code check
        List<Product> codeList1 = getList();

        //Validation for Product ID
        int dressId = 0;

        if (dressIdField.getText().isEmpty()){
            idError.setText("Enter product ID");
            return;
        } else{
            dressId = Integer.parseInt(dressIdField.getText());
            idError.setText("");
        }

        if (dressId <= 0){
            idError.setText("ID should be greater than zero");
            return;
        } else {
            idError.setText("");
        }

        for (Product pro: codeList1){
            if (pro.getDressId() == dressId){
                idError.setText("ID already exist");
                return;
            } else {
                idError.setText("");
            }
        }

        //store values in a variable from user input
        String dressName = dressNameField.getText();
        String dressType = dressTypeChoice.getValue();
        String dressSize = sizeCombo.getValue();
        Color color = colorPicker.getValue();
//        String dressColor = String.valueOf(color).toUpperCase();
        String dressColor = toRGBCode(color);
        System.out.println(dressColor);
        double dressPrice = Double.parseDouble((String.valueOf(String.format("%.0f", priceSlider.getValue()))));
        String dressDetails = dressDetailsArea.getText();
        LocalDate date = datePicker.getValue();
        String purchaseDate = String.valueOf(date);
        int dressQuantity = quantitySpinner.getValue();
        String discountCode = discountField.getText();
        RadioButton genderRadio = (RadioButton) genderToggle.getSelectedToggle();
        String gender = genderRadio.getText();
        boolean check = checkBox.isSelected();
        Image image = imageViewer.getImage();

        //Assign in global variable for access from other controller
        imageShow = image;

        //Condition for null field
        if (dressName.isEmpty()){
            nameError.setText("Dress name is required");
            return;
        } else {
            nameError.setText("");
        }
        if (dressType == null){
            typeError.setText("Dress type is required");
            return;
        } else {
            typeError.setText("");
        }
        if (dressSize == null){
            sizeError.setText("Dress size is required");
            return;
        } else {
            sizeError.setText("");
        }
        if (dressColor == null){
            colorError.setText("Dress color is required");
            return;
        } else {
            colorError.setText("");
        }
        if (dressPrice < 500){
            priceError.setText("Price can't be less than 500");
            return;
        } else {
            priceError.setText("");
        }
        if (dressDetails.isEmpty()) {
            detailsError.setText("Dress details is required");
            return;
        } else {
            detailsError.setText("");
        }
        if (dressDetails.length() >= 50){
            detailsError.setText("Only 50 characters are allowed");
            return;
        } else {
            detailsError.setText("");
        }
        if (date == null){
            dateError.setText("Date is required");
            return;
        } else {
            dateError.setText("");
        }
        if (date.isAfter(LocalDate.now())){
            dateError.setText("Date can't be future date");
            return;
        } else {
            dateError.setText("");
        }
        if (dressQuantity <= 0){
            quantityError.setText("Quantity can't be less than zero");
            return;
        } else {
            quantityError.setText("");
        }
        for (Product pro: codeList1){
            if (pro.getDiscountCode().equals(discountCode)){
                codeError.setText("Same code already exist");
                return;
            } else {
                codeError.setText("");
            }
        }
        if (!genderRadio.isSelected()){
            genderError.setText("Targeted customer is required");
            return;
        } else {
            genderError.setText("");
        }
        if (image == null){
            imageError.setText("Picture is required");
            return;
        } else {
            imageError.setText("");
        }

        //user input store in a class throw the objects
        product = new Product(dressId, dressName, dressType, dressSize, dressColor, dressPrice, dressDetails, purchaseDate, dressQuantity, discountCode, gender, check);

        //Upload in database table
        insertIntoDB(product);

        saveOutput.setText("Dress Information saved in Database");
    }
    @FXML
    public void imageUploader() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"));
        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(null);
        // Load and display the selected image
        if (selectedFile != null) {
            Image selectedImage = new Image(selectedFile.toURI().toString());
            imageViewer.setImage(selectedImage);
        }
    }


    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("Hoodie");
        type.add("Sweater");
        type.add("Shirt");
        type.add("Pant");
        type.add("T-Shirt");
        dressTypeChoice.setItems(type);

        ObservableList<String> size = FXCollections.observableArrayList();
        size.add("Extra Small");
        size.add("Small");
        size.add("Medium");
        size.add("Large");
        size.add("Extra Large");
        sizeCombo.setItems(size);

        ListView<String> selectedItemsListView = new ListView<>();
        sizeCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {selectedItemsListView.getItems().setAll(sizeCombo.getSelectionModel().getSelectedItem());});

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500);
        quantitySpinner.setValueFactory(valueFactory);

        updateButton.setVisible(false);

        if (SellerDashboardController.productU != null) {
            dressIdField.setText(String.valueOf(SellerDashboardController.productU.getDressId()));

            dressNameField.setText(SellerDashboardController.productU.getDressName());
            dressTypeChoice.setValue(SellerDashboardController.productU.getDressType());
            sizeCombo.setValue(SellerDashboardController.productU.getDressSize());
            colorPicker.setValue(Color.web(SellerDashboardController.productU.getDressColor()));
            priceSlider.setValue(SellerDashboardController.productU.getDressPrice());
            dressDetailsArea.setText(SellerDashboardController.productU.getDressDetails());
            datePicker.setValue(LocalDate.parse(SellerDashboardController.productU.getPurchaseDate()));
            quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, SellerDashboardController.productU.getDressQuantity()));
            discountField.setText(SellerDashboardController.productU.getDiscountCode());
            checkBox.setSelected(SellerDashboardController.productU.isCheckBox());
            dressIdField.setDisable(true);
            saveButton.setVisible(false);
            updateButton.setVisible(true);
        }
    }
    public List<Product> getList() {
        List<Product> productList = new ArrayList<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM dresscollection"; //Table name
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");

                product1 = new Product(id, code);
                productList.add(product1);
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
            String query = "INSERT INTO dresscollection VALUES("+ product.getDressId() + ", '" + product.getDressName() + "', '" + product.getDressType() + "', '" + product.getDressSize() + "', '" + product.getDressColor() + "', " + product.getDressPrice() + ", '" + product.getDressDetails() + "', '" + product.getPurchaseDate() + "', " + product.getDressQuantity() + ", '" + product.getDiscountCode() + "', '" + product.getCustomerGender() + "', '" + (product.isCheckBox() ? 1 : 0) + "');";
            statement.execute(query);
            System.out.println("ADDED");
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Error");
        }
    }

    @Override
    public void updateFromDB(Product product) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "UPDATE dresscollection SET name='" + product.getDressName() + "', type='" + product.getDressType() + "', size='" + product.getDressSize() + "', color='" + product.getDressColor() + "', price=" + product.getDressPrice() +", details='" + product.getDressDetails() + "', date='" + product.getPurchaseDate() + "', quantity=" + product.getDressQuantity() + ", code='" + product.getDiscountCode() + "', gender='" + product.getCustomerGender() + "', button=" + (product.isCheckBox() ? 1 : 0) + " WHERE id=" + product.getDressId() + ";";
            statement.execute(query);
            System.out.println("UPDATE");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Try again later.");
        }
    }

    @Override
    public void deleteFromDB(Product product) {
        System.out.println("Amar kono kaj nai vai aijagay!!!");
    }

    @FXML
    public void update(){
        int dressId = Integer.parseInt(dressIdField.getText());
        String dressName = dressNameField.getText();
        String dressType = dressTypeChoice.getValue();
        String dressSize = sizeCombo.getValue();
        Color color = colorPicker.getValue();
        String dressColor = toRGBCode(color);
        System.out.println(dressColor);
        double dressPrice = Double.parseDouble((String.valueOf(String.format("%.0f", priceSlider.getValue()))));
        String dressDetails = dressDetailsArea.getText();
        LocalDate date = datePicker.getValue();
        String purchaseDate = String.valueOf(date);
        int dressQuantity = quantitySpinner.getValue();
        String discountCode = discountField.getText();
        RadioButton genderRadio = (RadioButton) genderToggle.getSelectedToggle();
        String gender = genderRadio.getText();
        boolean check = checkBox.isSelected();

        Product product = new Product(dressId, dressName, dressType, dressSize, dressColor, dressPrice, dressDetails, purchaseDate, dressQuantity, discountCode, gender, check);
        updateFromDB(product);
    }
}
