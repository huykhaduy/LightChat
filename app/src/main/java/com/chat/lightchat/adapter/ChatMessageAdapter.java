package com.chat.lightchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chat.lightchat.R;
import com.chat.lightchat.models.ChatMessage;
import com.chat.lightchat.models.PublicUser;
import com.chat.lightchat.models.TimeSince;
import com.chat.lightchat.utilities.ImageUrl;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatMessage> mChatMessages;
    private Context context;
    private static final int TYPE_LEFT = 1;
    private static final int TYPE_RIGHT = 2;
    private int previousType = 0;
    private String userID;
    private PublicUser publicUser;
    public ChatMessageAdapter(List<ChatMessage> mChatMessages, String userID) {
        this.mChatMessages = mChatMessages;
        this.userID = userID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
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
                if (position < mChatMessages.size() - 1){
                    ChatMessage nextMess = mChatMessages.get(position+1);
                    if (nextMess.getSenderId().equals(mess.getSenderId()) && nextMess.getCreateAt().getSeconds() - mess.getCreateAt().getSeconds() <= 120){
                        holdReceive.avatar.setVisibility(View.INVISIBLE);
                        holdReceive.timeSent.setVisibility(View.GONE);
                        return;
                    }
                }

                if (mess.getCreateAt() != null){
                    holdReceive.timeSent.setText(TimeSince.from(mess.getCreateAt()));
                    holdReceive.timeSent.setVisibility(View.VISIBLE);
                } else {
                    holdReceive.timeSent.setText("");
                    holdReceive.timeSent.setVisibility(View.GONE);
                }


                // Get user in database
                if (publicUser == null){
                    FirebaseFirestore ref = FirebaseFirestore.getInstance();
                    ref.collection("Users").document(mess.getSenderId())
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    publicUser = documentSnapshot.toObject(PublicUser.class);
                                    Glide.with(context).load(ImageUrl.getImage(publicUser.getPhotoUrl())).centerCrop().placeholder(R.drawable.user).into(((ReceiverViewHolder) holder).avatar);
                                }
                            });
                }
                else {
                    Glide.with(context).load(ImageUrl.getImage(publicUser.getPhotoUrl())).centerCrop().placeholder(R.drawable.user).into(((ReceiverViewHolder) holder).avatar);
                }

                break;
            case TYPE_RIGHT:
                SenderViewHolder holdSend = (SenderViewHolder) holder;
                holdSend.textView.setText(mess.getBody());

                if (position < mChatMessages.size() - 1){
                    ChatMessage nextMess = mChatMessages.get(position+1);
                    if (nextMess.getSenderId().equals(mess.getSenderId()) && nextMess.getCreateAt().getSeconds() - mess.getCreateAt().getSeconds() <= 120){
                        holdSend.timeSent.setVisibility(View.GONE);
                        holdSend.timeSent.setText("");
                        return;
                    }
                }

                if (mess.getCreateAt() != null) {
                    holdSend.timeSent.setVisibility(View.VISIBLE);
                    holdSend.timeSent.setText(TimeSince.from(mess.getCreateAt()));
                }
                else {
                    holdSend.timeSent.setVisibility(View.GONE);
                    holdSend.timeSent.setText("");
                }
                break;

            default:
                ViewHolder hold = (ViewHolder) holder;
                hold.textView.setText(mess.getBody());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage mess = mChatMessages.get(position);
        return getSimpleType(mess);
    }

    private int getSimpleType(ChatMessage mess){
        if (mess.getSenderId().equals(userID)){
            return TYPE_RIGHT;
        }
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
        public TextView timeSent;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvMsgChatSentSame);
            timeSent = itemView.findViewById(R.id.timeSentByMe);
        }
    }

    class ReceiverViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public CircleImageView avatar;
        public TextView timeSent;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvMsgChatReceiverSame);
            timeSent = itemView.findViewById(R.id.timeSent);
            avatar = itemView.findViewById(R.id.imgChatReceiver);
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