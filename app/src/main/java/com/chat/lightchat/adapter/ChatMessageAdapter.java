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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
//    private List<ChatMessage> mChatMessages;
//    private static final int TYPE_LEFT = 1;
//    private static final int TYPE_RIGHT = 2;
//    private String userID;
//    public ChatMessageAdapter(List<ChatMessage> mChatMessages, String userID) {
//        this.mChatMessages = mChatMessages;
//        this.userID = userID;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater layout = LayoutInflater.from(context);
//        View messageView;
//        if (viewType == TYPE_LEFT){
//            messageView = layout.inflate(R.layout.item_container_receiver_msg, parent, false);
//        }
//        else if (viewType == TYPE_RIGHT){
//            messageView = layout.inflate(R.layout.item_container_sent_msg, parent, false);
//        }
//        else {
//            messageView = layout.inflate(R.layout.duy_chat_message_item, parent, false);
//        }
//        ViewHolder viewHolder = new ViewHolder(messageView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        ChatMessage mess = mChatMessages.get(position);
//        holder.textView.setText(mess.toString());
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (mChatMessages.get(position).getSenderId().equals(userID))
//            return TYPE_RIGHT;
//        return TYPE_LEFT;
////        return super.getItemViewType(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mChatMessages.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView textView;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textView = itemView.findViewById(R.id.message_info);
//        }
//
//    }
//
//    public int getItemHasKey(String key){
//        for (int i=0;i<mChatMessages.size();i++){
//            if (mChatMessages.get(i).getMessId().equals(key))
//                return i;
//        }
//        return -1;
//    }
//}

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatMessage> mChatMessages;
    private static final int TYPE_LEFT = 1;
    private static final int TYPE_RIGHT = 2;
    private String userID;
    public ChatMessageAdapter(List<ChatMessage> mChatMessages, String userID) {
        this.mChatMessages = mChatMessages;
        this.userID = userID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layout = LayoutInflater.from(context);
        if (viewType == TYPE_LEFT){
            return new ReceiverViewHolder(layout.inflate(R.layout.item_container_receiver_msg, parent, false));
        }
        else if (viewType == TYPE_RIGHT){
            return new SenderViewHolder(layout.inflate(R.layout.item_container_sent_msg, parent, false));
        }
        else {
            return new ViewHolder(layout.inflate(R.layout.duy_chat_message_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage mess = mChatMessages.get(position);
        switch (holder.getItemViewType()){
            case TYPE_LEFT:
                ReceiverViewHolder holdReceive = (ReceiverViewHolder) holder;
                holdReceive.textView.setText(mess.getBody());
                break;
            case TYPE_RIGHT:
                SenderViewHolder holdSend = (SenderViewHolder) holder;
                holdSend.textView.setText(mess.getBody());
                break;
            default:
                ViewHolder hold = (ViewHolder) holder;
                hold.textView.setText(mess.getBody());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mChatMessages.get(position).getSenderId().equals(userID))
            return TYPE_RIGHT;
        return TYPE_LEFT;
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

    class SenderViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvMsgChatSent);
        }
    }

    class ReceiverViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public CircleImageView avatar;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.imgChatReceiver);
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