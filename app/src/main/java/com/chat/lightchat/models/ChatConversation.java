package com.chat.lightchat.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ChatConversation {
    private String chatId;
    private String chatName;
    private String imageUrl;
    private String sampleText;
    private @ServerTimestamp Timestamp createAt;
    private @ServerTimestamp Timestamp lastUpdate;
    private List<String> listMemberId;
    private static final String TAG = "Chat Conversation";

    public ChatConversation() {
    }

    public ChatConversation(String chatName, String imageUrl, String sampleText, List<String> listMemberId) {
        this.chatName = chatName;
        this.imageUrl = imageUrl;
        this.sampleText = sampleText;
        this.listMemberId = listMemberId;
    }

    public static ChatConversation getChatConversationId(String chatId){
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        final ChatConversation[] conversation = new ChatConversation[1];
        ref.collection("ChatRoom").document(chatId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                conversation[0] = documentSnapshot.toObject(ChatConversation.class);
            }
        });
        return conversation[0];
    }

    public static void addChatConversation(ChatConversation conversation){
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("ChatRoom").add(conversation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public static void removeChatConversation(String chatId){
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("ChatRoom").document(chatId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public static void updateChatConversation(String chatId, ChatConversation conversation){
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("ChatRoom").document(chatId).set(conversation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully update!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error update document", e);
                    }
                });
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSampleText() {
        return sampleText;
    }

    public void setSampleText(String sampleText) {
        this.sampleText = sampleText;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "ChatConversation{" +
                "chatId='" + chatId + '\'' +
                ", chatName='" + chatName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", sampleText='" + sampleText + '\'' +
                ", createAt=" + createAt +
                ", lastUpdate=" + lastUpdate +
                ", listMemberId=" + listMemberId +
                '}';
    }
}