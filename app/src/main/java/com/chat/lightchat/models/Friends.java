package com.chat.lightchat.models;

public class Friends {
    private String imageUrl;
    private String fullName;
    private String chatId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Friends(String fullName, String imageUrl){
        this.fullName = fullName;
        this.imageUrl = imageUrl;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
