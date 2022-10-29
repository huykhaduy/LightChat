package com.chat.lightchat.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chat.lightchat.databinding.FragmentChatBinding;
import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.views.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private UserAdapter userAdapter;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentChatBinding binding = FragmentChatBinding.inflate(inflater, container, false);

        binding.imgBtnSignOut.setOnClickListener(v->{nextActivity();});

        userAdapter = new UserAdapter(container.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), RecyclerView.VERTICAL, false);
        binding.recycviewUser.setLayoutManager(linearLayoutManager);
        userAdapter.setData(getListUser());
        binding.recycviewUser.setAdapter(userAdapter);

//        return view;
            return binding.getRoot();
    }



    private void nextActivity(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private List<CurrentUser> getListUser(){
        List<CurrentUser> list = new ArrayList<>();
        // Viet tiep ne
//        list.add(CurrentUser.)
        return list;
    }
}