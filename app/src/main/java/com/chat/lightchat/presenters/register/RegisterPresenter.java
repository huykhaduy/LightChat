package com.chat.lightchat.presenters.register;

import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.models.User;
import com.chat.lightchat.presenters.login.LoginContract;
import com.chat.lightchat.presenters.login.LoginPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPresenter extends LoginPresenter implements RegisterContract.Presenter{
    private final static String TAG = "Register Presenter";

    public RegisterPresenter(LoginContract.View mView) {
        super(mView);
    }

    @Override
    public void signUpAccount(String username, String password, String displayName, boolean isCheck) {
        mView.showLoading(true);
        String text = isValidSignUpDetails(displayName, username, password, isCheck);
        if (!text.equals("")){
            mView.showLoading(false);
            mView.showLoginFail(text);
            return;
        }
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    CurrentUser.updateUserDisplayName(displayName);
                    mView.showLoading(false);
                    mView.showLoginSuccess("Thành công");
                    mView.showUserMain(user);
                }
                else {
                    mView.showLoading(false);
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    mView.showLoginFail("Thất bại");
                    mView.showLoading(false);
                }
            }
        });
    }

    private String isValidSignUpDetails(String displayName, String email, String password, boolean isCheck){
        if (displayName.trim().isEmpty()){
            return "Vui lòng nhập họ và tên";
        }else if(email.trim().isEmpty()){
            return ("Vui lòng nhập email");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ("Vui lòng nhập đúng định dạng email");
        }else if(password.trim().isEmpty()){
            return ("Vui lòng nhập mật khẩu");
        }else if(password.trim().length() < 6){
            return ("Độ dài mật khẩu cần ít nhất 6 kí tự");
        }else if(!isCheck){
            return ("Đồng ý điều khoản trước khi đăng ký");
        }
        return "";
    }
}
