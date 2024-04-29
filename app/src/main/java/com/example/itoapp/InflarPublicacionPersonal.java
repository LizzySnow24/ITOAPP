package com.example.itoapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class InflarPublicacionPersonal extends RecyclerView.ViewHolder{
    TextView nombre;
    TextView cargo;
    TextView correo;
    TextView tel;
    TextView extension;
    TextView ubicacion;
    ImageView image;
    Context mContext;

    public InflarPublicacionPersonal(@NonNull View itemView) {
        super(itemView);

        nombre = itemView.findViewById(R.id.nombre_personal);
        cargo = itemView.findViewById(R.id.cargo);
        correo = itemView.findViewById(R.id.correo);
        tel = itemView.findViewById(R.id.telefono);
        extension = itemView.findViewById(R.id.extension);
        ubicacion = itemView.findViewById(R.id.ubicacion);
        image = itemView.findViewById(R.id.imagen_personal);
        mContext = itemView.getContext();
    }
    public void unirDatos(PublicacionDatosPersonal data){
        nombre.setText(data.getNombre());
        cargo.setText(data.getCargo());
        correo.setText(data.getCorreo());
        tel.setText(data.getTelefono());
        extension.setText(data.getExtension());
        ubicacion.setText(data.getUbicacion());

        Glide.with(mContext)
                .load(data.getUrl_imagen())
                .into(image);
    }
}
