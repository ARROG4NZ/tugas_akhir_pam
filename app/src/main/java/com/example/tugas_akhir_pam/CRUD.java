package com.example.tugas_akhir_pam;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CRUD extends AppCompatActivity {
    private static final int SELECT_IMAGE = 200;
    private TextView email;
    private EditText et_headline,et_harga,et_description;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private StorageReference storage;
    private ImageView uploadfoto,hasilgambar;
    private Button btn_tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        email = findViewById(R.id.crud_email);
        et_headline = findViewById(R.id.et_headline);
        et_harga = findViewById(R.id.et_harga);
        et_description = findViewById(R.id.et_description);
        uploadfoto = findViewById(R.id.uploadfoto);
        hasilgambar = findViewById(R.id.hasilgambar);
        btn_tambah = findViewById(R.id.btn_tambah);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://tugas-akhir-pam-7d020-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        storage = FirebaseStorage.getInstance("gs://tugas-akhir-pam-7d020.appspot.com/").getReference();
//        uploadfoto.setOnClickListener(view -> {
//            ActivityResultLauncher<String> mget = registerForActivityResult(new ActivityResultContracts.GetContent(),
//                    new ActivityResultCallback<Uri>() {
//                        @Override
//                        public void onActivityResult(Uri result) {
//                            uploadfoto.setImageURI(result);
//                        }
//                    });
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if(currentuser != null){
            updateUI(currentuser);
        }else{
            Toast.makeText(this,"Log In First",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void updateUI(FirebaseUser user){
        email.setText(user.getEmail());
    }

    public void tambah(View view) {
        String email = user.getEmail();
        //soalnya masih belum mebuat edit nama
        String nama = et_headline.getText().toString();
        String harga = et_harga.getText().toString();
        String descripton= et_description.getText().toString();
        location loc = new location(nama,descripton,harga);
        mDatabase.child("locations").push().setValue(loc).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //menambahkan gambar tapi masih ada bug gak tau kenapa
//                uploadfoto.setDrawingCacheEnabled(true);
//                uploadfoto.buildDrawingCache();
//                Bitmap bitmap = ((BitmapDrawable) uploadfoto.getDrawable()).getBitmap();
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
//                byte[] bytes = stream.toByteArray();
//                String pathfoto = "gambar_location/"+nama+".jpg";
//                UploadTask uploadTask = storage.child(pathfoto).putBytes(bytes);
//                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(CRUD.this,"berhasil menambah data\n dan berhasil mengupload foto", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                   Toast.makeText(CRUD.this,"berhasil menambah data\n tapi upload foto gagal",Toast.LENGTH_SHORT).show();
//                    }
//                });
                Toast.makeText(CRUD.this,"berhasil menambahkan data",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void show(View view) {
        Intent intent= new Intent(this,homebase.class);
        startActivity(intent);
    }

    public void tambah_gambar(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"pilih galery"),SELECT_IMAGE);
    }
    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
       if(result.getResultCode() == Activity.RESULT_OK) {
           Intent data = result.getData();
           if(data != null && data.getData() != null){
               Uri selectimg = data.getData();
               Bitmap img = null;
               try{
                   img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectimg);
                   if(hasilgambar.getVisibility() == View.GONE){
                       hasilgambar.setVisibility(View.VISIBLE);
                       hasilgambar.setImageBitmap(img);
                   }
                 
               }catch (IOException e){
                   e.printStackTrace();
               }
               uploadfoto.setImageBitmap(img);
           }

       }
    });
}