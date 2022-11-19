package com.chat.lightchat.presenters.Friends;

public class FriendsContract {
    public interface View{
        void hideImageFriend();
        void showImageFriend();
    }

    public interface Presenter{
        void filterFriendsContains(String search);
    }
}
