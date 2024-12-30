package org.example.lab6.service;

import org.example.lab6.domain.Friendship;
import org.example.lab6.domain.Tuple;
import org.example.lab6.domain.User;
import org.example.lab6.repository.Repository;
import java.util.*;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static java.lang.Math.abs;

public class UserService {
    private final Repository<Long, User> userRepository;
    private final Repository<Tuple<Long, Long>, Friendship> friendshipRepository;

    public UserService(Repository<Long, User> repository, Repository<Tuple<Long, Long>, Friendship> friendshipFileRepository) {
        this.userRepository = repository;
        this.friendshipRepository = friendshipFileRepository;
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public List<String> getFriendsForUser(Long userId) {
        List<String> friendDetails = new ArrayList<>();
        Iterable<Friendship> allFriendships = friendshipRepository.findAll();
        for (Friendship friendship : allFriendships) {
            Tuple<Long, Long> friendshipId = friendship.getId();
            Long friendId = null;
            if (friendshipId.getLeft().equals(userId))
                friendId = friendshipId.getRight();
            else if (friendshipId.getRight().equals(userId))
                friendId = friendshipId.getLeft();
            if (friendId != null) {
                Optional<User> user = userRepository.findOne(friendId);
                if (user.isPresent()) {
                    User friend = user.get();
                    String friendInfo = friend.getUsername() + " - " + friend.getFullName();
                    friendDetails.add(friendInfo);
                }
            }
        }
        return friendDetails;
    }


    public Optional<User> findUser(String username) {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }


    private String userToString(User user) {
        return user.toString() + " ID : " + user.getId();
    }

    public void removeEntity(Long id) {
        Optional<User> deletedUser = userRepository.delete(id);
        if (deletedUser.isPresent()) {
            cleanupFriendships(id);
        } else
            throw new ServiceException("That user doesn't exist!");
    }

    private void cleanupFriendships(Long userId) {
        friendshipRepository.findAll().forEach(friendship -> {
            if (friendship.getId().getLeft().equals(userId) || friendship.getId().getRight().equals(userId)) {
                friendshipRepository.delete(friendship.getId());
            }
        });
    }

    public void addUser(User user) {
        if (findUser(user.getUsername()).isPresent())
            throw new ServiceException("That user already exists!");
        user.setId(generateId());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        try {
            userRepository.save(user);
        }
        catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

    }

    private Long generateId() {
        return abs(RandomGenerator.getDefault().nextLong());
    }

    public User userLogin(String username, String password) {
        Optional<User> userOptional = findUser(username);
        if (userOptional.isEmpty())
            throw new ServiceException("That user doesn't exist!");
        User user = userOptional.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        encoder.encode("password");
        if (!encoder.matches(password, user.getPassword()))
            throw new ServiceException("Wrong password!");
        return user;
    }


    public List<User> getAllUsersBesides(User paramUser) {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> !user.equals(paramUser))
                .collect(Collectors.toList());
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findOne(id);
    }

    public List<User> getAllUsersForZipCode(String zipCode) {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> user.getZipCode().equals(zipCode))
                .collect(Collectors.toList());
    }
}