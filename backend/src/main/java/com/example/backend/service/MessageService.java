package com.example.backend.service;

import com.example.backend.repository.IMessageRepository;

public class MessageService {
    private final IMessageRepository messageRepository;

    public MessageService(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}
