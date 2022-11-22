package com.chat.lightchat.presenters.ChatConversation;

import java.io.File;

public class ChatConversationContract {
    public interface View{
        void showLoading();
        void hideLoading();
        void scrollToBottom();
    }

    public interface Presenter{
        void listenerForNewMessage(String chatId);
        int getListSize();
    }
}
