package com.chat.lightchat.presenters.ChatConversation;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chat.lightchat.adapter.ChatHomeAdapter;
import com.chat.lightchat.adapter.ChatMessageAdapter;
import com.chat.lightchat.models.ChatConversation;
import com.chat.lightchat.models.ChatMessage;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChatConversationPresenter implements ChatConversationContract.Presenter{
    private ChatMessageAdapter adapter;
    private static final String TAG = "Chat Conversation";
    private List<ChatMessage> listMessages;

    public ChatConversationPresenter() {
        listMessages = new ArrayList<>();
        adapter = new ChatMessageAdapter(listMessages);
    }

    @Override
    public void sendPicture(File img) {

    }

    @Override
    public void sendMessage(String text) {

    }

    @Override
    public void listenerForNewMessage(String chatId) {
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("ChatRoom").document(chatId).collection("Conversations")
                .orderBy("createAt", Query.Direction.ASCENDING).limitToLast(2000)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error", error);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    ChatMessage item = dc.getDocument().toObject(ChatMessage.class);
                                    item.setMessId(dc.getDocument().getId());
                                    int isHas = adapter.getItemHasKey(item.getMessId());
                                    if (isHas > -1)
                                        return;
                                    listMessages.add(item);
                                    adapter.notifyItemChanged(listMessages.size()-1);
                                    break;
                                case MODIFIED:
                                    ChatMessage editItem = dc.getDocument().toObject(ChatMessage.class);
                                    editItem.setMessId(dc.getDocument().getId());
                                    int index = adapter.getItemHasKey(editItem.getMessId());
                                    if (index >= 0) {
                                        listMessages.set(index, editItem);
                                        adapter.notifyItemChanged(index);
                                    }
                                    break;
                                case REMOVED:
                                    ChatMessage deleteItem = dc.getDocument().toObject(ChatMessage.class);
                                    deleteItem.setMessId(dc.getDocument().getId());
                                    int deleteIndex = adapter.getItemHasKey(deleteItem.getMessId());
                                    if (deleteIndex >= 0) {
                                        listMessages.remove(deleteIndex);
                                        adapter.notifyItemRemoved(deleteIndex);
                                        adapter.notifyItemRangeChanged(deleteIndex, adapter.getItemCount());
                                        break;
                                    }
                            }
                        }
                    }
                });
    }

    public ChatMessageAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ChatMessageAdapter adapter) {
        this.adapter = adapter;
    }
}
