package com.mrlace.mrlace;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private static final int GalleryPick = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        ButterKnife.bind(this);

        categoryName = getIntent().getExtras().get("category").toString();

        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

        select_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void openGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            select_product_image.setImageURI(imageUri);

        }

    }
}
