package com.chat.lightchat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chat.lightchat.R;
import com.chat.lightchat.models.ChatConversation;
import com.chat.lightchat.models.PublicUser;
import com.chat.lightchat.models.TimeSince;
import com.chat.lightchat.utilities.ImageUrl;
import com.chat.lightchat.views.DuyChatMessageTest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatHomeAdapter extends RecyclerView.Adapter<ChatHomeAdapter.ViewHolder> {
    private List<ChatConversation> listConversation;
    private Context context;
    private FirebaseUser user;

    public ChatHomeAdapter() {
        listConversation = new ArrayList<>();
    }

    public ChatHomeAdapter(List<ChatConversation> listConversation) {
        this.listConversation = listConversation;
        this.user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View chatView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(chatView);
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
        holder.chatID = item.getChatId();
        holder.chatConversation = item;

        if (item.getListUsers().size()>1){
            if (item.getListUsers().get(0).getUid().equals(user.getUid())){
                PublicUser user = item.getListUsers().get(1);
                holder.tvName.setText(user.getDisplayName());
                holder.chatName = user.getDisplayName();
                holder.chatReceiver = user.getUid();
                holder.chatReceiverUrl = user.getPhotoUrl();
                Glide.with(context).load(ImageUrl
                                .getImage(user.getPhotoUrl())).centerCrop().placeholder(R.drawable.user)
                                .into(holder.avatar);
            }
            else {
                PublicUser user = item.getListUsers().get(0);
                holder.tvName.setText(user.getDisplayName());
                holder.chatName = user.getDisplayName();
                holder.chatReceiver = user.getUid();
                holder.chatReceiverUrl = user.getPhotoUrl();
                Glide.with(context).load(ImageUrl
                                .getImage(user.getPhotoUrl())).centerCrop().placeholder(R.drawable.user)
                                .into(holder.avatar);
            }
        } else {
            holder.tvName.setText(item.getChatName());
            holder.chatName = item.getChatName();
        }
//        holder.tvName.setText(item.getChatName());
        if (item.getLastUpdate() != null)
            holder.tvTime.setText(TimeSince.from(item.getLastUpdate()));
        else
            holder.tvTime.setText("");
        holder.tvChat.setText(item.getSampleText());
    }

    @Override
    public int getItemCount() {
        return listConversation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public String chatID;
        public String chatName;
        public String chatReceiver;
        public String chatReceiverUrl;
        public ChatConversation chatConversation;
        public CircleImageView avatar;
        public TextView tvName;
        public TextView tvTime;
        public TextView tvChat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            avatar  = itemView.findViewById(R.id.imageUser);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvChat = itemView.findViewById(R.id.tvChat);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DuyChatMessageTest.class);
            intent.putExtra("chatID", chatID);
            intent.putExtra("chatName", chatName);
            intent.putExtra("chatReceiver", chatReceiver);
            intent.putExtra("chatReceiverUrl", chatReceiverUrl);
            context.startActivity(intent);
        }
    }

    public List<ChatConversation> getListConversation() {
        return listConversation;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListConversation(List<ChatConversation> listConversation) {
        this.listConversation = listConversation;
        this.notifyDataSetChanged();
    }
}
