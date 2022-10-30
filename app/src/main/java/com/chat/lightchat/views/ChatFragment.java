package com.chat.lightchat.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chat.lightchat.R;
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
//        FragmentChatBinding binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        view.findViewById(R.id.imgBtnSignOut).setOnClickListener(v ->{nextActivity();});
//        binding.imgBtnSignOut.setOnClickListener(v->{nextActivity();});
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
//        binding.recycviewUser.setLayoutManager(linearLayoutManager);
//        binding.recycviewUser.setAdapter(mPresenter.getmAdapter());
        RecyclerView recyclerView = view.findViewById(R.id.recycviewUser);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mPresenter.getmAdapter());
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.i("Recycle", "Roll");
            }
        });
        Log.i("Recycle", recyclerView.getParent().toString());
        return view;
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