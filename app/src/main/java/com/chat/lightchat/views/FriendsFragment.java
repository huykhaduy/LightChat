package com.chat.lightchat.views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chat.lightchat.R;
import com.chat.lightchat.databinding.FragmentChatBinding;
import com.chat.lightchat.databinding.FragmentFriendsBinding;
import com.chat.lightchat.presenters.ChatHome.ChatHomeContract;
import com.chat.lightchat.presenters.Friends.FriendsContract;
import com.chat.lightchat.presenters.Friends.FriendsPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment implements FriendsContract.View {
    private FirebaseUser user;
    private RecyclerView recyclerView;
    private FriendsPresenter mPresenter;
    private SearchView searchView;
    private ImageView imageView;


    public FriendsFragment() {
        // Required empty public constructor
        user = FirebaseAuth.getInstance().getCurrentUser();
        mPresenter = new FriendsPresenter(this);
    }

    @Override
    public void hideImageFriend(){
        imageView.setVisibility(View.GONE);
    }

    @Override
    public void showImageFriend(){
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        imageView = view.findViewById(R.id.imgFriends);

        recyclerView = view.findViewById(R.id.recyclerViewFriends);
        recyclerView.setAdapter(mPresenter.getmAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.filterFriendsContains(newText);
                return false;
            }
        });

        return view;
    }




}