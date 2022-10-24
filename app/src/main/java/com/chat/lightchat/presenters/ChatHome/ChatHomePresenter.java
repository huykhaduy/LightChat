package com.chat.lightchat.presenters.ChatHome;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chat.lightchat.models.ChatConversation;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatHomePresenter implements ChatHomeContract.Presenter{
    private ArrayList<ChatConversation> listConversation;
    private static final String TAG = "Chat Home Presenter";

    @Override
    public void listerForIncomingChatHome(String userID) {
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("ChatRoom").whereArrayContains("listMemberId", userID)
                .orderBy("lastUpdate", Query.Direction.DESCENDING).limitToLast(2000)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error", error);
                            return;
                        }
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    ChatConversation item = dc.getDocument().toObject(ChatConversation.class);
                                    item.setChatId(dc.getDocument().getId());
                                    listConversation.add(item);
//                                    adapter.notifyItemChanged(chatItems.size()-1);
                                    break;
                                case MODIFIED:
                                    ChatConversation editItem = dc.getDocument().toObject(ChatConversation.class);
                                    editItem.setChatId(dc.getDocument().getId());
//                                    int index = adapter.getItemHasKey(editItem.getChatId());
//                                    if (index >= 0){
//                                        chatItems.set(index, editItem);
//                                        adapter.notifyItemChanged(index);
//                                    }

                                    break;
                                case REMOVED:
                                    ChatConversation deleteItem = dc.getDocument().toObject(ChatConversation.class);
                                    deleteItem.setChatId(dc.getDocument().getId());
                                    int deleteIndex = 0;
//                                    int deleteIndex = adapter.getItemHasKey(deleteItem.getChatId());
//                                    if (deleteIndex >= 0){
//                                        chatItems.remove(deleteIndex);
//                                        adapter.notifyItemRemoved(deleteIndex);
//                                        adapter.notifyItemRangeChanged(deleteIndex, adapter.getItemCount());
//                                        Toast.makeText(ChatHomeActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }
                });
    }
}
