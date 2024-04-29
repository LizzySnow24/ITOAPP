package com.example.itoapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InflarPublicacionTalleres extends RecyclerView.ViewHolder {

    TextView titulo;
    TextView tel;
    TextView instructor;
    TextView horario;
    TextView lugar;
    ImageView imagen;
    Context mContext;

    public InflarPublicacionTalleres(@NonNull View itemView) {
        super(itemView);

        titulo = itemView.findViewById(R.id.textTitulo);
        tel = itemView.findViewById(R.id.textTel);
        instructor = itemView.findViewById(R.id.instructor);
        horario = itemView.findViewById(R.id.textHorario);
        lugar = itemView.findViewById(R.id.textLugar);
        imagen = itemView.findViewById(R.id.imagen);
        mContext = itemView.getContext();
    }

    public void unir(DatosPublicacionTalleres data){
        titulo.setText(data.getTitulo());
        tel.setText(data.getTelefono());
        instructor.setText(data.getInstructor());
        horario.setText(data.getHorario());
        lugar.setText(data.getLugar());

        Glide.with(mContext)
                .load(data.getUrl_imagen())
                .into(imagen);
    }
}
