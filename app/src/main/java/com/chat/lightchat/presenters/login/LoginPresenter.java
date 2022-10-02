package com.chat.lightchat.presenters.login;

import com.chat.lightchat.models.User;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;

    @Override
    public void setView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void handleLogin(String email, String password) {
        User user = new User(email, password);
        if(user.isValidEmail() && user.isValidPassword()) {
            mView.loginSuccess();
        }
    }
}
