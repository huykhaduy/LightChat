package com.chat.lightchat.models;

import android.net.Uri;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PublicUser {
    private String uid;
    private String displayName;
    private String photoUrl;
    private String email;
    private String phone;
    private boolean isOnline;
    private @ServerTimestamp Timestamp lastOnline;
    private final static String TAG = "Public User";

    public PublicUser() {
    }

    public PublicUser(FirebaseUser user, boolean isOnline, Timestamp lastOnline){
        this.uid = user.getUid();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.phone = user.getPhoneNumber();
        this.photoUrl = user.getPhotoUrl().toString();
        this.isOnline = isOnline;
        this.lastOnline = lastOnline;
    }

    public PublicUser(String uid, String displayName, String photoUrl, String email, String phone, boolean isOnline, Timestamp lastOnline) {
        this.uid = uid;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.email = email;
        this.phone = phone;
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

//    public static PublicUser searchUserInfo(@NotNull  String searchText){
//        List<PublicUser> list = new ArrayList<>();
//        FirebaseFirestore ref = FirebaseFirestore.getInstance();
//        //Search text contains name or emails
//        boolean isEmail = Patterns.EMAIL_ADDRESS.matcher(searchText).matches();
//
//        if (isEmail){
//            for (PublicUser user : myPublicUserList){
//                if (!user.email.isEmpty() && String.con)
//            }
//            //Old method
//            ref.collection("Users").whereEqualTo("email",searchText)
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot snapshots) {
//                            for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()){
//                                PublicUser user = documentSnapshot.toObject(PublicUser.class);
//                                list.add(user);
//                            }
//                        }
//                    });
//        }
//        else {
//
//            ref.collection("Users").whereEqualTo("displayName", searchText)
//                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot snapshots) {
//                            for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()){
//                                PublicUser user = documentSnapshot.toObject(PublicUser.class);
//                                list.add(user);
//                            }
//                        }
//                    });
//        }
//        return null;
//    }

    public static List<PublicUser> getAllUser(){
        List<PublicUser> myList = new ArrayList<>();
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                List<PublicUser> list = snapshots.toObjects(PublicUser.class);
                for (PublicUser user:list){
                    Log.i("PublicUser",user.toString());
                }
                myList.addAll(list);
            }
        });
        return myList;
    }

    public static void saveUserInfo(PublicUser user){
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("Users").document(user.uid).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot written with ID: "+user.uid);
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PublicUser{" +
                "uid='" + uid + '\'' +
                ", displayName='" + displayName + '\'' +
                ", photoUrl=" + photoUrl +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", isOnline=" + isOnline +
                ", lastOnline=" + lastOnline +
                '}';
    }
}
