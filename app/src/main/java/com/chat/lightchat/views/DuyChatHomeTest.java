package com.chat.lightchat.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chat.lightchat.R;
import com.chat.lightchat.adapter.ChatHomeAdapter;
import com.chat.lightchat.models.ChatConversation;
import com.chat.lightchat.presenters.ChatHome.ChatHomeContract;
import com.chat.lightchat.presenters.ChatHome.ChatHomePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuyChatHomeTest extends AppCompatActivity implements ChatHomeContract.View{
    private FirebaseUser user;
    private static final String TAG = "ChatHome";
    private ChatHomePresenter mPresenter;
    private RecyclerView recyclerView;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duy_chat_home_test);
        mPresenter = new ChatHomePresenter();
        user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(mPresenter.getmAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);

        btn = findViewById(R.id.button2);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String chatName, String imageUrl, String sampleText, List<String> listMemberId
//                List<String> myList = new ArrayList<>();
//                myList.add(user.getUid());
//                ChatConversation chat = new ChatConversation("Chat voi duy", null, "This is sample text", myList);
//                ChatConversation.addChatConversation(chat);
//                Toast.makeText(DuyChatHomeTest.this, "Add conversation", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    protected void onStart() {
        super.onStart();
        mPresenter.listerForIncomingChatHome(user.getUid());
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