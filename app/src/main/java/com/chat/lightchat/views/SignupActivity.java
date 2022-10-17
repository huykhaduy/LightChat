package com.chat.lightchat.views;

import androidx.appcompat.app.AppCompatActivity;

import com.chat.lightchat.databinding.ActivitySignupBinding;
import com.chat.lightchat.utilities.Constants;
import com.chat.lightchat.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private PreferenceManager preferenceManager;
    FirebaseFirestore database;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
    }

    private void setListeners(){
//        binding.textSignIn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),LoginUserActivity.class)));
        binding.textSignIn.setOnClickListener(v -> onBackPressed());

        binding.btnLogin.setOnClickListener(v->{
                if(isValidSignUpDetails()){
                    signUp();
                }
            });


    }

    private void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void signUp(){
        //Use FireStore
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        HashMap<String, Object> user = new HashMap<>();
//        user.put(Constants.KEY_NAME, binding.inputEditTextName.getText().toString());
//        user.put(Constants.KEY_EMAIL, binding.inputEditTextEmail.getText().toString());
//        user.put(Constants.KEY_PASSWORD, binding.inputEditTextPassword.getText().toString());
//        database.collection(Constants.KEY_COLLECTION_USERS)
//                .add(user)
//                .addOnSuccessListener(documentReference -> {
//                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
//                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
//                    preferenceManager.putString(Constants.KEY_NAME, binding.inputEditTextName.getText().toString());
//                    Intent intent = new Intent(getApplicationContext(), LoginUserActivity.class);
//                    startActivity(intent);
//                })
//                .addOnFailureListener(exception ->{
//                    showToast(exception.getMessage());
//                });
        loading(true);
        //Use Authe
        String textFullName = binding.inputEditTextName.getText().toString().trim();
        String textEmail = binding.inputEditTextEmail.getText().toString().trim();
        String password = binding.inputEditTextPassword.getText().toString().trim();
         FirebaseAuth mAuth  = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(textEmail, password).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        //Activity dont have :3
                        showToast("Bạn đã đăng ký thành công");
                        loading(false);
                        database = FirebaseFirestore.getInstance();
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = database.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put(Constants.KEY_FULL_NAME,textFullName );
                        user.put(Constants.KEY_EMAIL,textEmail );
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSucess: user Profile is created for" + userID );
                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                preferenceManager.putString(Constants.KEY_USER_ID, userID);
                                preferenceManager.putString(Constants.KEY_FULL_NAME,textFullName );
                                //Next Intent
                            }
                        });
                    } else {
                        // If sign in fails, display a message to the user.

                        showToast("Đăng ký thất bại.");
                        loading(false);

                    }
                });


    }

    private boolean isValidSignUpDetails(){
        if(binding.inputEditTextName.getText().toString().trim().isEmpty()){
            showToast("Vui lòng nhập họ và tên");
            return false;
        }else if(binding.inputEditTextEmail.getText().toString().trim().isEmpty()){
            showToast("Vui lòng nhập email");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEditTextEmail.getText().toString()).matches()){
            showToast("Vui lòng nhập đúng định dạng email");
            return false;
        }else if(binding.inputEditTextPassword.getText().toString().trim().isEmpty()){
            showToast("Vui lòng nhập mật khẩu");
            return false;
        }else if(binding.inputEditTextPassword.getText().toString().trim().length() < 6){
            showToast("Độ dài mật khẩu cần ít nhất 6 kí tự");
            return false;
        }else if(binding.radioBtnLogin.isChecked() == false ){
            showToast("Đồng ý điều khoản trước khi đăng ký");
            return false;
        }

        return true;
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

}