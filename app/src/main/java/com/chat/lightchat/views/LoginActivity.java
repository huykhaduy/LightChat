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
import android.widget.TextView;
import android.widget.Toast;

import com.chat.lightchat.R;
import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.presenters.login.LoginContract;
import com.chat.lightchat.presenters.login.LoginPresenter;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {

    protected FirebaseAuth mAuth;
    protected LoginContract.Presenter mPresenter;
    protected GoogleSignInClient mGoogleSignInClient;
    protected CallbackManager mCallbackManager;
    private static final String TAG = "Login Activity";

    private ImageButton btnLoginGoogle;
    private LoginButton btnLoginFacebook;
    private Button btnLogin;
    private TextView btnRegister;
    private EditText txtEmail;
    private EditText txtPassword;
    private TextView btnForgetPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        initView();
        initData();
        registerListener();
    }

    private void initView() {
        btnLoginGoogle = findViewById(R.id.btn_login_google);
        btnLoginFacebook = findViewById(R.id.btn_login_face);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_sign_up);
        txtEmail = findViewById(R.id.inputEditTextEmail);
        txtPassword = findViewById(R.id.inputEditTextPassword);
        progressBar = findViewById(R.id.progressBar);
        // Chua add hieu ung
        btnForgetPassword = findViewById(R.id.textForgetPwd);
    }

    private void registerListener() {
        btnLoginGoogle.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        //Fb with different button action
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

    protected void initData() {
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        this.initPresenter();
    }

    protected void initPresenter(){
        mPresenter = new LoginPresenter(this);
    }


    @Override
    public void showUserMain(FirebaseUser user) {
        if (user != null){
            CurrentUser currentUser = new CurrentUser(user);
            Toast.makeText(this, currentUser.toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void showLoginSuccess(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginFail(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(Boolean isLoading) {
        if(isLoading){
            btnLogin.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.btn_login:
                clickHandleLogin();
                break;
            case R.id.btn_sign_up:
                clickHandleRegister();
                showLoginSuccess("Load sign up");
                break;
            case R.id.btn_login_google:
                clickHandleGoogle();
                break;
//            case R.id.button_login_fb:
//                clickHandleFacebook();
//                break;
            default:
                break;
        }
    }

    public void clickHandleLogin(){
        String myEmail = txtEmail.getText().toString();
        String myPasword = txtPassword.getText().toString();
        mPresenter.loginAccount(myEmail, myPasword);
    }

    public void clickHandleRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickHandleGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, LoginPresenter.LOGIN_GOOGLE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("HashCode", String.valueOf(resultCode));
        if (LoginPresenter.LOGIN_GOOGLE_CODE == requestCode){
            mPresenter.loginGoogle(data);
        }
    }
}