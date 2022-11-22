package com.chat.lightchat.presenters.Profile;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.models.PublicUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilePresenter implements ProfileContract.Presenter{
    @Override
    public void updateData(String displayName, String email, String phone) {
        CurrentUser.updateUserDisplayName(displayName);
        CurrentUser.updateUserEmail(email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        ref.collection("Users").document(user.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        PublicUser publicUseruser = documentSnapshot.toObject(PublicUser.class);
                        publicUseruser.setDisplayName(displayName);
                        publicUseruser.setEmail(email);
                        publicUseruser.setPhone(phone);
                        PublicUser.updateUserInfo(publicUseruser.getUid(), publicUseruser);
                    }
                });
    }
}
