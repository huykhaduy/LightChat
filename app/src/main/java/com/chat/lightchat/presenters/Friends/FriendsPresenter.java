package com.chat.lightchat.presenters.Friends;

import com.chat.lightchat.adapter.ListFriendAdapter;
import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.models.Friends;

import java.util.ArrayList;
import java.util.List;

public class FriendsPresenter implements FriendsContract.Presenter{
    private List<CurrentUser> listFriend;
    private ListFriendAdapter mAdapter;


    public FriendsPresenter(){
        this.listFriend = new ArrayList<>();
        this.mAdapter = new ListFriendAdapter(listFriend);

    }

    public ListFriendAdapter getmAdapter(){
        return mAdapter;
    }

    @Override
    public void filterFriendsContains(String searchText, String userID) {
        if(searchText == null || searchText.isEmpty()){
//            this.listFriend.clear();
            mAdapter.setListFriends(this.listFriend);

        }
    }
}
