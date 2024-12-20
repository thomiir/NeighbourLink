package org.example.lab6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.lab6.domain.Message;
import org.example.lab6.domain.MessageAlert;
import org.example.lab6.domain.User;

import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

public class ChatPageController extends AbstractController {

    @FXML
    private ListView<User> conversationList;

    @FXML
    private ListView<Message> chatHistory;

    @FXML
    private TextField messageInputField, replyToField;

    @FXML
    private Button buttonSendMessage;

    private final ObservableList<Message> currentMessages = FXCollections.observableArrayList();

    private Message replyingToMessage;

    @Override
    public void init() {
        configureMessageInputFields();
        configureConversationList();
        configureChatHistory();
    }

    private void configureMessageInputFields() {
        messageInputField.setVisible(false);
        replyToField.setEditable(false);
        replyToField.setVisible(false);
        buttonSendMessage.setVisible(false);
    }

    private void configureConversationList() {
        ObservableList<User> users = FXCollections.observableList(
                getUserService().getAllUsersBesides(getSceneService().getCurrentUser())
        );
        conversationList.setItems(users);
        conversationList.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getFullName());
            }
        });

        conversationList.getSelectionModel().selectedItemProperty().addListener((_, _, newUser) -> {
            if (newUser != null) {
                loadConversation(newUser);
                messageInputField.setVisible(true);
                buttonSendMessage.setVisible(true);
            }
        });
    }

    private void configureChatHistory() {
        chatHistory.setItems(currentMessages);
        chatHistory.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(Message message, boolean empty) {
                super.updateItem(message, empty);
                setGraphic(empty || message == null ? null : createMessageBubble(message));
            }
        });
    }

    private HBox createMessageBubble(Message message) {
        // Create the message bubble
        TextFlow messageBubble = new TextFlow(new Text(message.getText()));
        messageBubble.setPadding(new Insets(10));
        messageBubble.setStyle(getMessageBubbleStyle(message));

        // Create the container for the message
        VBox messageBox = new VBox();
        if (message.getReplyMessage() != -1) {
            // Display the "Replied to" label if the message is a reply
            Text repliedToLabel = new Text("Replied to: " + getMessageService().getMessageById(message.getReplyMessage()).getText());
            repliedToLabel.setStyle("-fx-font-weight: bold; -fx-fill: #888;");
            messageBox.getChildren().add(repliedToLabel);
        }
        messageBox.getChildren().add(messageBubble);

        // Create the main container for the message
        HBox container = new HBox(messageBox);
        container.setPadding(new Insets(5));
        container.setAlignment(getMessageAlignment(message));
        enableDragToReply(container, message); // Assuming this function enables drag-to-reply functionality

        // Handle double-click to edit or reply
        messageBubble.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                // Action on double-click, for example, edit the message
                editMessage(container, message); // Pass the container and message to the editMessage method
            }
        });

        return container;
    }

    private void editMessage(HBox container, Message message) {
        TextField editField = new TextField(message.getText());
        editField.setStyle("-fx-font-size: 14px;");
        container.getChildren().clear();
        container.getChildren().add(editField);
        editField.setOnAction(e -> {
            message.setText(editField.getText());
            container.getChildren().clear();
            container.getChildren().add(new TextFlow(new Text(message.getText())));
            chatHistory.refresh();
        });
    }




    private String getMessageBubbleStyle(Message message) {
        return Objects.equals(message.getSender(), getSceneService().getCurrentUser().getId()) ?
                "-fx-background-color: #D1FAD7; -fx-border-radius: 10px; -fx-background-radius: 10px;" :
                "-fx-background-color: #D7E8FA; -fx-border-radius: 10px; -fx-background-radius: 10px;";
    }

    private Pos getMessageAlignment(Message message) {
        return Objects.equals(message.getSender(), getSceneService().getCurrentUser().getId()) ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT;
    }

    private void enableDragToReply(HBox container, Message message) {
        container.setOnMousePressed(event -> {
            double startX = event.getSceneX();
            container.setOnMouseDragged(dragEvent -> {
                double deltaX = dragEvent.getSceneX() - startX;
                if (deltaX > 0) {
                    container.setTranslateX(Math.min(deltaX, 20));
                }
            });
            container.setOnMouseReleased(dragEvent -> {
                if (container.getTranslateX() > 10) {
                    replyToField.setText("Replying to: " + message.getText());
                    replyingToMessage = message;
                    replyToField.setVisible(true);
                }
                container.setTranslateX(0);
            });
        });
    }

    private void loadConversation(User user) {
        List<Message> messages = getMessageService().getMessagesForConversation(getSceneService().getCurrentUser(), user);
        currentMessages.setAll(messages);
        scrollToBottom();
    }

    @FXML
    private void handleSendMessage(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    private void sendMessage() {
        Message msg = new Message(
                getSceneService().getCurrentUser().getId(),
                conversationList.getSelectionModel().getSelectedItem().getId(),
                messageInputField.getText(),
                replyingToMessage != null ? replyingToMessage.getId() : -1
        );
        msg.setId(RandomGenerator.getDefault().nextLong());

        try {
            getMessageService().addMessage(msg);
            resetMessageInputFields();
            loadConversation(conversationList.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            MessageAlert.showError(e.getMessage());
        }
    }

    private void resetMessageInputFields() {
        messageInputField.clear();
        replyToField.clear();
        replyToField.setVisible(false);
        replyingToMessage = null;
    }

    private void scrollToBottom() {
        chatHistory.scrollTo(currentMessages.size() - 1);
    }
}
