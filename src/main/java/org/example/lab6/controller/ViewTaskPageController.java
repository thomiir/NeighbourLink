package org.example.lab6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.lab6.domain.Task;


public class ViewTaskPageController extends AbstractController {

    @FXML
    private Label taskTitleLabel;

    @FXML
    private Label taskDescriptionLabel;

    @FXML
    private Label taskPosterLabel;

    @FXML
    private Label taskSolverLabel;

    @FXML
    private Label taskDatePostedLabel;

    @FXML
    private Label taskLengthLabel;

    private Task task;

    /**
     * Sets the task and updates the UI.
     * @param task The task to display.
     */
    public void setTask(Task task) {
        this.task = task;
    }


    @FXML
    private void handleBackButtonClicked() {
        Stage stage = (Stage) taskPosterLabel.getScene().getWindow();
        stage.close();
    }
    //@Override
    @FXML
    public void initialize() {
        taskTitleLabel.setText(task.getTitle());
        taskDescriptionLabel.setText(task.getDescription());
        taskPosterLabel.setText(getUserService().findUserById(task.getPosterId()).get().getUsername());
        taskSolverLabel.setText("UNSOLVED");
        taskDatePostedLabel.setText(task.getDatePosted().toString());
        taskLengthLabel.setText(task.getLength().toString());
    }

    @Override
    public void init() {

    }
}
