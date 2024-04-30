package com.example.itoapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class InflarPublicacionServicio extends RecyclerView.ViewHolder{

    TextView nombre;
    TextView departamento;
    TextView correo;
    TextView telefono;
    TextView requisitos;
    ImageView image;
    Context mContext;

    public InflarPublicacionServicio(@NonNull View itemView) {
        super(itemView);

        nombre = itemView.findViewById(R.id.nombre_encargado);
        correo = itemView.findViewById(R.id.correo_servicio);
        telefono = itemView.findViewById(R.id.telefono_servicio);
        departamento = itemView.findViewById(R.id.departamento);
        requisitos = itemView.findViewById(R.id.requisitos);
        image = itemView.findViewById(R.id.imagen_servicio);
        mContext = itemView.getContext();
    }
    public void uniendoDatos(PublicacionDatosServicio data){
        nombre.setText(data.getNombre());
        correo.setText(data.getCorreo());
        telefono.setText(data.getTelefono());
        requisitos.setText(data.getRequisitos());
        departamento.setText(data.getDepartamento());


        Glide.with(mContext)
                .load(data.getUrl_imagen())
                .into(image);
    }
}
