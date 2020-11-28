package com.almusand.kawfira.Models;

public class Chat {
    private String id;
    private String senderId;
    private String message;
    private long sent;

    public Chat(String id, String senderId, String message, long sent) {
        this.id = id;
        this.senderId = senderId;
        this.message = message;
        this.sent = sent;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSent() {
        return sent;
    }

    public void setSent(long sent) {
        this.sent = sent;
    }
}
