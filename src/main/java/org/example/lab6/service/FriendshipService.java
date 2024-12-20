package org.example.lab6.service;

import org.example.lab6.domain.Friendship;
import org.example.lab6.domain.Tuple;
import org.example.lab6.domain.User;
import org.example.lab6.repository.Repository;
import org.example.lab6.util.observer.Observable;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class FriendshipService extends Observable {
    private final Repository<Tuple<Long, Long>, Friendship> friendshipRepository;
    private final Repository<Long, User> userRepository;

    public FriendshipService(Repository<Tuple<Long, Long>, Friendship> friendshipRepository, Repository<Long, User> userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public void addFriendship(Long id1, Long id2) throws SQLException {
        if (Objects.equals(id1, id2))
            throw new ServiceException("You can't befriend yourself!");
        User user1 = userRepository.findOne(id1).get();
        User user2 = userRepository.findOne(id2).get();
        notifyObservers();
        saveFriendship(user1, user2);
    }

    private void saveFriendship(User user1, User user2) throws SQLException {
        Friendship friendship = new Friendship();
        friendship.setDateSent(LocalDateTime.now());
        friendship.setId(new Tuple<>(user1.getId(), user2.getId()));
        friendshipRepository.save(friendship);
        notifyObservers();
    }

    public void removeFriendship(Long id1, Long id2) {
        User user1 = userRepository.findOne(id1).get();
        User user2 = userRepository.findOne(id2).get();
        friendshipRepository.delete(new Tuple<>(user1.getId(), user2.getId()));
    }

    public Friendship findFriendshipByUsername(String username) {
        for (Friendship friendship : friendshipRepository.findAll()) {
            Optional<User> user1 = userRepository.findOne(friendship.getId().getLeft());
            Optional<User> user2 = userRepository.findOne(friendship.getId().getRight());
            if (user1.get().getUsername().equals(username) || user2.get().getUsername().equals(username))
                return friendship;
        }
        return null;
    }
}