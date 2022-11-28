package com.chat.lightchat.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.chat.lightchat.R;
import com.chat.lightchat.databinding.FragmentProfileBinding;
import com.chat.lightchat.models.CurrentUser;
import com.chat.lightchat.utilities.ImageUrl;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.units.qual.Current;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    CircleImageView imgAva;
    FloatingActionButton floatingactionbutton;
    TextInputEditText textFullName;
    TextInputEditText textEmail;
    TextInputEditText textNumberPhone;
    Button btnUpdate;
    ProgressBar progressBar;
    private FirebaseUser user;

    public ProfileFragment() {
        // Required empty public constructor
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);
        registerListener();
        setDefaultData();




        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imgAva.setImageURI(uri);
        CurrentUser.updateUserImages(uri);
    }

    private void initView(View view){
        floatingactionbutton = view.findViewById(R.id.floatingActionButton);
        imgAva = view.findViewById(R.id.imgProject);
        textFullName = view.findViewById(R.id.inputEditTextNameProfile);
        textEmail = view.findViewById(R.id.inputEditTextEmailProfile);
        textNumberPhone = view.findViewById(R.id.inputEditTextNumberPhoneProfile);
        btnUpdate = view.findViewById(R.id.btn_Update);
        progressBar = view.findViewById(R.id.progressBarProfile);
    }

    private void registerListener(){
        floatingactionbutton.setOnClickListener(this);
    }

    private void setDefaultData(){
        textFullName.setText(user.getDisplayName());
        textEmail.setText(user.getEmail());
        textNumberPhone.setText(user.getPhoneNumber());
        Glide.with(getContext()).load(ImageUrl.getImage(user.getPhotoUrl())).centerCrop().placeholder(R.drawable.user).into(imgAva);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.floatingActionButton:
                clickHandelImg();
                break;
                default:
                break;

        }
    }


    public void clickHandelImg(){
                ImagePicker.with(ProfileFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
    }

}