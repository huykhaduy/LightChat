package com.chat.lightchat.models;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class SuccessHandle {
    private String TAG;

    public SuccessHandle(String TAG) {
        this.TAG = TAG;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public class Save implements OnSuccessListener<DocumentReference> {
        @Override
        public void onSuccess(DocumentReference documentReference) {
            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
        }
    }

    public class Update implements OnSuccessListener<Void>{
        @Override
        public void onSuccess(Void aVoid) {
            Log.d(TAG, "DocumentSnapshot successfully update!");
        }
    }

    public class Remove implements OnSuccessListener<Void>{
        @Override
        public void onSuccess(Void aVoid) {
            Log.d(TAG, "DocumentSnapshot successfully deleted!");
        }
    }
}
