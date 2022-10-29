package com.chat.lightchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.lightchat.R;
import com.chat.lightchat.models.ChatMessage;
import com.chat.lightchat.views.DuyChatMessageTest;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
    private List<ChatMessage> mChatMessages;

    public ChatMessageAdapter(List<ChatMessage> mChatMessages) {
        this.mChatMessages = mChatMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layout = LayoutInflater.from(context);
        View messageView = layout.inflate(R.layout.duy_chat_message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(messageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage mess = mChatMessages.get(position);
        holder.textView.setText(mess.toString());
    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.message_info);
        }

    }

    public int getItemHasKey(String key){
        for (int i=0;i<mChatMessages.size();i++){
            if (mChatMessages.get(i).getMessId().equals(key))
                return i;
        }
        return -1;
    }
}
