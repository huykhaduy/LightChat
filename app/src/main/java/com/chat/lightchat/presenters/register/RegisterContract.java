package com.chat.lightchat.presenters.register;


public interface RegisterContract {

    interface View {
        void registerSuccess();
        void registerFailure(String error);
    }

    interface Presenter {
        void setView(RegisterContract.View view);
        void handleRegister(String fullName, String email, String password, String confirmPassword);
    }
}
