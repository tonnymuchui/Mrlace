package com.mrlace.mrlace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminCategoryActivity extends AppCompatActivity {
    @BindView(R.id.t_shirt) ImageView tShirts;
    @BindView(R.id.sports_t_shirt) ImageView sportsTShirts;
    @BindView(R.id.female_dresses) ImageView femaleDresses;
    @BindView(R.id.sweather) ImageView sweather;
    @BindView(R.id.glasses) ImageView glasses;
    @BindView(R.id.purses_bags_wallets) ImageView purses_bags_wallets;
    @BindView(R.id.hats_caps) ImageView hats_caps;
    @BindView(R.id.shoess) ImageView shoes;
    @BindView(R.id.headphoness_handfree) ImageView headphoness_handfree;
    @BindView(R.id.laptops_pc) ImageView laptops_pc;
    @BindView(R.id.watches) ImageView watches;
    @BindView(R.id.mobilePhones) ImageView mobilePhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        ButterKnife.bind(this);

        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","tShirt"));
            }
        });

        sportsTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","Sports tShirts"));
            }
        });

        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","Female Dresses"));
            }
        });

        sweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","sweathers"));
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","Glasses"));
            }
        });

        purses_bags_wallets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","Wallets Bags Purses"));
            }
        });

        hats_caps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","Hats Caps"));
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","Shoes"));
            }
        });

       headphoness_handfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","HeadPhones HandFree"));
            }
        });

        laptops_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","Laptops"));
            }
        });

       watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","Watches"));
            }
        });

        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class).putExtra("category","Mobile Phones"));
            }
        });

        
    }
}
