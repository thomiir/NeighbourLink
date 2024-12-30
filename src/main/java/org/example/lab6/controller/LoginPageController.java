package org.example.lab6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.lab6.domain.MessageAlert;
import org.example.lab6.domain.User;

public class LoginPageController extends AbstractController {
    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField passwordFieldPassword;

    @FXML
    public void handleButtonLoginClicked() {
        String username = textFieldUsername.getText();
        String password = passwordFieldPassword.getText();
        try {
            User currentUser = getUserService().userLogin(username, password);
            getSceneService().setCurrentUser(currentUser);
            getSceneService().addScene("tasks", "views/main-page.fxml");
            getSceneService().addScene("friends", "views/friends-page.fxml");
            getSceneService().addScene("profile", "views/profile-page.fxml");
            getSceneService().addScene("add-friend", "views/add-friend-page.fxml");
            getSceneService().addScene("chat", "views/chat-page.fxml");
            getSceneService().addScene("community", "views/community-page.fxml");
            getSceneService().addScene("add-task", "views/add-task-page.fxml");
            getSceneService().switchScene("tasks");
            handleFriendRequestAlert();
        }
        catch (Exception e) {
            MessageAlert.showError(e.getMessage());
        }
    }

    @FXML
    public void handleButtonRegisterClicked() {
        getSceneService().addScene("register", "views/register-page.fxml");
        getSceneService().switchScene("register");
    }

    private void handleFriendRequestAlert() {
        if (!getRequestService().getReceivedRequests(getSceneService().getCurrentUser()).isEmpty())
            MessageAlert.showInfo("You have friend requests pending!");
    }

    @Override
    public void init() {
        getSceneService().addScene("login", "views/login-page.fxml");
    }
}
