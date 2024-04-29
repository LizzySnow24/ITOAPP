package com.example.itoapp;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class inflar_Publicacion extends RecyclerView.ViewHolder {
    TextView fecha;
    TextView texto;
    RecyclerView imagenes;
    Context mContext;

    public inflar_Publicacion(@NonNull View itemView){
        super(itemView);

        fecha = itemView.findViewById(R.id.fecha);
        texto = itemView.findViewById(R.id.text_view2);
        imagenes = itemView.findViewById(R.id.image_recycler_view);
        mContext = itemView.getContext();
    }

    public void unir(Datos_Publicacion data){
        Date date = new Date (data.getFecha());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechatexto = sdf.format(date);


        fecha.setText(fechatexto);
        texto.setText(data.getTexto());

        // Configurar el adaptador de imágenes
        Adaptador_Publicacion adapter = new Adaptador_Publicacion(mContext, data.getImageId());
        imagenes.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        imagenes.setAdapter(adapter);
    }
}
