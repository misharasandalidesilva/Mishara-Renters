package lk.ijse.controller;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class DashboardController {

    public AnchorPane nodePane;
    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnEquipment;

    @FXML
    private Button btnEvent;

    @FXML
    private Button btnFood;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnOrder;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnVehicle;

    @FXML
    private AnchorPane root;


    @FXML
    private AnchorPane Load;

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/customer.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/employee.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();

    }

    @FXML
    void btnEquipmentOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/equipment.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();


    }

    @FXML
    void btnEventOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/event.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();


    }

    @FXML
    void btnFoodOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/food.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();


    }

    @FXML
    void btnMenuOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/menu.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();


    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/orders.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();


    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/payment.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();


    }

    @FXML
    void btnVehicleOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/vehicle.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    public void initialize() {
    }


    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent loginpage = FXMLLoader.load(getClass().getResource("/view/login_page.fxml"));
        Scene scene = new Scene(loginpage);
        Stage stage = (Stage) Load.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Page");

    }

}
