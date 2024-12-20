package org.example.lab6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.lab6.HelloApplication;
import org.example.lab6.domain.MessageAlert;

import java.io.IOException;

public class ProfilePageController extends AbstractController {

    @FXML
    private Button buttonDeleteAccount;

    @Override
    public void init() {

    }

    @FXML
    private void handleButtonDeleteAccountClicked() {
        getUserService().removeEntity(getSceneService().getCurrentUser().getId());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/login-page.fxml"));
        LoginPageController loginPageController = new LoginPageController();
        loginPageController.setUserService(getUserService());
        loginPageController.setFriendshipService(getFriendshipService());
        loginPageController.setRequestService(getRequestService());
        loginPageController.setSceneService(getSceneService());
        loginPageController.setMessageService(getMessageService());
        loginPageController.setTaskService(getTaskService());
        fxmlLoader.setController(loginPageController);
        try {
            buttonDeleteAccount.getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML", e);
        }
        MessageAlert.showInfo("Successfully deleted your account!");
    }
}
