package com.example.tugas_akhir_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private EditText et_headline,et_harga,et_description;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        email = findViewById(R.id.crud_email);
        et_headline = findViewById(R.id.et_headline);
        et_harga = findViewById(R.id.et_harga);
        et_description = findViewById(R.id.et_description);
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
        String nama = et_headline.getText().toString();
        String harga = et_harga.getText().toString();
        String descripton= et_description.getText().toString();
        location loc = new location(nama,descripton,harga);
        mDatabase.child("locations").push().setValue(loc).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CRUD.this,"berhasil menambah data",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void show(View view) {
        Intent intent= new Intent(this,homebase.class);
        startActivity(intent);
    }
}