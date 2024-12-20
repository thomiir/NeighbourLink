package org.example.lab6.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.lab6.domain.MessageAlert;
import org.example.lab6.domain.User;
import org.example.lab6.service.ServiceException;

import java.sql.SQLException;

public class AddFriendPageController extends AbstractController {
    @FXML
    private TableView<User> tableViewUsers;

    @FXML
    private TableColumn<User, String> tableColumnUsername;

    @FXML
    private TableColumn<User, String> tableColumnFullName;

    @Override
    public void init() {
        getRequestService().addObserver(() -> {});
        tableViewUsers.getItems().clear();
        tableColumnFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        getUserService().getUsers().forEach(user ->
                tableViewUsers.getItems().add(user));
    }

    @FXML
    private void handleButtonRequestClicked()  {
        try {
            var selectedUsers = tableViewUsers.getSelectionModel().getSelectedItems();
            getRequestService().addRequest(getSceneService().getCurrentUser(), selectedUsers.getFirst());
            getRequestService().notifyObservers();
            getSceneService().switchScene("friends");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505"))
                MessageAlert.showError("You've already sent a request to that user!");
        }
         catch (ServiceException se) {
            MessageAlert.showError(se.getMessage());
         }
    }

    @FXML
    private void handleBackButtonClicked() {
        getSceneService().switchScene("friends");
    }

}