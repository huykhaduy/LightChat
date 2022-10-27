package com.chat.lightchat.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chat.lightchat.R;
import com.chat.lightchat.databinding.FragmentChatBinding;
import com.chat.lightchat.databinding.FragmentFriendsBinding;


public class FriendsFragment extends Fragment {



    public FriendsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFriendsBinding binding = FragmentFriendsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}