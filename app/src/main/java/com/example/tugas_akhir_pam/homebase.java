package com.example.tugas_akhir_pam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homebase extends AppCompatActivity {
    private DatabaseReference database;
    private RecyclerView rec;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<location> locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homebase);

        rec = findViewById(R.id.recycler);
        rec.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        rec.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance("https://tugas-akhir-pam-7d020-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        database.child("locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locations = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    location loc = data.getValue(location.class);
                    locations.add(loc);
                }
                adapter = new adapter(locations,homebase.this);
                rec.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(homebase.this,error.getDetails()+" "+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static Intent getActIntent(Activity activity){
        return new Intent(activity,homebase.class);
    }

    public void setting(View view) {
        Intent intent = new Intent(homebase.this,setting.class);
        startActivity(intent);
    }


    public void home(View view) {
        Intent intent = new Intent(homebase.this,homebase.class);
        startActivity(intent);
    }

    public void bookmark(View view) {
//        Intent intent = new Intent(homebase.this,bookmark.class);
//        startActivity(intent);
    }
}