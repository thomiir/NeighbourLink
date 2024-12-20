package org.example.lab6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.example.lab6.domain.Friendship;
import org.example.lab6.domain.MessageAlert;
import org.example.lab6.util.observer.Observer;
import org.example.lab6.service.SceneService;
import org.example.lab6.service.UserService;


import java.util.StringTokenizer;

public class FriendsPageController extends AbstractController implements Observer {
    @FXML
    private ListView<String> listViewFriends, listViewRequests;

    private ObservableList<String> requestList;

    public void init() {
        getRequestService().addObserver(this);
        getFriendshipService().addObserver(this);
        configureListViewRequests();
        populateFriendsList();
        populateRequestsList();
    }

    private void configureListViewRequests() {
        listViewRequests.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(item);
                    setWrapText(true);
                    setPrefWidth(param.getWidth() - 20);
                }
            }
        });
    }

    private void populateFriendsList() {
        listViewFriends.getItems().clear();
        UserService userService = getUserService();
        SceneService sceneService = getSceneService();
        userService.getFriendsForUser(sceneService.getCurrentUser().getId())
                .forEach(listViewFriends.getItems()::add);
    }

    private void populateRequestsList() {
        requestList = FXCollections.observableArrayList();
        listViewRequests.setItems(requestList);
        requestList.clear();
        getRequestService().getRequestsForUser(getSceneService().getCurrentUser())
                .forEach(request -> requestList.add(getRequestService().requestToString(request)));
    }

    @FXML
    private void handleButtonAddFriendClicked() {
        getSceneService().switchScene("add-friend");
    }

    @FXML
    private void handleButtonDeleteFriendClicked() {
        String selectedFriend = getSelectedItem(listViewFriends);
        if (selectedFriend == null) return;
        try {
            Friendship friendship = findFriendshipFromSelectedItem(selectedFriend);
            getFriendshipService().removeFriendship(friendship.getId().getLeft(), friendship.getId().getRight());
            listViewFriends.getItems().remove(selectedFriend);
        } catch (Exception e) {
            MessageAlert.showError(e.getMessage());
        }
    }

    @FXML
    private void handleButtonAcceptRequestClicked() {
        String selectedRequest = getSelectedItem(listViewRequests);
        if (selectedRequest == null) return;
        try {
            Long senderId = extractSenderIdFromRequest(selectedRequest);
            Long receiverId = extractReceiverIdFromRequest(selectedRequest);
            if (senderId.equals(getSceneService().getCurrentUser().getId())) {
                MessageAlert.showError("You can't accept a sent request!");
                return;
            }
            getFriendshipService().addFriendship(senderId, receiverId);
            getRequestService().removeRequest(senderId, receiverId);
            requestList.remove(selectedRequest);
            update();
        } catch (Exception e) {
            MessageAlert.showError(e.getMessage());
        }
    }

    @FXML
    private void handleButtonDeleteRequestClicked() {
        String selectedRequest = getSelectedItem(listViewRequests);
        if (selectedRequest == null) return;
        try {
            Long senderId = extractSenderIdFromRequest(selectedRequest);
            Long receiverId = extractReceiverIdFromRequest(selectedRequest);
            getRequestService().removeRequest(senderId, receiverId);
            requestList.remove(selectedRequest);
        } catch (Exception e) {
            MessageAlert.showError(e.getMessage());
        }
    }

    private String getSelectedItem(ListView<String> listView) {
        var selectedItems = listView.getSelectionModel().getSelectedItems();
        return selectedItems.isEmpty() ? null : selectedItems.get(0);
    }

    private Friendship findFriendshipFromSelectedItem(String selectedItem) {
        StringTokenizer tokenizer = new StringTokenizer(selectedItem);
        String username = tokenizer.nextToken();
        return getFriendshipService().findFriendshipByUsername(username);
    }

    private Long extractSenderIdFromRequest(String request) {
        StringTokenizer tokenizer = new StringTokenizer(request);
        tokenizer.nextToken();
        return getUserService().findUser(tokenizer.nextToken()).orElseThrow().getId();
    }

    private Long extractReceiverIdFromRequest(String request) {
        StringTokenizer tokenizer = new StringTokenizer(request);
        tokenizer.nextToken();
        tokenizer.nextToken();
        tokenizer.nextToken();
        return getUserService().findUser(tokenizer.nextToken()).orElseThrow().getId();
    }

    @Override
    public void update() {
        populateRequestsList();
        populateFriendsList();
    }
}
