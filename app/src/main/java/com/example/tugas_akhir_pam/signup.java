package com.example.tugas_akhir_pam;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {
    private FirebaseAuth mAUth;
    private FirebaseUser user;
    private EditText username,email,password,repeat_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAUth = FirebaseAuth.getInstance();
        username = findViewById(R.id.sign_up_username);
        email = findViewById(R.id.sign_up_email);
        password = findViewById(R.id.sign_up_password);
        repeat_password = findViewById(R.id.sign_up_repeat_password);
    }

    public void sign_up(View view) {
        mAUth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "membuat user dengan email dan password berhasil");
                    user = mAUth.getCurrentUser();
                    Toast.makeText(signup.this, user.toString(), Toast.LENGTH_SHORT).show();
                    updateUI(user);
                }else{
                    Log.w(TAG, "gagal membuat user");
                    Toast.makeText(signup.this, task.getException().toString(),Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    //mengecek apakah form terisi
    public boolean validateForm(){
        boolean result = true;
        if(TextUtils.isEmpty(username.getText().toString())){
            username.setError("Required");
            result = false;
        }
        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("Required");
            result = false;
        }
        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Required");
            result = false;
        }
        if (TextUtils.isEmpty(repeat_password.getText().toString())){
            repeat_password.setError("Required");
            result = false;
        }else{
            if(!repeat_password.getText().toString().equals(password.getText().toString())){
                repeat_password.setError("Password berbeda");
                result = false;
            }
        }

        return result;
    }

    //mengubah tampilan
    public void updateUI(FirebaseUser userUI){
        if(userUI != null){
            Intent inten = new Intent(signup.this,login.class);
            startActivity(inten);
        } else {
            Toast.makeText(signup.this,"Buat akun terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
        }
    }
}