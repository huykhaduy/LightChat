package com.chat.lightchat.presenters.Friends;

import android.util.Log;
import android.util.Patterns;
import android.widget.ImageView;

import com.chat.lightchat.adapter.ListFriendAdapter;
import com.chat.lightchat.models.Friends;
import com.chat.lightchat.models.PublicUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FriendsPresenter implements FriendsContract.Presenter{
    private List<PublicUser> listFriend;
    private ListFriendAdapter mAdapter;
    private FriendsContract.View mView;
    private final List<PublicUser> allUsers = PublicUser.getAllUser();
    private FirebaseUser myUser = FirebaseAuth.getInstance().getCurrentUser();


    public FriendsPresenter(FriendsContract.View mView){
        this.mView = mView;
        this.listFriend = new ArrayList<>();
        this.mAdapter = new ListFriendAdapter(listFriend);
    }

    public ListFriendAdapter getmAdapter(){
        return mAdapter;
    }

    @Override
    public void filterFriendsContains(String searchText) {
        if (listFriend.size()>0){
            listFriend.clear();
        }
        if(!searchText.isEmpty()){
            for (PublicUser user: allUsers){
                if (StringUtils.containsIgnoreCase(user.getEmail(), searchText) || StringUtils.containsIgnoreCase(user.getDisplayName(), searchText))
                    if (!user.getUid().equals(myUser.getUid()))
                        listFriend.add(user);
            }
        }
        if (listFriend.size() == 0){
            mView.showImageFriend();
            Log.i("Image","Show");
        }
        else {
            mView.hideImageFriend();
            Log.i("Image","Hide");
        }
        mAdapter.setListFriends(this.listFriend);
    }
}
