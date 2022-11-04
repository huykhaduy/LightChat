package com.chat.lightchat.presenters.Friends;

public class FriendsContract {
    public interface View{

    }

    public interface Presenter{
        void filterFriendsContains(String search, String userID);
    }
}
