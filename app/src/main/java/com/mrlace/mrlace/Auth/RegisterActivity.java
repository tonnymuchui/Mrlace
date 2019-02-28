package com.mrlace.mrlace.Auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.mrlace.mrlace.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_btn) Button register_btn;
    @BindView(R.id.register_username_input) EditText register_username_input;
    @BindView(R.id.register_phone_input) EditText register_phone_input;
    @BindView(R.id.register_password_input) EditText register_password_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }
}
