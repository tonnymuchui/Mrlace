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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrlace.mrlace.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_btn) Button login_btn;
    @BindView(R.id.login_phone_input) EditText login_phone_input;
    @BindView(R.id.login_password_input) EditText login_password_input;
    private ProgressDialog loadingBar;
    private DatabaseReference rootRef;
    private String parentDbName = "Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        rootRef = FirebaseDatabase.getInstance().getReference();
        loadingBar = new ProgressDialog(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }

        });
    }

    private void LoginUser() {

String phoneNumber = login_phone_input.getText().toString();
String loginPassword = login_password_input.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)){

            Toast.makeText(this, "Enter PhoneNumber", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(loginPassword)){

            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else {
    loadingBar.setTitle("Loging In");
    loadingBar.setMessage("Please Wait!");
    loadingBar.setCanceledOnTouchOutside(false);
    loadingBar.show();

    AllowAccount(phoneNumber, loginPassword);

        }
    }

    private void AllowAccount(final String phoneNumber, String loginPassword) {
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbName).child(phoneNumber).exists()){

                    
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Account Doesn't Exist!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
