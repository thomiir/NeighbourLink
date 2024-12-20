package org.example.lab6.controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.lab6.HelloApplication;
import org.example.lab6.domain.Task;
import org.example.lab6.domain.constants.SortingCriteria;
import org.example.lab6.util.paging.Page;
import org.example.lab6.util.paging.Pageable;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class MainPageController extends AbstractController {

    private SortingCriteria sortingCriteria;
    private TableColumn.SortType sortingOrder;
    private Integer currentPageNumber;
    private Integer maximumPageNumber;

    @FXML
    private TableView<Task> tableViewTasks;

    @FXML
    private TableColumn<Task, String> columnType;

    @FXML
    private TableColumn<Task, String> columnTitle;

    @FXML
    private TableColumn<Task, String> columnPoster;

    @FXML
    private TableColumn<Task, String> columnTimePosted;

    @FXML
    private TableColumn<Task, String> columnDuration;

    @FXML
    private TableColumn<Task, Integer> columnIndex;

    @FXML
    private Label labelPageNumber;

    @FXML
    private Button buttonNext;

    @FXML
    private Button buttonPrevious;

    @Override
    public void init() {
        Platform.runLater(() -> {
            currentPageNumber = 1;
            maximumPageNumber = ((int) Math.ceil((double) getTaskService().count() / 10));
            sortingOrder = TableColumn.SortType.ASCENDING;
            sortingCriteria = SortingCriteria.TITLE;
            ImageView img = new ImageView(new Image("org/example/lab6/images/add.png"));
            img.setFitHeight(15);
            img.setFitWidth(15);
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.getChildren().add(img);
            hbox.setOnMouseClicked(_ -> handleAddTask());
            columnIndex.setGraphic(hbox);
            img.setOnMouseClicked(_ -> handleAddTask());
            columnIndex.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(tableViewTasks.getItems().indexOf(cellData.getValue()) + 1 + (currentPageNumber - 1) * 10));
            columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
            columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            columnPoster.setCellValueFactory(cellData -> new SimpleStringProperty(getUserService().findUserById(cellData.getValue().getPosterId()).get().getUsername()));
            columnTimePosted.setCellValueFactory(new PropertyValueFactory<>("datePosted"));
            columnDuration.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLength().toString()));
            addListeners();
            reloadTableContent();
        });
        tableViewTasks.setRowFactory(_ -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Task clickedTask = row.getItem();
                    ViewTaskPageController vtpc = new ViewTaskPageController();
                    vtpc.setTask(clickedTask);
                    vtpc.setSceneService(getSceneService());
                    vtpc.setTaskService(getTaskService());
                    vtpc.setUserService(getUserService());
                    vtpc.init();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/view-task-page.fxml"));
                    fxmlLoader.setController(vtpc);
                    try {
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
            });
            return row;
        });
    }

    private void addListeners() {
        tableViewTasks.getColumns().forEach(column -> {
            if (column != columnIndex)
                column.sortTypeProperty().addListener((_, _, newValue) -> {
                sortingOrder = newValue;
                switch (column.getText()) {
                    case "TYPE":
                        sortingCriteria = SortingCriteria.TYPE;
                        break;
                    case "TITLE":
                        sortingCriteria = SortingCriteria.TITLE;
                        break;
                    case "DATE":
                        sortingCriteria = SortingCriteria.DATE;
                        break;
                    case "DURATION":
                        sortingCriteria = SortingCriteria.DURATION;
                        break;
                }
            });
        });
    }

    private void reloadTableContent() {
        Platform.runLater(() -> {
            Pageable pageable = new Pageable(10, currentPageNumber);
            Page<Task> currentPage = new Page<>(getTaskService().findAllOnPage(pageable).getElementsOnPage());
            ObservableList<Task> tasks = FXCollections.observableList(
                    StreamSupport.stream(currentPage.getElementsOnPage().spliterator(), false)
                            .toList());
            tableViewTasks.getItems().clear();
            tableViewTasks.getItems().addAll(tasks);
            labelPageNumber.setText("Page " + currentPageNumber + " of " + maximumPageNumber);
            buttonPrevious.setDisable(currentPageNumber == 1);
            buttonNext.setDisable(Objects.equals(currentPageNumber, maximumPageNumber));
        });
    }

    @FXML
    private void handleButtonPreviousClicked() {
        currentPageNumber--;
        reloadTableContent();
    }

    @FXML
    private void handleButtonNextClicked() {
        currentPageNumber++;
        reloadTableContent();
    }

    private void handleAddTask() {
        getSceneService().switchScene("add-task");
    }


}
