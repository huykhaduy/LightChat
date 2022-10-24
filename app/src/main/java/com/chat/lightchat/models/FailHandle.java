package com.chat.lightchat.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class FailHandle{
    private String TAG;

    public FailHandle(String TAG) {
        this.TAG = TAG;
    }

    public class Save implements OnFailureListener {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.w(TAG, "Error adding document", e);
        }
    }

    public class Update implements OnFailureListener{
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.w(TAG, "Error update document", e);
        }
    }

    public class Remove implements OnFailureListener {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.w(TAG, "Error remove document", e);
        }
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }
}
