package org.example.lab6.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab6.HelloApplication;
import org.example.lab6.domain.MessageAlert;
import org.example.lab6.domain.User;

import java.io.IOException;


public class RegisterPageController extends AbstractController {
    @FXML
    private TextField textFieldFullName;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField passwordFieldPassword;

    @FXML
    private TextField textFieldZipCode;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private void handleButtonCreateAccountClicked() {
        String fullName = textFieldFullName.getText();
        String email = textFieldEmail.getText();
        String username = textFieldUsername.getText();
        String password = passwordFieldPassword.getText();
        String zipCode = textFieldZipCode.getText();
        String address = textFieldAddress.getText();
        User user = new User(fullName, email, username, password, address, zipCode);
        getUserService().addUser(user);
        MessageAlert.showInfo("User created successfully!");
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
            textFieldEmail.getScene().getWindow().hide();
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
    }

    @Override
    public void init() {

    }
}
