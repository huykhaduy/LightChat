package com.chat.lightchat.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chat.lightchat.R;
import com.chat.lightchat.presenters.login.LoginContract;
import com.chat.lightchat.presenters.login.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {

    private EditText mTextEmail, mTextPassword;
    private Button mButtonLogin, mButtonNavigatorRegistor;
    private TextView mTextViewError;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        registerListener();
        initPresenter();
    }

    private void initView() {
        mTextEmail = findViewById(R.id.text_email);
        mTextPassword = findViewById(R.id.text_password);
        mButtonLogin = findViewById(R.id.button_login);
        mButtonNavigatorRegistor = findViewById(R.id.button_navigator_register);

    }

    private void registerListener() {
        mButtonLogin.setOnClickListener(this);
        mButtonNavigatorRegistor.setOnClickListener(this);
    }

    private void initPresenter() {
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.setView(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                String email = mTextEmail.getText().toString();
                String password = mTextPassword.getText().toString();
                mLoginPresenter.handleLogin(email, password);
                break;

            case R.id.button_navigator_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFailure(String error) {

    }


}