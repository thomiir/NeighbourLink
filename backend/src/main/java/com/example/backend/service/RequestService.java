package com.example.backend.service;

import com.example.backend.domain.Friendship;
import com.example.backend.domain.Request;
import com.example.backend.domain.Tuple;
import com.example.backend.domain.User;
import com.example.backend.repository.Repository;
import com.example.backend.util.observer.Observable;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RequestService extends Observable {
    private final Repository<Tuple<Long, Long>, Request> requestRepository;
    private final Repository<Long, User> userRepository;
    private final Repository<Tuple<Long, Long>, Friendship> friendshipRepository;

    public RequestService(Repository<Tuple<Long, Long>, Request> requestRepository, Repository<Long, User> userRepository, Repository<Tuple<Long, Long>, Friendship> friendshipRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public Iterable<Request> getRequestsForUser(User user) {
        return StreamSupport.stream(requestRepository.findAll().spliterator(), false)
                .filter(request -> request.getId().getLeft().equals(user.getId()) || request.getId().getRight().equals(user.getId()))
                .collect(Collectors.toList());
    }

    public void addRequest(User sender, User receiver) throws SQLException {
        Request newRequest = new Request(
                sender.getId(),
                receiver.getId(),
                LocalDateTime.now(),
                "pending"
        );
        if (sender.getId().equals(receiver.getId()))
            throw new ServiceException("You can't send a friend request to yourself!");
        if (friendshipRepository.findOne(new Tuple<>(sender.getId(), receiver.getId())).isPresent() ||
            friendshipRepository.findOne(new Tuple<>(receiver.getId(), sender.getId())).isPresent())
            throw new ServiceException("You can't send a friend request to a friend!");
        requestRepository.save(newRequest);
        notifyObservers();
    }

    public void removeRequest(Long senderID, Long receiverID) {
        notifyObservers();
        requestRepository.delete(new Tuple<>(senderID, receiverID));
    }

    public String requestToString(Request request) {
        return "From " +
                userRepository.findOne(request.getSender()).get().getUsername() +
                " To " +
                userRepository.findOne(request.getReceiver()).get().getUsername() +
                " On " +
                request.getDateSent() +
                " Status: " +
                request.getStatus();
    }

    public List<Request> getReceivedRequests(User user) {
        return StreamSupport.stream(requestRepository.findAll().spliterator(), false)
                .filter(request -> request.getReceiver().equals(user.getId()))
                .collect(Collectors.toList());
    }
}