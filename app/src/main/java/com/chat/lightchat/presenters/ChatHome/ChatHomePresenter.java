package com.chat.lightchat.presenters.ChatHome;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chat.lightchat.adapter.ChatHomeAdapter;
import com.chat.lightchat.models.ChatConversation;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatHomePresenter implements ChatHomeContract.Presenter{
    private List<ChatConversation> listConversation;
    private static final String TAG = "Chat Home Presenter";
    private ChatHomeAdapter mAdapter;

    public ChatHomePresenter() {
        this.listConversation = new ArrayList<>();
        this.mAdapter = new ChatHomeAdapter(listConversation);
    }

    @Override
    public void listerForIncomingChatHome(String userID) {
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("ChatRoom").whereArrayContains("listMemberId", userID)
                .orderBy("lastUpdate", Query.Direction.ASCENDING)
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
                                    ChatConversation item = dc.getDocument().toObject(ChatConversation.class);
                                    item.setChatId(dc.getDocument().getId());
                                    int isHas = mAdapter.getItemHasKey(item.getChatId());
                                    if (isHas > -1)
                                        return;
                                    listConversation.add(0, item);
                                    mAdapter.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                    ChatConversation editItem = dc.getDocument().toObject(ChatConversation.class);
                                    editItem.setChatId(dc.getDocument().getId());
                                    if (editItem.getLastUpdate() == null)
                                        break;

                                    int index = mAdapter.getItemHasKey(editItem.getChatId());
                                    if (index >= 0) {
                                        listConversation.set(index, editItem);
                                        mAdapter.notifyItemChanged(index);
                                        bringToTop(index);
                                    }

                                    break;
                                case REMOVED:
                                    ChatConversation deleteItem = dc.getDocument().toObject(ChatConversation.class);
                                    deleteItem.setChatId(dc.getDocument().getId());
                                    int deleteIndex = mAdapter.getItemHasKey(deleteItem.getChatId());
                                    if (deleteIndex >= 0) {
                                        listConversation.remove(deleteIndex);
                                        mAdapter.notifyItemRemoved(deleteIndex);
                                        mAdapter.notifyItemRangeChanged(deleteIndex, mAdapter.getItemCount());
//                                        Toast.makeText(ChatHomeActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                            }
                        }
                    }
                });
    }

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public void filterChatContains(String searchText, String userID){
        Log.i("TextChage",searchText);
        if (searchText == null || searchText.isEmpty()){
            this.listConversation.clear();
            mAdapter.setListConversation(this.listConversation);
            this.listerForIncomingChatHome(userID);
        }
        else {
            List<ChatConversation> result = new ArrayList<>();
            for (ChatConversation chat : listConversation){
                if (StringUtils.containsIgnoreCase(chat.getChatName(), searchText)){
                    result.add(chat);
                }
            }
            mAdapter.setListConversation(result);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void bringToTop(int index){
        ChatConversation chat1 = listConversation.get(index);
        if (listConversation.size() > 1){
            listConversation.remove(index);
            listConversation.add(0, chat1);
            mAdapter.notifyDataSetChanged();
        }
    }

    public List<ChatConversation> getListConversation() {
        return listConversation;
    }

    public void setListConversation(List<ChatConversation> listConversation) {
        this.listConversation = listConversation;
    }

    public ChatHomeAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(ChatHomeAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }
}

