package com.chat.lightchat.models;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CurrentUser {
    private String email;
    private String displayName;
    private Uri photoUrl;
    private boolean isEmailVerified;
    private String uid;
    private String providerID;
    private String phoneNumber;
    private static final String TAG = "CurrentUser";

    public CurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.setInfoDefault(user);
    }

    // OAuth provider
    public CurrentUser(UserInfo info){
        this.setInfoFromProvider(info);
    }

    // Firebase default Provider
    public CurrentUser(FirebaseUser user){
        this.setInfoDefault(user);
    }

    // Default POJO constructor
    public CurrentUser(String email, String displayName, Uri photoUrl, boolean isEmailVerified, String uid, String providerID, String phoneNumber) {
        this.email = email;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.isEmailVerified = isEmailVerified;
        this.uid = uid;
        this.providerID = providerID;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setInfoDefault(FirebaseUser user){
        displayName = user.getDisplayName();
        email = user.getEmail();
        photoUrl = user.getPhotoUrl();
        isEmailVerified = user.isEmailVerified();
        uid = user.getUid();
        providerID = user.getProviderId();
        phoneNumber = user.getPhoneNumber();
    }

    public void setInfoFromProvider(UserInfo info){
        displayName = info.getDisplayName();
        email = info.getEmail();
        photoUrl = info.getPhotoUrl();
        isEmailVerified = info.isEmailVerified();
        uid = info.getUid();
        providerID = info.getProviderId();
        phoneNumber = info.getPhoneNumber();
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", photoUrl=" + photoUrl +
                ", isEmailVerified=" + isEmailVerified +
                ", uid='" + uid + '\'' +
                ", providerID='" + providerID + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public static void updateUserInfo(String displayName, String photoUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .setPhotoUri(Uri.parse(photoUrl))
                .build();
        if (user == null){
            Log.i(TAG, "User is not login");
            return;
        }
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "Change successfully");
                        }
                    }
                });
    }

    public static Task<Void> updateUserDisplayName(String displayName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();
        if (user == null){
            Log.i(TAG, "User is not login");
            return null;
        }
        return user.updateProfile(profileUpdates);
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.i(TAG, "Change successfully");
//                        }
//                    }
//                });
    }

    public static void updateUserPhotoUrl(String photoUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(photoUrl))
                .build();
        if (user == null){
            Log.i(TAG, "User is not login");
            return;
        }
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "Change successfully");
                        }
                    }
                });
    }

    public static void updateUserEmail(String email){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Log.i(TAG, "User is not login");
            return;
        }
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });
    }

    public static void sendEmailConfirm(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null){
            Log.i(TAG, "User is not login");
            return;
        }
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    public static void updatePassword(String newPassword){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Log.i(TAG, "User is not login");
            return;
        }
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
    }

    public static void sendPasswordResetEmail(String emailAddress){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}
