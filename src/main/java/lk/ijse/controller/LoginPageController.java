package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginPageController {
    public AnchorPane rootNode;
    public TextField txtUserName;
    public TextField txtPassword;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        final String uname = "a";
        final String pass = "a";

        String username = txtUserName.getText();
        String password = txtPassword.getText();

        if(username.equals(uname)){
            if (password.equals(pass)){
                navigateToDashboard((Stage) rootNode.getScene().getWindow());
            }
            else{
                new Alert(Alert.AlertType.ERROR,"Password Is Wrong !!!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"User Name Is Wrong !!!").show();
        }
    }

    private void navigateToDashboard(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        AnchorPane rootNode = loader.load();
        DashboardController controller = loader.getController();
        controller.initialize(); // Assuming you want to initialize the dashboard controller

        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard");

    }
    }

