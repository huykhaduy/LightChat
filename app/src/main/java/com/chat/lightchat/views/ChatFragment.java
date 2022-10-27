package com.chat.lightchat.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chat.lightchat.databinding.FragmentChatBinding;
import com.chat.lightchat.views.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {



    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentChatBinding binding = FragmentChatBinding.inflate(inflater, container, false);

        binding.imgBtnSignOut.setOnClickListener(v->{nextActivity();});



//        return view;
            return binding.getRoot();
    }



    private void nextActivity(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}