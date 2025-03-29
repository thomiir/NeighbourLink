package com.example.backend.domain;


import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name="messages")
public class Message extends Entity<Long> {

    @ManyToOne()
    @JoinColumn(name="sender")
    private User sender;

    @ManyToOne()
    @JoinColumn(name="receiver")
    private User receiver;
    private String text;

    @ManyToOne()
    @JoinColumn(name="reply")
    private Message replyMessage;

    public Message() {}

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public Message getReplyMessage() {
        return replyMessage;
    }
}
