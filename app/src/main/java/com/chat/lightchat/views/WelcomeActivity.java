package com.chat.lightchat.views;

import androidx.appcompat.app.AppCompatActivity;

import com.chat.lightchat.databinding.ActivityWelcomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;


public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }


    
    private void setListeners(){
        binding.btnLoginNow.setOnClickListener(v -> nextActivity());
    }

    private void nextActivity(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            startActivity(new Intent(getApplicationContext(), LoginUserActivity.class));
        }else{
            //Activity dont have :3

        }
    }

}