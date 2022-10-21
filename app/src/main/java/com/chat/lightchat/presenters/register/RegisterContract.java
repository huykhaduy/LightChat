package com.chat.lightchat.presenters.register;

import com.chat.lightchat.presenters.login.LoginContract;

public class RegisterContract extends LoginContract{
    public interface Presenter{
        void signUpAccount(String username, String password, String displayName, boolean isCheck);
    }
}
