package com.chat.lightchat.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;


public class ChatMessage {
    public static int TYPE_TEXT = 1;
    public static int TYPE_IMAGE = 2;
    public static int LIMIT_MESSAGE = 1000;
    private String senderId;
    private String body;
    private @ServerTimestamp Timestamp createAt;
    private int messType;
    private String messId;
//    private String chatAvatar;
    private static final String TAG = "Chat Message";

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String body, int messType) {
        this.senderId = senderId;
        this.body = body;
        this.messType = messType;
    }

    public static void saveMessage(FirebaseUser user, String chatId, String body, int type){
        if (user == null)
            return;
        ChatMessage msg = new ChatMessage(user.getUid(), body, type);

        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("ChatRoom").document(chatId).collection("Conversations").add(msg)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        DocumentReference docRef = ref.collection("ChatRoom").document(chatId);
                        docRef.update("sampleText", body);
                        docRef.update("lastUpdate", FieldValue.serverTimestamp());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public static void removeMessage(String chatId, String messId){
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("ChatRoom").document(chatId).collection("Conversations").document(messId).delete()
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

    public static void updateMessage(FirebaseUser user, String chatId, String messId, String body, int type){
        if (user == null)
            return;
        ChatMessage msg = new ChatMessage(user.getUid(), body, type);
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("ChatRoom").document(chatId).collection("Conversations").document(messId).set(msg)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public int getMessType() {
        return messType;
    }

    public void setMessType(int messType) {
        this.messType = messType;
    }

    public String getMessId() {
        return messId;
    }

    public void setMessId(String messId) {
        this.messId = messId;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderId='" + senderId + '\'' +
                ", body='" + body + '\'' +
                ", createAt=" + createAt +
                ", messType=" + messType +
                ", messId='" + messId + '\'' +
                '}';
    }
}
