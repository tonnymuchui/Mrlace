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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrlace.mrlace.AdminCategoryActivity;
import com.mrlace.mrlace.HomeActivity;
import com.mrlace.mrlace.Model.Users;
import com.mrlace.mrlace.R;
import com.mrlace.mrlace.prevalent.Prevalent;
import com.rey.material.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_btn) Button login_btn;
    @BindView(R.id.login_phone_input) EditText login_phone_input;
    @BindView(R.id.login_password_input) EditText login_password_input;
    @BindView(R.id.log_remember_me_chkb) CheckBox checkBox;
    @BindView(R.id.admin_panel_link) TextView admin_panel_link;
    @BindView(R.id.not_admin_panel_link) TextView not_admin_panel_link;
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
        Paper.init(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }

        });

        admin_panel_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setText("Login Admin");
                admin_panel_link.setVisibility(View.INVISIBLE);
                not_admin_panel_link.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });
        not_admin_panel_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setText("Login");
                admin_panel_link.setVisibility(View.VISIBLE);
                not_admin_panel_link.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
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
    loadingBar.setTitle("Login Loading");
    loadingBar.setMessage("Please Wait!");
    loadingBar.setCanceledOnTouchOutside(false);
    loadingBar.show();

    AllowAccount(phoneNumber, loginPassword);

        }
    }

    private void AllowAccount(final String phoneNumber, final String loginPassword) {

        if (checkBox.isChecked()){

            Paper.book().write(Prevalent.UserPhoneKey, phoneNumber);
            Paper.book().write(Prevalent.UserPasswordKey, loginPassword);
        }

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbName).child(phoneNumber).exists()){

                    Users usersData = dataSnapshot.child(parentDbName).child(phoneNumber).getValue(Users.class);

                    if (usersData.getPhoneNumber().equals(phoneNumber)){

                        if (usersData.getPassword().equals(loginPassword)){

                       if (parentDbName.equals("Admins")){

                           loadingBar.dismiss();
                           startActivity(new Intent(LoginActivity.this,AdminCategoryActivity.class));
                       }
                       else if (parentDbName.equals("Users")){

                           loadingBar.dismiss();
                           startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                           Prevalent.currentOnlineUser = usersData;

                       }
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password Incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {

                    Toast.makeText(LoginActivity.this, "Account Doesn't Exist!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
}
