package com.chat.lightchat.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chat.lightchat.databinding.FragmentChatBinding;
import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.presenters.ChatHome.ChatHomeContract;
import com.chat.lightchat.presenters.ChatHome.ChatHomePresenter;
import com.chat.lightchat.views.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements ChatHomeContract.View {
    private FirebaseUser user;
    private static final String TAG = "ChatHome";
    private ChatHomePresenter mPresenter;

    public ChatFragment() {
        mPresenter = new ChatHomePresenter();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentChatBinding binding = FragmentChatBinding.inflate(inflater, container, false);
        binding.imgBtnSignOut.setOnClickListener(v->{nextActivity();});
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), RecyclerView.VERTICAL, false);
        binding.recycviewUser.setLayoutManager(linearLayoutManager);
        binding.recycviewUser.setAdapter(mPresenter.getmAdapter());
        return binding.getRoot();
    }

    public void onStart() {
        super.onStart();
        mPresenter.listerForIncomingChatHome(user.getUid());
    }


    private void nextActivity(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }


    @Override
    public void showProfile() {

    }

    @Override
    public void showFriends() {

    }

    @Override
    public void showChats() {

    }

    @Override
    public void openChatMessage(String chatID) {

    }
}