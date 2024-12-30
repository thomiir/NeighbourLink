package org.example.lab6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.lab6.HelloApplication;
import org.example.lab6.domain.MessageAlert;
import java.io.IOException;

public class ProfilePageController extends AbstractController {

    @FXML
    private Button buttonDeleteAccount;

    @FXML
    private Label labelUsername;

    @FXML
    private Label labelFullName;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelZipCode;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> tasksBarChart;


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

    @FXML
    private void handleButtonLogoutClicked() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/login-page.fxml"));
        LoginPageController loginPageController = new LoginPageController();
        loginPageController.setUserService(getUserService());
        loginPageController.setFriendshipService(getFriendshipService());
        loginPageController.setRequestService(getRequestService());
        loginPageController.setSceneService(getSceneService());
        loginPageController.setMessageService(getMessageService());
        loginPageController.setTaskService(getTaskService());
        fxmlLoader.setController(loginPageController);
        getSceneService().setCurrentUser(null);
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
    }

    @Override
    public void init() {
        labelUsername.setText(getSceneService().getCurrentUser().getUsername());
        labelFullName.setText(getSceneService().getCurrentUser().getFullName());
        labelEmail.setText(getSceneService().getCurrentUser().getEmail());
        labelZipCode.setText(getSceneService().getCurrentUser().getZipCode());
        Integer tasksSolved = getTaskService().getTasksSolvedByUser(getSceneService().getCurrentUser());
        Integer tasksPosted = getTaskService().getTasksPostedByUser(getSceneService().getCurrentUser());
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Tasks Posted", tasksPosted));
        series.getData().add(new XYChart.Data<>("Tasks Solved", tasksSolved));
        tasksBarChart.getData().add(series);
    }
}
