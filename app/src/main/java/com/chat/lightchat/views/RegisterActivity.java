package com.chat.lightchat.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chat.lightchat.R;
import com.chat.lightchat.presenters.register.RegisterContract;
import com.chat.lightchat.presenters.register.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View, View.OnClickListener {

    private EditText mTextFullName, mTextEmail, mTextPassword, mTextConfirmPassword;
    private Button mButtonRegister, mButtonNavigatorLogin;
    private TextView mTextViewError;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        registerListener();
        initPresenter();
    }

    private void initView() {
        mTextFullName = findViewById(R.id.text_fullname);
        mTextEmail = findViewById(R.id.text_email);
        mTextPassword = findViewById(R.id.text_password);
        mTextConfirmPassword = findViewById(R.id.text_confirm_password);
        mButtonRegister = findViewById(R.id.button_register);
        mButtonNavigatorLogin = findViewById(R.id.button_navigator_login);
        mTextViewError = (TextView) findViewById(R.id.text_error);

    }

    private void registerListener() {
        mButtonRegister.setOnClickListener(this);
        mButtonNavigatorLogin.setOnClickListener(this);
    }

    private void initPresenter() {
        mRegisterPresenter = new RegisterPresenter();
        mRegisterPresenter.setView(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                String fullName = mTextFullName.getText().toString();
                String email = mTextEmail.getText().toString();
                String password = mTextPassword.getText().toString();
                String confirmPassword = mTextConfirmPassword.getText().toString();
                mRegisterPresenter.handleRegister(fullName, email, password, confirmPassword);
                break;

            case R.id.button_navigator_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void registerSuccess() {
        mTextViewError.setVisibility(View.GONE);
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void registerFailure(String error) {
        mTextViewError.setText(error);
        mTextViewError.setVisibility(View.VISIBLE);
    }
}