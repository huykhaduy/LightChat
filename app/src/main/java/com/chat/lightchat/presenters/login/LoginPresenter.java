package com.chat.lightchat.presenters.login;

import android.content.Intent;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.chat.lightchat.models.PublicUser;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPresenter implements LoginContract.Presenter {
    public static final int LOGIN_GOOGLE_CODE = 9001;
    protected LoginContract.View mView;
    protected FirebaseAuth mAuth;
    private final static String TAG = "Login Presenter";

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void loginGoogle(Intent data) {
//        mView.showLoading(true);
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
            firebaseAuthWithGoogle(account.getIdToken());
//            mView.showLoading(false);
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e);
//            mView.showLoading(false);
        }
    }

    @Override
    public void loginFacebook(AccessToken accessToken) {
//        mView.showLoading(true);
        Log.d(TAG, "handleFacebookAccessToken:" + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveNewUserToPublicInfo(task, user);
//                            mView.showLoading(false);
                            mView.showUserMain(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            mView.showLoading(false);
                            mView.showLoginFail("Lỗi");
                        }
                    }
                });
    }

    @Override
    public void loginAccount(String username, String password) {
        mView.showLoading(true);

        String errText = isValidSignUpDetails(username, password);
        if (!errText.equals("")){
            mView.showLoginFail(errText);
            mView.showLoading(false);
            return;
        }

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveNewUserToPublicInfo(task, user);

                            mView.showLoading(false);
                            mView.showLoginSuccess("Thành công");
                            mView.showUserMain(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            mView.showLoading(false);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            mView.showLoginFail("Thất bại");
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveNewUserToPublicInfo(task, user);
                            mView.showLoginSuccess("Thành công");
                            mView.showUserMain(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            mView.showLoginFail("Thất bại");
                        }
                    }
                });
    }

    private String isValidSignUpDetails(String email, String password){
        if(email.trim().isEmpty()){
            return ("Vui lòng nhập email");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ("Vui lòng nhập đúng định dạng email");
        }else if(password.trim().isEmpty()){
            return ("Vui lòng nhập mật khẩu");
        }
        return "";
    }

    protected void saveNewUserToPublicInfo(Task<AuthResult> task, FirebaseUser user){
        boolean isNewbie = task.getResult().getAdditionalUserInfo().isNewUser();
        if (isNewbie && user != null){
            saveNewUserToPublicInfo(user);
        }
    }

    protected void saveNewUserToPublicInfo(FirebaseUser user){
        PublicUser publicUser = new PublicUser(user, true);
        PublicUser.saveUserInfo(publicUser);
    }
}
