package org.example.lab6.controller;

import javafx.fxml.FXML;
import org.example.lab6.service.*;

public abstract class AbstractController implements Controller {
    private UserService userService;
    private SceneService sceneService;
    private RequestService requestService;
    private FriendshipService friendshipService;
    private MessageService messageService;
    private TaskService taskService;

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public SceneService getSceneService() {
        return sceneService;
    }

    public UserService getUserService() {
        return userService;
    }

    public FriendshipService getFriendshipService() {
        return friendshipService;
    }

    public RequestService getRequestService() {
        return requestService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setFriendshipService(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public abstract void init();

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void handleButtonProfileClicked() {
        sceneService.switchScene("profile");
    }

    @FXML
    public void handleButtonTasksClicked() {
        sceneService.switchScene("tasks");
    }

    @FXML
    public void handleButtonFriendsClicked() {
        sceneService.switchScene("friends");
    }

    @FXML
    public void handleButtonChatClicked() {
        sceneService.switchScene("chat");
    }

    @FXML
    public void handleButtonCommunityClicked() {
        sceneService.switchScene("community");
    }

    @FXML
    public void handleButtonMyTasksClicked() {sceneService.switchScene("my-tasks");}
}
