package com.mrlace.mrlace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mrlace.mrlace.prevalent.Prevalent;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.settings_profile_image) CircleImageView settings_profile_image;
    @BindView(R.id.settings_phone_number) EditText settings_phone_number;
    @BindView(R.id.settings_full_name) EditText settings_full_name;
    @BindView(R.id.settings_address) EditText settings_address;
    @BindView(R.id.close_settings) TextView close_settings;
    @BindView(R.id.update_settings) TextView update_settings;
    @BindView(R.id.profile_image_change_btn) TextView profile_image_change_btn;
    private DatabaseReference databaseReference;
    private StorageTask uploadTask;
    private Uri imageUri;
    private String myUri = "";
    private StorageReference storageReferenceProfileRef;
    private String checker =  "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        storageReferenceProfileRef = FirebaseStorage.getInstance().getReference().child("Profile Pictures");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhoneNumber());
        userInfoDisplay(settings_profile_image, settings_phone_number, settings_full_name, settings_address);

        close_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked")){

                    userInfoSave();
                }
                else {
                    updateUserInfo();
                }
            }
        });
        profile_image_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });

    }

    private void updateUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", settings_full_name.getText().toString());
        userMap.put("PhoneOrder", settings_phone_number.getText().toString());
        userMap.put("address", settings_address.getText().toString());
        ref.child(Prevalent.currentOnlineUser.getPhoneNumber()).updateChildren(userMap);

        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
        Toast.makeText(SettingsActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && requestCode==RESULT_OK && data!=null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            settings_profile_image.setImageURI(imageUri);
        }
        else {
            Toast.makeText(this, "Error Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }

    private void userInfoSave() {

        if (TextUtils.isEmpty(settings_full_name.getText().toString())){
            Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
        }

        else  if (TextUtils.isEmpty(settings_phone_number.getText().toString())){
            Toast.makeText(this, "PhoneNumber required", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(settings_address.getText().toString())){
            Toast.makeText(this, "Address required", Toast.LENGTH_SHORT).show();
        }
        else if (checker.equals("clicked")){
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Upload Profile");
        progressDialog.setMessage("please wait......");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null){
            final StorageReference fileRef = storageReferenceProfileRef
                    .child(Prevalent.currentOnlineUser.getPhoneNumber() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();

                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){
                        Uri downloaduri = task.getResult();
                        myUri = downloaduri.toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("name", settings_full_name.getText().toString());
                        userMap.put("PhoneOrder", settings_phone_number.getText().toString());
                        userMap.put("address", settings_address.getText().toString());
                        userMap.put("image", myUri);
                        ref.child(Prevalent.currentOnlineUser.getPhoneNumber()).updateChildren(userMap);

                        progressDialog.dismiss();
                        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
                        Toast.makeText(SettingsActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SettingsActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Image not Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(final CircleImageView settings_profile_image, final EditText settings_phone_number, final EditText settings_full_name, final EditText settings_address) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {

                    if (dataSnapshot.child("image").exists()){
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("userName").getValue().toString();
                        String phone = dataSnapshot.child("phoneNumber").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(settings_profile_image);
                        settings_full_name.setText(name);
                        settings_phone_number.setText(phone);
                        settings_address.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
