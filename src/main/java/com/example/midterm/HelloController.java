package com.example.midterm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class HelloController {
    // Menu Items
    /*----------------------------------------*/
    @FXML
    private MenuItem orderTotalFilter;

    @FXML
    private MenuItem orderDetailsFilter;

    @FXML
    private MenuItem customerInformationFilter;

    @FXML
    private MenuItem employeesFilter ;
    /*----------------------------------------*/


    // Get Order Total Id's
    /*----------------------------------------*/
    @FXML
    private TextField orderTotalOrderNumberTF;

    @FXML
    private TableColumn<Total, Double> orderTotalColumn;
    @FXML
    private TableView<Total> ordersTotalTable;
    @FXML
    private TableColumn<Total,Integer> orderTotalOrderNumberColumn;
    /*----------------------------------------*/

    // Get Order Details
    /*----------------------------------------*/
    @FXML
    private TextField orderNumberOrderDetailsTF;

    @FXML
    private TableView<OrderDetails> ordersDetailsTable;

    @FXML
    private TableColumn<OrderDetails, Date> orderDateColumn;

    @FXML
    private TableColumn<OrderDetails,Double> orderFreightCharge;

    @FXML
    private TableColumn<OrderDetails,String> orderProductColumn;

    @FXML
    private TableColumn<OrderDetails,Integer> orderQuantityColumn;

    @FXML
    private TableColumn<OrderDetails,Double> orderUnitPriceColumn;

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);


    private Connection connection;

    ObservableList<Total> totals = FXCollections.observableArrayList();
    ObservableList<OrderDetails> details = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws ClassNotFoundException{
        createConnection();

    }

    private void changeLayout(){

    }


    private void createConnection() throws ClassNotFoundException{
        try{
            // load the database driver
            Class.forName("com.mysql.jdbc.Driver");

            // Creates the connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "admin");
            System.out.println("connected");
        }catch (SQLException sqlException){
            System.out.println("Not Connected");
        }
    }


    @FXML
    protected void filterTotal() throws SQLException {
        ordersTotalTable.getItems().clear();
        int orderNumber = Integer.parseInt(orderTotalOrderNumberTF.getText());

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select orders.OrderID, Sum(Price * Quantity) from orders, orderdetails, products where orders.OrderID = '" + orderNumber + "' AND orderdetails.OrderID = orders.OrderID AND orderdetails.ProductID = products.ProductID");

        while (resultSet.next()) {
            totals.add(new Total(resultSet.getInt(1), resultSet.getDouble(2)));
        }

        orderTotalOrderNumberColumn.setCellValueFactory(new PropertyValueFactory<Total,Integer>("orderId"));
        orderTotalColumn.setCellValueFactory(new PropertyValueFactory<Total,Double>("total"));
        ordersTotalTable.setItems(totals);
    }

    @FXML
    protected void filterOrderDetails() throws SQLException {
        ordersDetailsTable.getItems().clear();
        int orderNumber = Integer.parseInt(orderNumberOrderDetailsTF.getText());

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select OrderDate, ProductName, Quantity, Price from northwind.orders,northwind.orderdetails, northwind.products where northwind.orders.OrderID = '" + orderNumber + "' AND northwind.orderdetails.OrderID = northwind.orders.OrderID AND northwind.orderdetails.ProductID = northwind.products.ProductID");

        while (resultSet.next()) {
            details.add(new OrderDetails(resultSet.getDate(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getDouble(4)));
        }

        orderDateColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails,Date>("orderDate"));
        orderProductColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails,String>("product"));
        orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails,Integer>("quantity"));
        orderUnitPriceColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails,Double>("unitPrice"));
        ordersDetailsTable.setItems(details);
    }
}

