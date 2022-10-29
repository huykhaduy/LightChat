package com.chat.lightchat.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.lightchat.R;
import com.chat.lightchat.models.CurrentUser;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private Context context;
    private List<CurrentUser> listUser;


    public UserAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CurrentUser> currentUserList){
        this.listUser = currentUserList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new  UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        CurrentUser currentUser = listUser.get(position);
        if(currentUser == null){
            return;
        }
        holder.imageView.setImageURI(currentUser.getPhotoUrl());
        holder.tvName.setText(currentUser.getDisplayName());

    }

    @Override
    public int getItemCount() {
        if(listUser !=null){
            return listUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView tvName;
        private TextView tvChat;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageUser);
            tvName = itemView.findViewById(R.id.tvName);
            tvChat = itemView.findViewById(R.id.tvChat);
        }
    }
}
