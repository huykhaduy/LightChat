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
import androidx.appcompat.widget.SearchView;

import com.chat.lightchat.R;
import com.chat.lightchat.databinding.FragmentChatBinding;
import com.chat.lightchat.models.ChatConversation;
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
    private RecyclerView recyclerView;
    private SearchView searchView;

    public ChatFragment() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        mPresenter = new ChatHomePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        view.findViewById(R.id.imgBtnSignOut).setOnClickListener(v ->{nextActivity();});

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView = view.findViewById(R.id.recycviewUser);
        recyclerView.setAdapter(mPresenter.getmAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);

        searchView = view.findViewById(R.id.search_view);
//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                mPresenter.filterChatContains(searchView.getQuery().toString(), user.getUid());
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.filterChatContains(newText, user.getUid());
                return false;
            }
        });
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

    // Save chat demo lên firebase để test
    private void addToFirebase(){
        List<String> myList = new ArrayList<>();
        myList.add(user.getUid());
        for (int i=1;i<=10;i++){
            ChatConversation chat = new ChatConversation("Chat này để test "+Integer.toString(i), "image.png", "This is sample text", myList);
            ChatConversation.addChatConversation(chat);
        }
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