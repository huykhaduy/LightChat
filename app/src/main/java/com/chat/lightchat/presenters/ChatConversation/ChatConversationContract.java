package com.chat.lightchat.presenters.ChatConversation;

import java.io.File;

public class ChatConversationContract {
    public interface View{
        void showSending();
        void showImageUpload();
    }

    public interface Presenter{
        void sendPicture(File img);
        void sendMessage(String text);
        void listenerForNewMessage(String chatId);
    }
}
