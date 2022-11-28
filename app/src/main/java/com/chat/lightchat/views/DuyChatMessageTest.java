package com.chat.lightchat.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chat.lightchat.R;
import com.chat.lightchat.adapter.ChatMessageAdapter;
import com.chat.lightchat.models.ChatConversation;
import com.chat.lightchat.models.ChatMessage;
import com.chat.lightchat.models.PublicUser;
import com.chat.lightchat.presenters.ChatConversation.ChatConversationContract;
import com.chat.lightchat.presenters.ChatConversation.ChatConversationPresenter;
import com.chat.lightchat.presenters.ChatHome.ChatHomePresenter;
import com.chat.lightchat.utilities.ImageUrl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class DuyChatMessageTest extends AppCompatActivity implements View.OnClickListener, ChatConversationContract.View {
    private RecyclerView mRecycleView;
    private ChatConversationPresenter mPresenter;
    private String chatId;
    private String chatNameStr;
    private AppCompatImageView addChat;
    private AppCompatImageView backMenu;
    private EditText textField;
    private FirebaseUser user;
    private ProgressBar loadingBar;
    private CircleImageView imgAvatar;
    private String chatReceiver;
    private String chatReceiverUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get intent info
        Intent info = getIntent();
        chatId = info.getStringExtra("chatID");
        chatNameStr = info.getStringExtra("chatName");
        chatReceiver = info.getStringExtra("chatReceiver");
        chatReceiverUrl = info.getStringExtra("chatReceiverUrl");
        mRecycleView = findViewById(R.id.recyclerViewChat);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mPresenter = new ChatConversationPresenter(this, user.getUid());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        mRecycleView.setAdapter(mPresenter.getAdapter());
        mRecycleView.setLayoutManager(layoutManager);


        // Find view
        TextView chatName = findViewById(R.id.chatName);
        addChat = findViewById(R.id.addChatBtn);
        backMenu = findViewById(R.id.imageBack);
        textField = findViewById(R.id.textField);
        loadingBar = findViewById(R.id.progressBar_Chat);
        imgAvatar = findViewById(R.id.imgAvaChat);

        chatName.setText(chatNameStr);
        addChat.setOnClickListener(this);
        backMenu.setOnClickListener(this);
        Glide.with(this).load(ImageUrl.getImage(chatReceiverUrl)).centerCrop().placeholder(R.drawable.user).into(imgAvatar);
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
                String text = textField.getText().toString().strip();
                if (!text.isEmpty()){
                    ChatMessage.saveMessage(user, chatId, text, ChatMessage.TYPE_TEXT);
                    textField.setText("");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading() {
        this.loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void scrollToBottom() {
        this.mRecycleView.scrollToPosition(mPresenter.getListSize()-1);
    }
}