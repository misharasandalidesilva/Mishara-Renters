package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Food;
import lk.ijse.model.Menu;
import lk.ijse.model.tm.FoodTm;
import lk.ijse.model.tm.MenuTm;
import lk.ijse.model.tm.OrdersTm;
import lk.ijse.repository.EventRepo;
import lk.ijse.repository.FoodRepo;
import lk.ijse.repository.MenuRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuController {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colF_id;

    @FXML
    private TableColumn<?, ?> colM_code;

    @FXML
    private TableColumn<?, ?> colM_name;

    @FXML
    private TableColumn<?, ?> colO_id;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<MenuTm> tblMenu;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtFoodId;

    @FXML
    private JFXTextField txtMenuCode;

    @FXML
    private JFXTextField txtMenuName;

    @FXML
    private JFXTextField txtOrderId;

    private List<Menu> MenuList = new ArrayList<>();

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtMenuCode.setText("");
        txtMenuName.setText("");
        txtDescription.setText("");
        txtFoodId.setText("");
        txtOrderId.setText("");

    }

    public void initialize() {
        this.MenuList = getAllMenu();
        setCellValueFactory();
        loadMenutable();
        showSelectedUserDetails();
        new JFXTextField();
    }

    private void loadNextMenuCode() {
        try {
            String currentId = MenuRepo.currentId();
            String nextId = nextId(currentId);

            txtMenuCode.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("M");
            int id = Integer.parseInt(split[1], 10);
            return "M" + String.format("%03d", ++id);
        }
        return "M001";
    }

    private void loadMenutable() {
        ObservableList<MenuTm> tmList = FXCollections.observableArrayList();

        for (Menu menu : MenuList) {
            MenuTm menuTm = new MenuTm(
                    menu.getM_Code(),
                    menu.getM_name(),
                    menu.getDescription(),
                    menu.getF_id(),
                    menu.getO_id());

            tmList.add(menuTm);
        }
        tblMenu.setItems(tmList);
        Object selectedItem = tblMenu.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String M_Code = txtMenuCode.getText();

        boolean isDeleted = MenuRepo.delete(M_Code);
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Menu deleted!").show();
        }
        initialize();
        clearFields();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String M_Code = txtMenuCode.getText();
        String M_name = txtMenuName.getText();
        String Description = txtDescription.getText();
        String F_id = txtFoodId.getText();
        String O_id = txtOrderId.getText();

        Menu menu = new Menu(M_Code, M_name, Description, F_id, O_id);

        boolean isSaved = MenuRepo.save(menu);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Menu saved!").show();
        }
        initialize();
        clearFields();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String M_Code = txtMenuCode.getText();
        String M_name = txtMenuName.getText();
        String Description = txtDescription.getText();
        String F_id = txtFoodId.getText();
        String O_id = txtOrderId.getText();

        Menu menu = new Menu(M_Code, M_name, Description, F_id, O_id);

        boolean isUpdated = MenuRepo.update(menu);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Menu updated!").show();
        }
        initialize();
        clearFields();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String M_Code = txtMenuCode.getText();

        try {
            Menu menu = MenuRepo.searchByCode(M_Code);

            if (menu != null) {
                txtMenuCode.setText(menu.getM_Code());
                txtMenuName.setText(menu.getM_name());
                txtDescription.setText(menu.getDescription());
                txtFoodId.setText(menu.getF_id());
                txtOrderId.setText(menu.getO_id());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        clearFields();


    }


    private void setCellValueFactory() {
        colM_code.setCellValueFactory(new PropertyValueFactory<>("M_Code"));
        colM_name.setCellValueFactory(new PropertyValueFactory<>("M_name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colF_id.setCellValueFactory(new PropertyValueFactory<>("F_id"));
        colO_id.setCellValueFactory(new PropertyValueFactory<>("O_id"));


    }

    private List<Menu> getAllMenu() {
        List<Menu> MenuList = null;
        try {
            MenuList = MenuRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return MenuList;
    }

    private void showSelectedUserDetails() {
        MenuTm selectedUser = tblMenu.getSelectionModel().getSelectedItem();
        tblMenu.setOnMouseClicked(event -> showSelectedUserDetails());
        if (selectedUser != null) {
            txtMenuCode.setText(selectedUser.getM_Code());
            txtMenuName.setText(selectedUser.getM_name());
            txtDescription.setText(selectedUser.getDescription());
            txtFoodId.setText(selectedUser.getF_id());
            txtOrderId.setText(selectedUser.getO_id());

        }
    }
}