package com.example.tugas_akhir_pam;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class setting extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private TextView username;
    private TextView email;
    private EditText oldpass,newpass,renewpass;
    private ConstraintLayout setting, changepass;
    private ImageView imgprofile;
    private String realpath;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        username = findViewById(R.id.username_seting);
        email = findViewById(R.id.email_setting);
        setting = findViewById(R.id.setting_layout);
        changepass = findViewById(R.id.changepass_layout);
        oldpass = findViewById(R.id.old_pass);
        newpass = findViewById(R.id.new_pass);
        renewpass = findViewById(R.id.renew_pass);
        imgprofile = findViewById(R.id.imgprofile);
        mDatabase = FirebaseDatabase.getInstance("https://tugas-akhir-pam-7d020-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        username.setText(user.getDisplayName());
        email.setText(user.getEmail());
        //mengambil gambar dari firebase
        Uri pct = user.getPhotoUrl();
//        imgprofile.setImageURI(pct);

    }

    public void log_out(View view) {
        mAuth.signOut();
        Intent inten = new Intent(this, login.class);
        startActivity(inten);
    }


    public void setting(View view) {
        Intent intent = new Intent(setting.this,setting.class);
        startActivity(intent);
    }


    public void home(View view) {
        Intent intent = new Intent(setting.this,homebase.class);
        startActivity(intent);
    }


    public void ubahpass(View view) {
            String passbaru = newpass.getText().toString();
            String repass = renewpass.getText().toString();
            if(passbaru.equals(repass)){
                user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"password berhasil diubah");
                            setting.setVisibility(View.VISIBLE);
                            changepass.setVisibility(View.GONE);
                        }
                    }
                });
            }else{
                Toast.makeText(this, "new password dan renew passowrd tidak sama", Toast.LENGTH_SHORT).show();
            }
    }

    public void changepass(View view) {
        if(changepass.getVisibility()== View.GONE){
            setting.setVisibility(View.GONE);
            changepass.setVisibility(view.VISIBLE);
        }
    }

    public void changeProfile(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,2);
    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 ){
            String[] galeryPermision = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if(EasyPermissions.hasPermissions(this, galeryPermision)){
                Uri selectimg = data.getData();
                String[] filepath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectimg,filepath,null,null,null);
                c.moveToFirst();

                int columnindex = c.getColumnIndex(filepath[0]);
                String pctpath = c.getString(columnindex);
                c.close();

                Bitmap temp = (BitmapFactory.decodeFile(pctpath));
                //mengatur ukuran photo
                temp = Bitmap.createScaledBitmap(temp,70,70,true);
                imgprofile.setImageBitmap(temp);

                //mengganti photo profile dengan URI
                UserProfileChangeRequest profileupdate = new UserProfileChangeRequest.Builder().setPhotoUri(selectimg).build();
                user.updateProfile(profileupdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });

            }else{
                EasyPermissions.requestPermissions(this, "Access ditolak",101, galeryPermision);
            }
        }
    }

    public static final Bitmap getBit(ContentResolver cr, Uri uri) throws FileNotFoundException,IOException {
        InputStream input = cr.openInputStream(uri);
        Bitmap bit = BitmapFactory.decodeStream(input);
        input.close();
        return bit;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){

            new AppSettingsDialog.Builder(this).build().show();
        }
    }

}