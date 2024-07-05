package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Payment;
import lk.ijse.model.Vehicle;
import lk.ijse.model.tm.PaymentTm;
import lk.ijse.model.tm.VehicleTm;
import lk.ijse.repository.PaymentRepo;
import lk.ijse.repository.VehicleRepo;

import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class VehicleController {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colV_Name;

    @FXML
    private TableColumn<?, ?> colV_id;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<VehicleTm> tblVehicle;

    @FXML
    private TextField txtVehicleId;

    @FXML
    private TextField txtVehicleName;
    private List<Vehicle> vehicleList;

    @FXML
    void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();

    }

    private void clearFields() {
        txtVehicleId.setText("");
        txtVehicleName.setText("");


    }

    public void initialize() throws SQLException {
        this.vehicleList = getAllVehicle();
        setCellValueFactory();
        loadVehicletable();
        showSelectedUserDetails();
        loadNextVehicleId();

    }

    private void loadNextVehicleId() {
        try {
            String currentId = VehicleRepo.currentId();
            String nextId = nextId(currentId);

            txtVehicleId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("V");
            int id = Integer.parseInt(split[1], 10);
            return "V" + String.format("%03d", ++id);
        }
        return "V001";
    }

    private void loadVehicletable() {
        ObservableList<VehicleTm> tmList = FXCollections.observableArrayList();

        for (Vehicle vehicle : vehicleList) {

            VehicleTm vehicleTm = new VehicleTm(
                    vehicle.getV_id(),
                    vehicle.getV_name());


            tmList.add(vehicleTm);
        }
        tblVehicle.setItems(tmList);
        Object selectedItem = tblVehicle.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String V_id = txtVehicleId.getText();

        boolean isDeleted = VehicleRepo.delete(V_id);
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Vehicle deleted!").show();
        }
        initialize();
        clearFields();
    }

    @FXML
    void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String V_id = txtVehicleId.getText();
        String V_name = txtVehicleName.getText();

        Vehicle vehicle = new Vehicle(V_id, V_name);

        boolean isSaved = VehicleRepo.save(vehicle);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Vehicle saved!").show();
        }
        initialize();
        clearFields();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String V_id = txtVehicleId.getText();
        String V_name = txtVehicleName.getText();

        Vehicle vehicle = new Vehicle(V_id, V_name);

        boolean isUpdated = VehicleRepo.update(vehicle);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Vehicle updated!").show();
        }
        initialize();
        clearFields();

    }

    @FXML
    void txtSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String V_id = txtVehicleId.getText();

        Vehicle vehicle = VehicleRepo.searchById(V_id);

        if (vehicle != null) {
            txtVehicleId.setText(vehicle.getV_id());
            txtVehicleName.setText(vehicle.getV_name());

        }
        initialize();
        clearFields();

    }


    private void setCellValueFactory() {
        colV_id.setCellValueFactory(new PropertyValueFactory<>("V_id"));
        colV_Name.setCellValueFactory(new PropertyValueFactory<>("V_name"));

    }

    private List<Vehicle> getAllVehicle() throws SQLException {
        List<Vehicle> vehicleList = null;
        vehicleList = VehicleRepo.getAll();
        return vehicleList;
    }

    private void showSelectedUserDetails() {
        VehicleTm selectedUser = tblVehicle.getSelectionModel().getSelectedItem();
        tblVehicle.setOnMouseClicked(event -> showSelectedUserDetails());
        if (selectedUser != null) {
            txtVehicleId.setText(selectedUser.getV_id());
            txtVehicleName.setText(selectedUser.getV_name());

        }

    }

}
