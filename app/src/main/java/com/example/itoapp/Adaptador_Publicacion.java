package com.example.itoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptador_Publicacion extends RecyclerView.Adapter<Adaptador_Publicacion.ImagenViewHolder> {

    private Context mContext;
    private List<String> mImagenes;

    public Adaptador_Publicacion(Context context, List<String> imagenes) {
        mContext = context;
        mImagenes = imagenes;
    }

    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.formato_imagenes, parent, false);
        return new ImagenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder holder, int position) {
        String imageUrl = mImagenes.get(position);
        Glide.with(mContext)
                .load(imageUrl)
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return mImagenes.size();
    }

    public class ImagenViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;

        public ImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.image_view);
        }
    }
}
