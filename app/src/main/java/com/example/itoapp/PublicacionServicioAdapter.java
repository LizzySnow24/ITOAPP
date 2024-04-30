package com.example.itoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PublicacionServicioAdapter extends RecyclerView.Adapter<PublicacionServicioAdapter.PublicacionViewHolder>{

    private Context mContext;
    private List<PublicacionDatosServicio> mPublicacionDatosPersonal;

    public PublicacionServicioAdapter(Context context, List<PublicacionDatosServicio> datosPublicacionList) {
        mContext = context;
        mPublicacionDatosPersonal = datosPublicacionList;
    }
    @NonNull
    @Override
    public PublicacionServicioAdapter.PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cascaron_serviciosocial, parent, false);
        return new PublicacionServicioAdapter.PublicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionServicioAdapter.PublicacionViewHolder holder, int position) {
        PublicacionDatosServicio data = mPublicacionDatosPersonal.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mPublicacionDatosPersonal.size();
    }

    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        InflarPublicacionServicio mInflarPublicacion;
        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            mInflarPublicacion = new InflarPublicacionServicio(itemView);
        }

        public void bind(PublicacionDatosServicio datos) {
            mInflarPublicacion.uniendoDatos(datos);
        }
    }
}
