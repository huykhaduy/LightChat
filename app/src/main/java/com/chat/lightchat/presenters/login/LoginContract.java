package com.chat.lightchat.presenters.login;

public interface LoginContract {
    interface View {
        void loginSuccess();
        void loginFailure(String error);
    }

    interface Presenter {
        void setView(LoginContract.View view);
        void handleLogin(String email, String password);
    }
}
