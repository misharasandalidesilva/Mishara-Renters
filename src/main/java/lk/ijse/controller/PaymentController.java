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
import lk.ijse.model.Payment;
import lk.ijse.model.tm.OrdersTm;
import lk.ijse.model.tm.PaymentTm;
import lk.ijse.repository.MenuRepo;
import lk.ijse.repository.OrdersRepo;
import lk.ijse.repository.PaymentRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentController {
    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colO_id;

    @FXML
    private TableColumn<?, ?> colP_id;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<PaymentTm> tblpayment;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPaymentId;
    private List<Payment> PaymentList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.PaymentList = getAllpayment();
        setCellValueFactory();
        loadpaymenttable();
        showSelectedUserDetails();
        loadNextPaymentId();
    }

    private void loadNextPaymentId() {
        try {
            String currentId = PaymentRepo.currentId();
            String nextId = nextId(currentId);

            txtPaymentId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("P");
            int id = Integer.parseInt(split[1], 10);
            return "P" + String.format("%03d", ++id);
        }
        return "P001";
    }

    private void loadpaymenttable () {
        ObservableList<PaymentTm> tmList = FXCollections.observableArrayList();

        for (Payment payment : PaymentList) {
            PaymentTm paymentTm = new PaymentTm(
                    payment.getP_id(),
                    payment.getDate(),
                    payment.getDescription(),
                    payment.getO_id());


            tmList.add(paymentTm);
        }
        tblpayment.setItems(tmList);
        Object selectedItem = tblpayment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String P_id = txtPaymentId.getText();
        String Date = txtDate.getText();
        String Description = txtDescription.getText();
        String O_id = txtOrderId.getText();

        Payment payment = new Payment(P_id, Date, Description, O_id);

        boolean isUpdated = PaymentRepo.update(payment);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Payment updated!").show();
        }
        initialize();
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String P_id = txtPaymentId.getText();

        boolean isDeleted = PaymentRepo.delete(P_id);
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Payment deleted!").show();
        }
        initialize();
        clearFields();

    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String P_id = txtPaymentId.getText();
        String Date = txtDate.getText();
        String Description = txtDescription.getText();
        String O_id = txtOrderId.getText();

        Payment payment = new Payment(P_id, Date, Description, O_id);

        boolean isSaved = PaymentRepo.save(payment);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Payment saved!").show();
        }
        initialize();
        clearFields();

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();

    }

    private void clearFields() {
        txtPaymentId.setText("");
        txtDate.setText("");
        txtDescription.setText("");
        txtOrderId.setText("");


    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String P_id = txtPaymentId.getText();

        Payment payment = PaymentRepo.searchById(P_id);

        if (payment != null) {
            txtPaymentId.setText(payment.getP_id());
            txtDate.setText(payment.getDate());
            txtDescription.setText(payment.getDescription());
            txtOrderId.setText(payment.getO_id());

        }
        initialize();
        clearFields();
    }


    private void setCellValueFactory() {
        colP_id.setCellValueFactory(new PropertyValueFactory<>("P_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colO_id.setCellValueFactory(new PropertyValueFactory<>("O_id"));



    }

    private List<Payment> getAllpayment() throws SQLException {
        List<Payment> PaymentList = null;
        PaymentList = PaymentRepo.getAll();
        return PaymentList;
    }

    private void showSelectedUserDetails() {
        PaymentTm selectedUser = tblpayment.getSelectionModel().getSelectedItem();
        tblpayment.setOnMouseClicked(event -> showSelectedUserDetails());
        if (selectedUser != null) {
            txtPaymentId.setText(selectedUser.getP_id());
            txtDate.setText(selectedUser.getDate());
            txtDescription.setText(selectedUser.getDescription());
            txtOrderId.setText(selectedUser.getO_id());

        }
    }

}
