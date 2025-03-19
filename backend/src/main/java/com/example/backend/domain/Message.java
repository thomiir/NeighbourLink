package com.example.backend.domain;


public class Message extends Entity<Long> {
    private final Long senderId;
    private final Long receiverId;
    private String text;
    private final Long replyMessageId;


    public Message(Long sender, Long receiver, String text, Long replyMessage) {
        this.senderId = sender;
        this.receiverId = receiver;
        this.text = text;
        this.replyMessageId = replyMessage;
    }

    public Long getSender() {
        return senderId;
    }

    public Long getReceiver() {
        return receiverId;
    }

    public String getText() {
        return text;
    }

    public Long getReplyMessage() {
        return replyMessageId;
    }

    public void setText(String text) {
        this.text = text;
    }


}
