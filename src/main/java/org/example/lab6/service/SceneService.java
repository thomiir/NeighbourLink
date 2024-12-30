package org.example.lab6.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.lab6.HelloApplication;
import org.example.lab6.controller.Controller;
import org.example.lab6.domain.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneService {
    private final Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();
    private final UserService userService;
    private final RequestService requestService;
    private final FriendshipService friendshipService;
    private final MessageService messageService;
    private final TaskService taskService;
    private User currentUser;

    public SceneService(Stage primaryStage, UserService userService, RequestService requestService, FriendshipService friendshipService, MessageService messageService, TaskService taskService) {
        this.primaryStage = primaryStage;
        this.userService = userService;
        this.requestService = requestService;
        this.friendshipService = friendshipService;
        this.messageService = messageService;
        this.taskService = taskService;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addScene(String name, String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        try {
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setUserService(userService);
            controller.setSceneService(this);
            controller.setRequestService(requestService);
            controller.setFriendshipService(friendshipService);
            controller.setMessageService(messageService);
            controller.setTaskService(taskService);
            controller.init();
            scenes.put(name, new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchScene(String name) {
        Scene scene = scenes.get(name);
        if (scene != null)
        {
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        else
            System.out.println("Scene not found: " + name);
    }
}
