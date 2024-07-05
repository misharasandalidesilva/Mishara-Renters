package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Equipment;
import lk.ijse.model.tm.EquipmentTm;
import lk.ijse.model.tm.MenuTm;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.EquipmentRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EquipmentController {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colEq_id;

    @FXML
    private TableColumn<?, ?> colEq_name;

    @FXML
    private TableColumn<?, ?> colEq_qty;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EquipmentTm> tblEquipment;

    @FXML
    private JFXTextField txtEquipmentId;

    @FXML
    private JFXTextField txtEquipmentName;

    @FXML
    private JFXTextField txtEquipmentQty;



    private List<lk.ijse.model.Equipment> equipmentList = new ArrayList<>();

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

        private void clearFields() {
            txtEquipmentId.setText("");
            txtEquipmentName.setText("");
            txtEquipmentQty.setText("");


    }
    public void initialize() {
        this.equipmentList=getAllEquipment();
        setCellValueFactory();
        loadEquipmentTable();
        showSelectedUserDetails();
        loadNextEquipmentId();
    }

    private void loadNextEquipmentId() {
        try {
            String currentId = EquipmentRepo.currentId();
            String nextId = nextId(currentId);

            txtEquipmentId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("Eq");
            int id = Integer.parseInt(split[1], 10);
            return "Eq" + String.format("%03d", ++id);
        }
        return "Eq001";

    }

    private void loadEquipmentTable() {
        ObservableList<EquipmentTm> tmList = FXCollections.observableArrayList();

        for (Equipment equipment : equipmentList) {
            EquipmentTm equipmentTm = new EquipmentTm(
                    equipment.getEq_id(),
                    equipment.getEq_name(),
                    equipment.getEq_qty()

            );

            tmList.add(equipmentTm);
        }
        tblEquipment.setItems(tmList);
        Object selectedItem = tblEquipment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colEq_id.setCellValueFactory(new PropertyValueFactory<>("Eq_id"));
        colEq_name.setCellValueFactory(new PropertyValueFactory<>("Eq_name"));
        colEq_qty.setCellValueFactory(new PropertyValueFactory<>("Eq_qty"));

    }

    private List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = null;
        try {
            equipmentList = EquipmentRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipmentList;
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String Eq_id = txtEquipmentId.getText();

        try {
            boolean isDeleted = EquipmentRepo.delete(Eq_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Equipment deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        clearFields();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String Eq_id = txtEquipmentId.getText();
        String Eq_name = txtEquipmentName.getText();
        String Eq_qty = txtEquipmentQty.getText();

        Equipment equipment = new Equipment(Eq_id, Eq_name, Eq_qty);

        try {
            boolean isSaved = EquipmentRepo.save(equipment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Equipment saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        clearFields();

    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        String Eq_id = txtEquipmentId.getText();
        String Eq_name= txtEquipmentName.getText();
        String Eq_qty = txtEquipmentQty.getText();

        Equipment equipment = new Equipment(Eq_id, Eq_name, Eq_qty);

        try {
            boolean isUpdated = EquipmentRepo.update(equipment);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Equipment updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        clearFields();

    }


    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtEquipmentId.getText();

        try {
            Equipment equipment = EquipmentRepo.searchById(id);

            if (equipment != null) {
                txtEquipmentId.setText(equipment.getEq_id());
                txtEquipmentName.setText(equipment.getEq_name());
                txtEquipmentQty.setText(equipment.getEq_qty());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        clearFields();
    }

    private void showSelectedUserDetails() {
        EquipmentTm selectedUser = tblEquipment.getSelectionModel().getSelectedItem();
        tblEquipment.setOnMouseClicked(event -> showSelectedUserDetails());
        if (selectedUser != null) {
            txtEquipmentId.setText(selectedUser.getEq_id());
            txtEquipmentName.setText(selectedUser.getEq_name());
            txtEquipmentQty.setText(selectedUser.getEq_qty());

        }
    }
}
