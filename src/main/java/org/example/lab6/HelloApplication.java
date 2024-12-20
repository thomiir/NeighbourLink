package org.example.lab6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.lab6.controller.*;
import org.example.lab6.domain.*;
import org.example.lab6.domain.validators.*;
import org.example.lab6.repository.*;
import org.example.lab6.service.*;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Repository<Long, User> userRepository = new UserRepository(new UserValidator());
        Repository<Tuple<Long, Long>, Friendship> friendshipRepository = new FriendshipRepository(new FriendshipValidator());
        Repository<Tuple<Long, Long>, Request> requestRepository = new RequestRepository(new RequestValidator());
        Repository<Long, Message> messageRepository = new MessageRepository(new MessageValidator());
        PagingRepository<Long, Task> taskRepository = new TaskRepository(new TaskValidator());
        UserService userService = new UserService(userRepository, friendshipRepository);
        FriendshipService friendshipService = new FriendshipService(friendshipRepository, userRepository);
        RequestService requestService = new RequestService(requestRepository, userRepository, friendshipRepository);
        MessageService messageService = new MessageService(messageRepository, userRepository);
        TaskService taskService = new TaskService(taskRepository, userRepository);
        SceneService sceneService = new SceneService(stage, userService, requestService, friendshipService, messageService, taskService);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/login-page.fxml"));
        LoginPageController loginPageController = new LoginPageController();
        loginPageController.setUserService(userService);
        loginPageController.setFriendshipService(friendshipService);
        loginPageController.setRequestService(requestService);
        loginPageController.setSceneService(sceneService);
        loginPageController.setMessageService(messageService);
        loginPageController.setTaskService(taskService);
        fxmlLoader.setController(loginPageController);
        try {
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
}
