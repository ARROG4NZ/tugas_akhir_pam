package com.example.tugas_akhir_pam;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.viewHolder> {
    private ArrayList<location> location;
    private Context contex;
    private StorageReference storage;
    private StorageReference getimg;

    public adapter(ArrayList<location> loc, Context ctx){
        location = loc;
        contex =ctx;
        storage = FirebaseStorage.getInstance("gs://tugas-akhir-pam-7d020.appspot.com/").getReference();
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String judul = location.get(position).getNama();
        String description = location.get(position).getDescription();
        String harga = location.get(position).getHarga();
//        Uri image = location.get(position).getImage();
//        holder.imagelocation.setImageURI(image);
        holder.headline.setText(judul);
        getimg = storage.child("gambar_location/"+judul+".jpg");
       getimg.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
                Glide.with(contex).load(uri.toString()).into(holder.imagelocation);
           }
       });
        holder.bookmark.setOnClickListener(view -> {
           //disaat menekan tombol bookmark
        });
    }

    @Override
    public int getItemCount() {
        return location.size();
    }

    class viewHolder extends  RecyclerView.ViewHolder{
        private ImageView imagelocation;
        private TextView headline;
        private ImageButton bookmark;
        public viewHolder(View v){
            super(v);
            imagelocation= v.findViewById(R.id.imagelocation);
            headline = v.findViewById(R.id.headline);
            bookmark = v.findViewById(R.id.bookmark_location);
        }
    }
}
