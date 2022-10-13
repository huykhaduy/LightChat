package com.chat.lightchat.models;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;

public class User {
    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

//    public User(String fullName, String email, String password){
//        this.fullName = fullName;
//        this.email = email;
//        this.password = password;
//    }

    public User(String fullName, String email, String password, String confirmPassword) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public boolean isValidFullName() {
        return !TextUtils.isEmpty(fullName);
    }

    public boolean isValidEmail() {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword() {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

    public boolean isValidConfirmPassword() {
        return !TextUtils.isEmpty(confirmPassword) && confirmPassword.equals(password);
    }

    public String validation() {

        if(!this.isValidFullName()) return "Full Name is invalid";

        if(!this.isValidEmail()) return "Email is invalid";

        if(!this.isValidPassword()) return "Password is invalid";

        if(!this.isValidConfirmPassword()) return "Password does not match";

        return "";
    }

}
