package com.chat.lightchat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chat.lightchat.R;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.lightchat.models.ChatConversation;
import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.models.Friends;
import com.chat.lightchat.models.PublicUser;
import com.chat.lightchat.utilities.ImageUrl;
import com.chat.lightchat.views.DuyChatMessageTest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListFriendAdapter extends RecyclerView.Adapter<ListFriendAdapter.ViewHolder> {
    private List<PublicUser> listFriends;
    private Context context;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
//        holder.avatar.setImageString(item.getPhotoUrl());
        holder.user = item;
        Glide.with(context).load(ImageUrl.getImage(item.getPhotoUrl())).centerCrop().placeholder(R.drawable.user).into(holder.avatar);

    }

    @Override
    public int getItemCount() {
        return listFriends.size() ;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CircleImageView avatar;
        public TextView tvName;
        public ImageButton imageButton;
        public PublicUser user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar  = itemView.findViewById(R.id.imageUserFriends);
            tvName = itemView.findViewById(R.id.tvNameFriends);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imageButton:
                    makeChatWith(user);
                    break;
            }
        }

        private void makeChatWith(PublicUser user){
            List<PublicUser> members = new ArrayList<>();
            members.add(user);
            PublicUser me = new PublicUser(FirebaseAuth.getInstance().getCurrentUser());
            members.add(me);
            ChatConversation chat = new ChatConversation(user.getDisplayName(), user.getPhotoUrl(), "Let's chat", members);
            ChatConversation.addAndOpenChatConversation(chat, context);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListFriends(List<PublicUser> listFriends) {
        this.listFriends = listFriends;
        this.notifyDataSetChanged();
    }
}
