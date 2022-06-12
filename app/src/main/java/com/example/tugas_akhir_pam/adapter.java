package com.example.tugas_akhir_pam;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.viewHolder> {
    private ArrayList<location> location;
    private Context contex;
    public adapter(ArrayList<location> loc, Context ctx){
        location = loc;
        contex =ctx;
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
        holder.bookmark.setOnClickListener(view -> {
            //saat menekan tombol bookmark
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
