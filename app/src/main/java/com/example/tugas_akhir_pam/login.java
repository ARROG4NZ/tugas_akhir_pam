package com.example.tugas_akhir_pam;
import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailAdress);
        password = findViewById(R.id.password);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //melakukan pengecekan apakah sebelumnya sudah melakukan login
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }

    public void sign_in(View view) {
        //melakukan pengecekan apakah form kosong
        if(!validateForm()){
            return;
        }
        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //login berhasil
                    Log.d(TAG,"login berhasil");
                    //setelah berhasil maka akan dilakukan penyimpanan user pada penyimpanan sementara
                    FirebaseUser usernow =mAuth.getCurrentUser();
                    Toast.makeText(login.this, usernow.toString(), Toast.LENGTH_SHORT).show();
                    updateUI(usernow);
                } else {
                    Log.w(TAG, "login gagal\n",task.getException());
                    Toast.makeText(login.this,"login gagal",Toast.LENGTH_SHORT).show();
                    updateUI(null);

                }
            }
        });
    }

    //mengarahkan ke halaman sign_up
    public void to_sign_up(View view) {
    Intent inten = new Intent(this, signup.class);
    startActivity(inten);
    }

    //perpindahan ke halaman utama
    public void updateUI(FirebaseUser user){
        if(user != null){
            Intent inten = new Intent(this,CRUD.class);
            startActivity(inten);
        }else {
            Toast.makeText(login.this,"Log In First",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //mengecek apakah form terisi
    public boolean validateForm(){
        boolean result = true;
        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("Required");
            result = false;
        }else{
            email.setError(null);
        }

        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Required");
            result = false;
        }else{
            password.setError(null);
        }

        return result;
    }


}