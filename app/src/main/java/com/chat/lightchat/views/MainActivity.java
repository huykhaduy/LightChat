package com.chat.lightchat.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chat.lightchat.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword("duy123@gmail.com","03123131233");
    }
}