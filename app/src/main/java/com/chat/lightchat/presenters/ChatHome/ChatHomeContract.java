package com.chat.lightchat.presenters.ChatHome;

public class ChatHomeContract {
    public interface View{
        void showProfile();
        void showFriends();
        void showChats();
        void openChatMessage(String chatID);
    }

    public interface Presenter{
        void listerForIncomingChatHome(String userID);
        void filterChatContains(String search, String userID);
    }
}
