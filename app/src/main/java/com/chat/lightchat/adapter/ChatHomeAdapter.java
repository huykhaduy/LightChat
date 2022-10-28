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
import com.chat.lightchat.models.ChatConversation;
import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.views.DuyChatMessageTest;

import java.util.List;

public class ChatHomeAdapter extends RecyclerView.Adapter<ChatHomeAdapter.ViewHolder> {
    private List<ChatConversation> listConversation;
    private Context context;

    public ChatHomeAdapter() {
    }

    public ChatHomeAdapter(List<ChatConversation> listConversation) {
        this.listConversation = listConversation;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View chatView = inflater.inflate(R.layout.duy_chat_home_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(chatView);
        return viewHolder;
    }

    public int getItemHasKey(String key){
        for (int i=0;i<listConversation.size();i++){
            if (listConversation.get(i).getChatId().equals(key))
                return i;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatConversation item = listConversation.get(position);
        holder.getTextView().setText(item.toString());
        holder.chatID = item.getChatId();
    }

    @Override
    public int getItemCount() {
        return listConversation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        public String chatID;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = itemView.findViewById(R.id.chat_info);
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DuyChatMessageTest.class);
            intent.putExtra("chatID", chatID);
            context.startActivity(intent);
        }
    }

    public List<ChatConversation> getListConversation() {
        return listConversation;
    }

    public void setListConversation(List<ChatConversation> listConversation) {
        this.listConversation = listConversation;
    }
}
