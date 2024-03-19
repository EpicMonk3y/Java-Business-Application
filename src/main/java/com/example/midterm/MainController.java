package com.example.midterm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class MainController {
    // Menu Items
    /*----------------------------------------*/
    @FXML
    private MenuItem orderTotalFilter;

    @FXML
    private MenuItem orderDetailsFilter;

    @FXML
    private MenuItem customerICountryFilter;

    @FXML
    private MenuItem employeesBirthYearFilter;

    @FXML
    private MenuBar menuBar;
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
    private AnchorPane orderTotalAP;
    /*----------------------------------------*/

    // Get Order Details  Id's
    /*----------------------------------------*/
    @FXML
    private TextField orderNumberOrderDetailsTF;

    @FXML
    private TableView<OrderDetails> ordersDetailsTable;

    @FXML
    private TableColumn<OrderDetails, Date> orderDateColumn;

    @FXML
    private TableColumn<OrderDetails,String> orderProductColumn;

    @FXML
    private TableColumn<OrderDetails,Integer> orderQuantityColumn;

    @FXML
    private TableColumn<OrderDetails,Double> orderUnitPriceColumn;

    @FXML
    private AnchorPane orderDetailsAP;

    /*----------------------------------------*/

    // Get Customer Info  Id's
    /*----------------------------------------*/

    @FXML
    private TextField customerInfoTF;

    @FXML
    private TableView<CustomerInfo> customersTable;

    @FXML
    private TableColumn<CustomerInfo, String> customerNameColumn;

    @FXML
    private TableColumn<CustomerInfo,String> customerCityColumn;

    @FXML
    private AnchorPane customerInfoAP;

    // Get Customer Info Id's
    /*----------------------------------------*/

    @FXML
    private TextField yearTF;

    @FXML
    private TableView<EmployeeInfo> employeeNameTable;

    @FXML
    private TableColumn<EmployeeInfo, String> employeeFirstNameColumn;

    @FXML
    private TableColumn<EmployeeInfo, String> employeeLastNameColumn;

    @FXML
    private AnchorPane employeeInfoAP;
    /*----------------------------------------*/


    private Connection connection;

    ObservableList<Total> totals = FXCollections.observableArrayList();
    ObservableList<OrderDetails> orderDetails = FXCollections.observableArrayList();

    ObservableList<CustomerInfo> customerDetails = FXCollections.observableArrayList();

    ObservableList<EmployeeInfo> employeeDetails = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws ClassNotFoundException{
        createConnection();
        changeLayout();

    }

    // Changes the layout of the application base on which menu is chosen
    private void changeLayout(){
        // Order Total Filter Layout
        orderTotalFilter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                orderTotalAP.setVisible(true);
                orderDetailsAP.setVisible(false);
                customerInfoAP.setVisible(false);
                employeeInfoAP.setVisible(false);
                customersTable.getItems().clear();
                employeeNameTable.getItems().clear();
                ordersDetailsTable.getItems().clear();
                ordersTotalTable.getItems().clear();
                orderTotalOrderNumberTF.clear();
                orderNumberOrderDetailsTF.clear();
                customerInfoTF.clear();
                yearTF.clear();
            }
        });
        // Order Details Filter Layout
        orderDetailsFilter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                orderTotalAP.setVisible(false);
                orderDetailsAP.setVisible(true);
                customerInfoAP.setVisible(false);
                employeeInfoAP.setVisible(false);
                customersTable.getItems().clear();
                employeeNameTable.getItems().clear();
                ordersDetailsTable.getItems().clear();
                ordersTotalTable.getItems().clear();
                orderTotalOrderNumberTF.clear();
                orderNumberOrderDetailsTF.clear();
                customerInfoTF.clear();
                yearTF.clear();
            }
        });
        // Customer Country Filter Layout
        customerICountryFilter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                orderTotalAP.setVisible(false);
                orderDetailsAP.setVisible(false);
                customerInfoAP.setVisible(true);
                employeeInfoAP.setVisible(false);
                customersTable.getItems().clear();
                employeeNameTable.getItems().clear();
                ordersDetailsTable.getItems().clear();
                ordersTotalTable.getItems().clear();
                orderTotalOrderNumberTF.clear();
                orderNumberOrderDetailsTF.clear();
                customerInfoTF.clear();
                yearTF.clear();
            }
        });
        // Employees Birth Year Filter Layout
        employeesBirthYearFilter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                orderTotalAP.setVisible(false);
                orderDetailsAP.setVisible(false);
                customerInfoAP.setVisible(false);
                employeeInfoAP.setVisible(true);
                customersTable.getItems().clear();
                employeeNameTable.getItems().clear();
                ordersDetailsTable.getItems().clear();
                ordersTotalTable.getItems().clear();
                orderTotalOrderNumberTF.clear();
                orderNumberOrderDetailsTF.clear();
                customerInfoTF.clear();
                yearTF.clear();
            }
        });


    }

    // Creating Access to the Database
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

    // Filter by total
    @FXML
    protected void filterTotal() throws SQLException {
        ordersTotalTable.getItems().clear();
        int orderNumber = Integer.parseInt(orderTotalOrderNumberTF.getText());

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select Sum(Price * Quantity) from orders, orderdetails, products where orders.OrderID = '" + orderNumber + "' AND orderdetails.OrderID = orders.OrderID AND orderdetails.ProductID = products.ProductID");

        while (resultSet.next()) {
            totals.add(new Total(resultSet.getDouble(1)));
        }

        orderTotalColumn.setCellValueFactory(new PropertyValueFactory<Total,Double>("total"));
        ordersTotalTable.setItems(totals);
    }

    // Filter by Order Details
    @FXML
    protected void filterOrderDetails() throws SQLException {
        ordersDetailsTable.getItems().clear();
        int orderNumber = Integer.parseInt(orderNumberOrderDetailsTF.getText());

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select OrderDate, ProductName, Quantity, Price from orders,orderdetails, products where orders.OrderID = '" + orderNumber + "' AND orderdetails.OrderID = orders.OrderID AND orderdetails.ProductID = products.ProductID");

        while (resultSet.next()) {
            orderDetails.add(new OrderDetails(resultSet.getDate(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getDouble(4)));
        }

        orderDateColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails,Date>("orderDate"));
        orderProductColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails,String>("product"));
        orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails,Integer>("quantity"));
        orderUnitPriceColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails,Double>("unitPrice"));
        ordersDetailsTable.setItems(orderDetails);
    }

    // Filter by Customer Country
    @FXML
    protected void filterCustomerByCountry() throws SQLException {
        customersTable.getItems().clear();
        String country = customerInfoTF.getText();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select CustomerName, City from customers where Country = '" + country + "' order by City");

        while (resultSet.next()) {
            customerDetails.add(new CustomerInfo(resultSet.getString(1), resultSet.getString(2)));
        }

        customerNameColumn.setCellValueFactory(new PropertyValueFactory<CustomerInfo,String>("customerName"));
        customerCityColumn.setCellValueFactory(new PropertyValueFactory<CustomerInfo,String>("city"));
        customersTable.setItems(customerDetails);
    }

    // Filter by Employee Birth Year
    @FXML
    protected void filterEmployeeByBirthYear() throws SQLException {
        employeeNameTable.getItems().clear();
        String year = yearTF.getText();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select FirstName, LastName from Northwind.employees where substring(BirthDate,1,4) = '" + year + "' order by LastName");

        while (resultSet.next()) {
            employeeDetails.add(new EmployeeInfo(resultSet.getString(1), resultSet.getString(2)));
        }

        employeeFirstNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeInfo,String>("firstName"));
        employeeLastNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeInfo,String>("lastName"));
        employeeNameTable.setItems(employeeDetails);
    }

    // Closes the application
    @FXML
    protected void exit() throws SQLException {
        connection.close();
        System.exit(0);
    }
}

