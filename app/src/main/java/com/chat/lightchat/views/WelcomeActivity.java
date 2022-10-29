package com.chat.lightchat.views;

import androidx.appcompat.app.AppCompatActivity;

import com.chat.lightchat.databinding.ActivityWelcomeBinding;
import com.chat.lightchat.models.CurrentUser;
import com.facebook.login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomeBinding binding;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            CurrentUser myUser = new CurrentUser(user);
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this,  myUser.toString(), Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }

        setListeners();
    }
    
    private void setListeners(){
        binding.btnLoginNow.setOnClickListener(v -> nextActivity());
    }

    private void nextActivity(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}