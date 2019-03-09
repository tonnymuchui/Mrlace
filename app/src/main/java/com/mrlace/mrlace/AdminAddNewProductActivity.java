package com.mrlace.mrlace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName;

    @BindView(R.id.admin_product_name) EditText product_name;
    @BindView(R.id.admin_product_description) EditText product_description;
    @BindView(R.id.admin_product_price) EditText product_price;
    @BindView(R.id.admin_select_product_image) ImageView select_product_image;
    @BindView(R.id.add_new_product) Button add_new_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        ButterKnife.bind(this);

        categoryName = getIntent().getExtras().get("category").toString();

        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

    }
}
