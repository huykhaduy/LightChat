package com.chat.lightchat.presenters.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;

public class LoginContract {
    public interface View{
        void showUserMain(FirebaseUser user);
        void showLoginSuccess(String text);
        void showLoginFail(String text);
        void showLoading(Boolean isLoading);
    }

    public interface Presenter{
        void loginGoogle(Intent data);
        void loginFacebook(AccessToken accessToken);
        void loginAccount(String username, String password);
    }
}
