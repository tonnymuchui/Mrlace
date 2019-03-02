package com.mrlace.mrlace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrlace.mrlace.Auth.LoginActivity;
import com.mrlace.mrlace.Auth.RegisterActivity;
import com.mrlace.mrlace.Model.Users;
import com.mrlace.mrlace.prevalent.Prevalent;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
@BindView(R.id.main_ligin_btn) Button main_loginButton;
    @BindView(R.id.main_join_now_btn) Button main_Sign_upButton;
    private ProgressDialog loadingBar;
    private DatabaseReference rootRef;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        rootRef = FirebaseDatabase.getInstance().getReference();
        loadingBar = new ProgressDialog(this);
        Paper.init(this);
        main_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        main_Sign_upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != ""){

            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){

                AllowAcess(UserPhoneKey, UserPasswordKey);

                loadingBar.setTitle("Already Logged in!");
                loadingBar.setMessage("Please Wait!");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }
        }
    }

    private void AllowAcess(final String UserPhoneKey,final String UserPasswordKey) {


        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbName).child(UserPhoneKey).exists()){

                    Users usersData = dataSnapshot.child(parentDbName).child(UserPhoneKey).getValue(Users.class);

                    if (usersData.getPhoneNumber().equals(UserPhoneKey)){

                        if (usersData.getPassword().equals(UserPasswordKey)){

                            loadingBar.dismiss();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password Incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {

                    Toast.makeText(MainActivity.this, "Account Doesn't Exist!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }
}
