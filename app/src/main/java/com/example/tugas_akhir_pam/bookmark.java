package com.example.tugas_akhir_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class bookmark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
    }

    public void setting(View view) {
        Intent intent = new Intent(bookmark.this,setting.class);
        startActivity(intent);
    }


    public void home(View view) {
        Intent intent = new Intent(bookmark.this,homebase.class);
        startActivity(intent);
    }

    public void bookmark(View view) {
//        Intent intent = new Intent(homebase.this,bookmark.class);
//        startActivity(intent);
    }
}