package com.chat.lightchat.models;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

public class PublicUser {
    private String uid;
    private String displayName;
    private Uri photoUrl;
    private String email;
    private boolean isOnline;
    private @ServerTimestamp Timestamp lastOnline;
    private final static String TAG = "Public User";

    public PublicUser() {
    }

    public PublicUser(FirebaseUser user, boolean isOnline, Timestamp lastOnline){
        this.uid = user.getUid();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.photoUrl = user.getPhotoUrl();
        this.isOnline = isOnline;
        this.lastOnline = lastOnline;
    }

    public PublicUser(String uid, String displayName, Uri photoUrl, String email, boolean isOnline, Timestamp lastOnline) {
        this.uid = uid;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.email = email;
        this.isOnline = isOnline;
        this.lastOnline = lastOnline;
    }

    public static PublicUser getUserInfo(String uid){
        final PublicUser[] user = new PublicUser[1];
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("Users").document(uid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user[0] = documentSnapshot.toObject(PublicUser.class);
                    }
                });
        return user[0];
    }

    public static void saveUserInfo(PublicUser user){
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("Users").add(user)
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

    public static void updateUserInfo(String uid, PublicUser info){
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("Users").document(uid).set(info)
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

    public static void removeUserInfo(String uid){
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("Users").document(uid).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully removed!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error remove document", e);
                    }
                });
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Timestamp getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Timestamp lastOnline) {
        this.lastOnline = lastOnline;
    }
}
