package com.chat.lightchat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chat.lightchat.R;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.lightchat.models.ChatConversation;
import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.models.Friends;
import com.chat.lightchat.models.PublicUser;
import com.chat.lightchat.views.DuyChatMessageTest;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListFriendAdapter extends RecyclerView.Adapter<ListFriendAdapter.ViewHolder> {
    private List<PublicUser> listFriends;
    private Context context;


    public ListFriendAdapter(List<PublicUser> listFriends){
        this.listFriends = listFriends;
    }

    @NonNull
    @Override
    public ListFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View FriendView = LayoutInflater.from(context).inflate(R.layout.item_friends, parent, false);
        return new ViewHolder(FriendView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFriendAdapter.ViewHolder holder, int position) {
        PublicUser item = listFriends.get(position);
//        holder.chatID = item.getChatId();

        holder.tvName.setText(item.getDisplayName());
//        holder.avatar.setImageURI(item.getPhotoUrl());

    }

    @Override
    public int getItemCount() {
        return listFriends.size() ;
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {
        public CircleImageView avatar;
        public TextView tvName;
        public ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar  = itemView.findViewById(R.id.imageUserFriends);
            tvName = itemView.findViewById(R.id.tvNameFriends);
            imageButton = itemView.findViewById(R.id.imageButton);
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListFriends(List<PublicUser> listFriends) {
        this.listFriends = listFriends;
        this.notifyDataSetChanged();
    }
}
