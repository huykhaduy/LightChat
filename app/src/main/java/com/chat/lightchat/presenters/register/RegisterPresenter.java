package com.chat.lightchat.presenters.register;

import androidx.annotation.NonNull;

import com.chat.lightchat.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenter implements  RegisterContract.Presenter{

    private RegisterContract.View mView;

    @Override
    public void setView(RegisterContract.View view) {
        mView = view;
    }

    @Override
    public void handleRegister(String fullName, String email, String password, String confirmPassword) {

        User user = new User(fullName, email, password, confirmPassword);
//            User user = new User(fullName, email, password);

        if(!user.validation().equals("")) {
            mView.registerFailure(user.validation());
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        OnCompleteListener<AuthResult> tResultOnCompleteListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()) {
                    mView.registerSuccess();
                } else {
                    mView.registerFailure("Register Failure");
                }
            }
        };

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                tResultOnCompleteListener
        );
    }
}
