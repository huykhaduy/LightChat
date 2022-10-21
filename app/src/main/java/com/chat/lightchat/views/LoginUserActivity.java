package com.chat.lightchat.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.chat.lightchat.databinding.ActivityLoginUserBinding;
import com.chat.lightchat.utilities.Constants;
import com.chat.lightchat.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;


public class LoginUserActivity extends AppCompatActivity {
    private ActivityLoginUserBinding binding;



    SharedPreferences sharedPreferences;
    PreferenceManager preferenceManager;
    FirebaseFirestore database;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){ //If you have account in phone before, it will turn on next activity
            //Next intent


            //finish(); Turn on finish();
//        }


        binding = ActivityLoginUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setListeners();


    }

    private void setListeners(){
        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE );

        //get sharePreferences
        binding.inputEditTextEmail.setText(sharedPreferences.getString("email",""));
        binding.inputEditTextPassword.setText(sharedPreferences.getString("password",""));
        binding.radioBtnLogin.setChecked(sharedPreferences.getBoolean("checked",false));



        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.inputEditTextEmail.getText().toString().trim();
            String password = binding.inputEditTextPassword.getText().toString().trim();

            if(binding.radioBtnLogin.isChecked()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.putBoolean("checked", true);
                editor.apply();
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("email");
                editor.remove("password");
                editor.remove("checked");
                editor.apply();
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            if(isValidSignInDetails()){
                signIn();
            }
        });

//        binding.btnLogin.setOnClickListener(v -> addDataFireStore());

        binding.btnSignUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),SignupActivity.class)));

    }

        private void showToast(String msg){
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

        private void signIn() {
            loading(true);
            String textEmail = binding.inputEditTextEmail.getText().toString().trim();
            String textPassword = binding.inputEditTextPassword.getText().toString().trim();
            FirebaseAuth mAuth  = FirebaseAuth.getInstance();

            mAuth.signInWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(this, task ->{
                if(task.isSuccessful()){
                    loading(false);
                    showToast("Bạn đã đăng nhập thành công");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        //Test doan code nay để lấy data quăng qua nextActivity bằng preferenceManager, nó chạy được, nma sẽ phát sinh vd: quay về welcome, lúc này cần 1 intent mới dể kết thúc task cũ
//                    database = FirebaseFirestore.getInstance();
//                    userID = mAuth.getCurrentUser().getUid();
//                    DocumentReference documentReference = database.collection("users").document(userID);
//                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
//                            preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
//                            preferenceManager.putString(Constants.KEY_FULL_NAME, documentSnapshot.getString(Constants.KEY_FULL_NAME));
//                            //Dont have intent
//                            //Next Intent
//                        }
//                    });

                }else{
                    loading(false);
                    showToast("Vui lòng nhập lại email hoặc mật khẩu");
                }
            });
        }

        private Boolean isValidSignInDetails(){
            if(binding.inputEditTextEmail.getText().toString().trim().isEmpty()){
                showToast("Vui lòng nhập email");
                return false;
            }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEditTextEmail.getText().toString()).matches()){
                showToast("Vui lòng nhập đúng định dạng email");
                return false;
            }else if(binding.inputEditTextPassword.getText().toString().trim().isEmpty()){
                showToast("Vui lòng nhập mật khẩu");
                return false;
            }else{
            return true;
            }
        }

        private void loading(Boolean isLoading){
            if(isLoading){
                binding.btnLogin.setVisibility(View.INVISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.btnLogin.setVisibility(View.VISIBLE);
            }
        }
//    private void addDataFireStore(){
//
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        HashMap<String, Object>  data = new HashMap<>();
//        data.put("first_name", "Phuc");
//        data.put("last_name", "Ly");
//        database.collection("uses")
//                .add(data)
//                .addOnSuccessListener(documentReference -> {
//                    Toast.makeText(getApplicationContext(), " Data Inserted ",Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(exception ->{
//                    Toast.makeText(getApplicationContext(),exception.getMessage() ,Toast.LENGTH_SHORT).show();
//                });
//    }

}