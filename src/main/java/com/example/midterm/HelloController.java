package com.example.midterm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class HelloController {
    @FXML
    private TextField orderTotalOrderNumberTF;

    @FXML
    private TableColumn<Total, Double> orderTotalColumn;
    @FXML
    private TableView<Total> ordersTotalTable;
    @FXML
    private TableColumn<Total,Integer> orderTotalOrderNumberColumn;




    private Connection connection;

    ObservableList<Total> list;

    @FXML
    private void initialize() throws ClassNotFoundException{
        createConnection();
        list = FXCollections.observableArrayList();
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
            list.add(new Total(resultSet.getInt(1), resultSet.getDouble(2)));
        }

        orderTotalOrderNumberColumn.setCellValueFactory(new PropertyValueFactory<Total,Integer>("orderId"));
        orderTotalColumn.setCellValueFactory(new PropertyValueFactory<Total,Double>("total"));
        ordersTotalTable.setItems(list);
    }
}

