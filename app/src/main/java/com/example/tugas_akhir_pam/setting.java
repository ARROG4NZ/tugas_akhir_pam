package com.example.tugas_akhir_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class setting extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView username;
    private TextView email;
    private ConstraintLayout setting, changepass;

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
        username.setText(user.getDisplayName());
        email.setText(user.getEmail());

    }

    public void changepass(View view) {
        if(setting.getVisibility() == View.VISIBLE){
            setting.setVisibility(View.GONE);
            changepass.setVisibility(view.VISIBLE);

        }
    }

    public void log_out(View view) {
        mAuth.signOut();
        Intent inten = new Intent(this, login.class);
        startActivity(inten);
    }
}