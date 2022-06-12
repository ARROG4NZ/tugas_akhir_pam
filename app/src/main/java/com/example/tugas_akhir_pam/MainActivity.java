package com.example.tugas_akhir_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView nama,email,password,uid;
    private ImageView profil;
    FirebaseAuth mAuth;
    Button CRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nama = findViewById(R.id.tvnama);
        email = findViewById(R.id.tvemailAdress);
        password = findViewById(R.id.password);
        uid = findViewById(R.id.tvuid);
        mAuth = FirebaseAuth.getInstance();
        profil = findViewById(R.id.photo);
        CRUD = findViewById(R.id.CRUD);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if(currentuser != null){
            updateUI(currentuser);
        }else{
            Toast.makeText(MainActivity.this,"Log In First",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUI(FirebaseUser user){
        nama.setText(user.getDisplayName());
        email.setText(user.getEmail());
        Uri photo = user.getPhotoUrl();
        profil.setImageURI(photo);
        uid.setText(user.getUid());
    }

    public void log_out(View view) {

        mAuth.signOut();
        Intent inten = new Intent(this, login.class);
        startActivity(inten);
    }

    public void CURD(View view) {
        Intent inten = new Intent(this, CRUD.class);
        startActivity(inten);
    }
}