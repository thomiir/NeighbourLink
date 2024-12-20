package org.example.lab6.service;

import org.example.lab6.domain.Message;
import org.example.lab6.domain.User;
import org.example.lab6.repository.Repository;
import org.example.lab6.util.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageService extends Observable {
    private final Repository<Long, Message> messageRepository;
//    private final Repository<Long, User> userRepository;

    public MessageService(Repository<Long, Message> messageRepository, Repository<Long, User> userRepository) {
        this.messageRepository = messageRepository;
//        this.userRepository = userRepository;
    }

    public List<Message> getMessagesForConversation(User sender, User receiver) {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), false)
                .filter(msg -> msg.getSender().equals(sender.getId()) && msg.getReceiver().equals(receiver.getId()) ||
                                        msg.getReceiver().equals(sender.getId()) && msg.getSender().equals(receiver.getId()))
                .collect(Collectors.toList());
    }

    public void addMessage(Message msg) {
        try {
            messageRepository.save(msg);
            notifyObservers();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Message getMessageById(Long id) {
        return messageRepository.findOne(id).orElse(null);
    }
}
