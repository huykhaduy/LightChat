package com.chat.lightchat.presenters.Profile;

public class ProfileContract {
    interface View{
        void dispayNewData(String displayName, String email, String phone);
    }

    interface Presenter{
        void updateData(String displayName, String email, String phone);
    }
}
