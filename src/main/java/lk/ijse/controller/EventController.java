package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Employee;
import lk.ijse.model.Equipment;
import lk.ijse.model.Event;
import lk.ijse.model.tm.EquipmentTm;
import lk.ijse.model.tm.EventTm;
import lk.ijse.model.tm.MenuTm;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.EquipmentRepo;
import lk.ijse.repository.EventRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventController {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colE_Code;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EventTm> tblEvent;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtEventCode;

    @FXML
    private JFXTextField txtEventType;
    private List<Event> eventList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.eventList=getAllEvent();
        setCellValueFactory();
        loadEventTable();
        showSelectedUserDetails();
        loadNextEventId();
    }

    private void loadNextEventId() {
        try {
            String currentId = EventRepo.currentId();
            String nextId = nextId(currentId);

            txtEventCode.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("E");
            int id = Integer.parseInt(split[1], 10);
            return "E" + String.format("%03d", ++id);
        }
        return "E001";
    }

    private void loadEventTable() {
        ObservableList<EventTm> tmList = FXCollections.observableArrayList();

        for (Event event : eventList) {
            EventTm eventTm = new EventTm(
                    event.getE_Code(),
                    event.getDescription(),
                    event.getType()

            );

            tmList.add(eventTm);
        }
        tblEvent.setItems(tmList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
        private void clearFields() {
            txtEventCode.setText("");
            txtDescription.setText("");
            txtEventType.setText("");

        }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String E_Code = txtEventCode.getText();

        boolean isDeleted = EventRepo.delete(E_Code);
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Event deleted!").show();
        }
        initialize();
        clearFields();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String E_Code = txtEventCode.getText();
        String Description = txtDescription.getText();
        String Type = txtEventType.getText();

        Event event3= new Event( E_Code,Description,Type );

        try {
            boolean isSaved = EventRepo.save(event3);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Event saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        clearFields();


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String E_Code = txtEventCode.getText();
        String Description = txtDescription.getText();
        String Type = txtEventType.getText();

        Event event2 = new Event(E_Code, Description, Type);

        boolean isUpdated = EventRepo.update(event2);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Event updated!").show();
        }
        initialize();
        clearFields();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String E_Code = txtEventCode.getText();

        try {
            Event event1 = EventRepo.searchByCode(E_Code);

            if (event != null) {
                txtEventCode.setText(event1.getE_Code());
                txtDescription.setText(event1.getDescription());
                txtEventType.setText(event1.getType());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        clearFields();

    }

    private void setCellValueFactory() {
        colE_Code.setCellValueFactory(new PropertyValueFactory<>("E_Code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
    }

    public List<Event> getAllEvent() throws SQLException {
        List<Event> eventList = null;
        eventList = EventRepo.getAll();
        return eventList;
    }

    private void showSelectedUserDetails() {
        EventTm selectedUser = tblEvent.getSelectionModel().getSelectedItem();
        tblEvent.setOnMouseClicked(event -> showSelectedUserDetails());
        if (selectedUser != null) {
            txtEventCode.setText(selectedUser.getE_Code());
            txtDescription.setText(selectedUser.getDescription());
            txtEventType.setText(selectedUser.getType());


        }
    }

}
