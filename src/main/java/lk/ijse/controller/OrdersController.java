package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Menu;
import lk.ijse.model.Orders;
import lk.ijse.model.tm.MenuTm;
import lk.ijse.model.tm.OrdersTm;
import lk.ijse.model.tm.PaymentTm;
import lk.ijse.repository.FoodRepo;
import lk.ijse.repository.MenuRepo;
import lk.ijse.repository.OrdersRepo;

import java.sql.SQLException;
import java.util.List;

import static java.awt.SystemColor.menu;

public class OrdersController {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colC_id;

    @FXML
    private TableColumn<?, ?> colE_code;

    @FXML
    private TableColumn<?, ?> colO_id;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<OrdersTm> tblOrder;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtEventCode;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtOrderQty;

    @FXML
    private TextField txtStatus;
    private List<Orders> orderList;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtOrderId.setText("");
        txtOrderQty.setText("");
        txtStatus.setText("");
        txtCustomerId.setText("");
        txtEventCode.setText("");

    }

    public void initialize() throws SQLException {
        this.orderList = getAllOrders();
        setCellValueFactory();
        loadOrdertable();
        showSelectedUserDetails();
        loadNextOrdersId();
    }
    private void loadNextOrdersId() {
        try {
            String currentId = OrdersRepo.currentId();
            String nextId = nextId(currentId);

            txtOrderId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
            int id = Integer.parseInt(split[1], 10);
            return "O" + String.format("%03d", ++id);
        }
        return "O001";
    }

    private void loadOrdertable () {
        ObservableList<OrdersTm> tmList = FXCollections.observableArrayList();

        for (Orders orders : orderList) {
            OrdersTm ordersTm = new OrdersTm(
                    orders.getO_id(),
                    orders.getQty(),
                    orders.getStatus(),
                    orders.getC_id(),
                    orders.getE_Code());

            tmList.add(ordersTm);
        }
        tblOrder.setItems(tmList);
        Object selectedItem = tblOrder.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String O_id = txtOrderId.getText();

        boolean isDeleted = OrdersRepo.delete(O_id);
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Order deleted!").show();
        }
        initialize();
        clearFields();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String O_id = txtOrderId.getText();
        String Qty = txtOrderQty.getText();
        String Status = txtStatus.getText();
        String C_id = txtCustomerId.getText();
        String E_Code = txtEventCode.getText();

        Orders orders = new Orders(O_id, Qty, Status, C_id, E_Code);

        boolean isSaved = OrdersRepo.save(orders);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Order saved!").show();
        }
        initialize();
        clearFields();


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String O_id = txtOrderId.getText();
        String Qty = txtOrderQty.getText();
        String Status = txtStatus.getText();
        String C_id = txtCustomerId.getText();
        String E_Code = txtEventCode.getText();

        Orders orders = new Orders(O_id, Qty, Status, C_id, E_Code);

        boolean isUpdated = OrdersRepo.update(orders);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Order updated!").show();
        }
        initialize();
        clearFields();
    }

    @FXML
    void colCustomerIDOnAction(ActionEvent event) {

    }

    @FXML
    void colEventCodeOnAction(ActionEvent event) {

    }

    @FXML
    void colOrderIDOnAction(ActionEvent event) {

    }

    @FXML
    void colOrderQtyOnAction(ActionEvent event) {

    }

    @FXML
    void colStatusOnAction(ActionEvent event) {

    }

    @FXML
    void tblOrderOnAction(ActionEvent event) {

    }

    @FXML
    void txtCustomerIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtEventCodeOnAction(ActionEvent event) {

    }

    @FXML
    void txtOrderIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtOrderQtyOnAction(ActionEvent event) {

    }

    @FXML
    void txtStatusOnAction(ActionEvent event) {

    }

    public void tblOrdersOnAction(SortEvent<TableView> tableViewSortEvent) {
    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String O_id = txtOrderId.getText();

        Orders orders = OrdersRepo.searchById(O_id);

        if (orders != null) {
            txtOrderId.setText(orders.getO_id());
            txtOrderQty.setText(orders.getQty());
            txtStatus.setText(orders.getStatus());
            txtCustomerId.setText(orders.getC_id());
            txtEventCode.setText(orders.getE_Code());

        }
        initialize();
        clearFields();

    }


    private void setCellValueFactory() {
        colO_id.setCellValueFactory(new PropertyValueFactory<>("O_id"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colC_id.setCellValueFactory(new PropertyValueFactory<>("C_id"));
        colE_code.setCellValueFactory(new PropertyValueFactory<>("E_Code"));


    }

    private List<Orders> getAllOrders() throws SQLException {
        List<Orders> ordersList = null;
        ordersList = OrdersRepo.getAll();
        return ordersList;
    }

    private void showSelectedUserDetails() {
        OrdersTm selectedUser = tblOrder.getSelectionModel().getSelectedItem();
        tblOrder.setOnMouseClicked(event -> showSelectedUserDetails());
        if (selectedUser != null) {
            txtOrderId.setText(selectedUser.getO_id());
            txtOrderQty.setText(selectedUser.getQty());
            txtStatus.setText(selectedUser.getStatus());
            txtCustomerId.setText(selectedUser.getC_id());
            txtEventCode.setText(selectedUser.getE_Code());

        }
    }
}
