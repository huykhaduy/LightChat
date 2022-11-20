package com.chat.lightchat.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.lightchat.R;
import com.chat.lightchat.adapter.ChatMessageAdapter;
import com.chat.lightchat.models.ChatConversation;
import com.chat.lightchat.models.ChatMessage;
import com.chat.lightchat.presenters.ChatConversation.ChatConversationPresenter;
import com.chat.lightchat.presenters.ChatHome.ChatHomePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DuyChatMessageTest extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecycleView;
    private ChatConversationPresenter mPresenter;
    private String chatId;
    private String chatNameStr;
    private AppCompatImageView addChat;
    private AppCompatImageView backMenu;
    private EditText textField;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mRecycleView = findViewById(R.id.recyclerViewChat);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mPresenter = new ChatConversationPresenter(user.getUid());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setAdapter(mPresenter.getAdapter());
        mRecycleView.setLayoutManager(layoutManager);
        Intent info = getIntent();
        chatId = info.getStringExtra("chatID");
        chatNameStr = info.getStringExtra("chatName");

        TextView chatName = findViewById(R.id.chatName);
        chatName.setText(chatNameStr);

        addChat = findViewById(R.id.addChatBtn);
        backMenu = findViewById(R.id.imageBack);
        textField = findViewById(R.id.textField);


        addChat.setOnClickListener(this);
        backMenu.setOnClickListener(this);
    }

    protected void onStart() {
        super.onStart();
        mPresenter.listenerForNewMessage(chatId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                finish();
                break;
            case R.id.addChatBtn:
                String text = textField.getText().toString();
                if (!text.isEmpty()){
                    ChatMessage.saveMessage(user, chatId, text, ChatMessage.TYPE_TEXT);
                    textField.setText("");
                }
                break;
            default:
                break;
        }
    }
}