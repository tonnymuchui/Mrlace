package com.mrlace.mrlace.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrlace.mrlace.MainActivity;
import com.mrlace.mrlace.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_btn) Button register_btn;
    @BindView(R.id.register_username_input) EditText register_username_input;
    @BindView(R.id.register_phone_input) EditText register_phone_input;
    @BindView(R.id.register_password_input) EditText register_password_input;
    private ProgressDialog loadingBar;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        rootRef = FirebaseDatabase.getInstance().getReference();
        loadingBar = new ProgressDialog(this);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }

            private void CreateAccount() {

                String userName = register_username_input.getText().toString();
                String phoneNumber = register_phone_input.getText().toString();
                String password = register_password_input.getText().toString();
                
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Your Number", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Please Provide Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please Wait");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    ValidatePhoneNumber(userName,phoneNumber,password);

                }
            }

            private void ValidatePhoneNumber(final String userName, final String phoneNumber, final String password) {

                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (!(dataSnapshot.child("Users").child(phoneNumber).exists())){

                            HashMap<String, Object> userDataMap = new HashMap<>();
                            userDataMap.put("userName", userName);
                            userDataMap.put("phoneNumber", phoneNumber);
                            userDataMap.put("password", password);

                            rootRef.child("Users").child(phoneNumber).updateChildren(userDataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                                            }
                                            else {

                                                loadingBar.dismiss();
                                                Toast.makeText(RegisterActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                        else {

                            Toast.makeText(RegisterActivity.this, "This" + "" + phoneNumber + "" + "Already exist!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });

            }
        });
    }
}
