package com.chat.lightchat.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chat.lightchat.R;
import com.chat.lightchat.adapter.ChatMessageAdapter;
import com.chat.lightchat.models.ChatConversation;
import com.chat.lightchat.models.ChatMessage;
import com.chat.lightchat.presenters.ChatConversation.ChatConversationPresenter;
import com.chat.lightchat.presenters.ChatHome.ChatHomePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DuyChatMessageTest extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private ChatConversationPresenter mPresenter;
    private String chatId;
    private Button addChat;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duy_chat_message_test);
        mRecycleView = findViewById(R.id.message_recycle);

        mPresenter = new ChatConversationPresenter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setAdapter(mPresenter.getAdapter());
        mRecycleView.setLayoutManager(layoutManager);
        Intent info = getIntent();
        chatId = info.getStringExtra("chatID");

        addChat = findViewById(R.id.addChatBtn);

        user = FirebaseAuth.getInstance().getCurrentUser();

        addChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessage.saveMessage(user, chatId, "Hello world", ChatMessage.TYPE_TEXT);
                Toast.makeText(DuyChatMessageTest.this, "Add chat", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        mPresenter.listenerForNewMessage(chatId);
    }
}