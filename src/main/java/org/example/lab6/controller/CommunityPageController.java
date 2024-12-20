package org.example.lab6.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.lab6.domain.User;
import java.util.stream.StreamSupport;

public class CommunityPageController extends AbstractController {

    @FXML
    private Label labelZipCode;

    @FXML
    private TableView<User> tableViewUsers;

    @FXML
    private TableColumn<User, Integer> columnIndex;

    @FXML
    private TableColumn<User, String> columnUsername;

    @FXML
    private TableColumn<User, String> columnFullName;

    @FXML
    private TableColumn<User, String> columnAddress;

    @FXML
    private TableColumn<User, Integer> columnTasksSolved;

    @Override
    public void init() {
        labelZipCode.setText("Your community: " + getSceneService().getCurrentUser().getZipCode());
        Tooltip tooltip = new Tooltip("[list of addresses from the zipcode]");
        Tooltip.install(labelZipCode, tooltip);
        columnIndex.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(tableViewUsers.getItems().indexOf(cellData.getValue())));
        columnUsername.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUsername()));
        columnFullName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFullName()));
        columnAddress.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAddress()));
        columnTasksSolved.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(getTaskService().getTasksSolvedByUser(cellData.getValue())));
        tableViewUsers.setItems(FXCollections.observableList(StreamSupport.stream(getUserService().getUsers().spliterator(), false).toList()));
    }


}
