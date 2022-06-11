package com.example.tugas_akhir_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CRUD extends AppCompatActivity {
    private TextView email;
    private EditText et_nama;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        email = findViewById(R.id.crud_email);
        et_nama = findViewById(R.id.et_nama);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://tugas-akhir-pam-7d020-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

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
        String nama = et_nama.getText().toString();
        String judul = "ini judul";
        user user = new user(nama,email,judul);
        mDatabase.child("user").child("coba").setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CRUD.this, "data berhasil ditambah",Toast.LENGTH_SHORT).show();
            }
        });
    }
}