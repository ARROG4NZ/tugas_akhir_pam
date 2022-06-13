package com.example.tugas_akhir_pam;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class setting extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView username;
    private TextView email;
    private EditText oldpass,newpass,renewpass;
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
        oldpass = findViewById(R.id.old_pass);
        newpass = findViewById(R.id.new_pass);
        renewpass = findViewById(R.id.renew_pass);
        username.setText(user.getDisplayName());
        email.setText(user.getEmail());

    }

    public void changepass(View view) {
        if(setting.getVisibility() == View.VISIBLE){
            setting.setVisibility(View.GONE);
            changepass.setVisibility(view.VISIBLE);
            if(newpass.getText().toString().equals(renewpass.getText().toString())){
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
            }
        }
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

    public void bookmark(View view) {
//        Intent intent = new Intent(homebase.this,bookmark.class);
//        startActivity(intent);
    }
}