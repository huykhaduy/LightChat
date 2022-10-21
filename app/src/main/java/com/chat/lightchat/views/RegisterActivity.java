package com.chat.lightchat.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chat.lightchat.R;
import com.chat.lightchat.presenters.login.LoginPresenter;
import com.chat.lightchat.presenters.register.RegisterContract;
import com.chat.lightchat.presenters.register.RegisterPresenter;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends LoginActivity implements RegisterContract.View, View.OnClickListener {

    private EditText txtUserEmail;
    private EditText txtUserName;
    private EditText txtUserPassword;
    private Button btnSignUp;
    private TextView btnBack;
    private LoginButton btnLoginFacebook;
    private ImageButton btnLoginGoogle;
    private RegisterPresenter mRegisterPrensenter;
    private RadioButton checkBtn;
    private ProgressBar progressBar;
    private static final String TAG = "Register Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();
        initData();
        registerListener();
    }

    private void initView(){
        txtUserEmail = findViewById(R.id.inputEditTextEmail);
        txtUserName = findViewById(R.id.inputEditTextName);
        txtUserPassword = findViewById(R.id.inputEditTextPassword);
        btnSignUp = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.textSignIn);
        btnLoginGoogle = findViewById(R.id.btn_login_google);
        btnLoginFacebook = findViewById(R.id.btn_login_face);
        checkBtn = findViewById(R.id.radioBtnLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void initPresenter(){
        mRegisterPrensenter = new RegisterPresenter(this);
    }

    private void registerListener(){
        btnSignUp.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnLoginGoogle.setOnClickListener(this);

        //Fb with different listener
        btnLoginFacebook.setReadPermissions("email");
        btnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                mPresenter.loginFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d(TAG, "facebook:onError", e);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId){
                // Register
            case R.id.btnLogin:
                clickHandleRegister();
                break;
                // Back
            case R.id.textSignIn:
                clickHandleLogin();
                break;
                // Google
            case R.id.btn_login_google:
                clickHandleGoogle();
                break;
            default:
                break;
        }
    }

    @Override
    public void clickHandleLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void clickHandleRegister(){
        String displayName = txtUserName.getText().toString();
        String email = txtUserEmail.getText().toString();
        String password = txtUserPassword.getText().toString();
        boolean isCheck = checkBtn.isChecked();
        mRegisterPrensenter.signUpAccount(email, password, displayName, isCheck);
    }

    @Override
    public void showLoading(Boolean isLoading) {
        if(isLoading){
            btnSignUp.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            btnSignUp.setVisibility(View.VISIBLE);
        }
    }


}