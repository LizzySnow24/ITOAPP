package com.example.itoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PublicacionPersonalAdapter extends RecyclerView.Adapter<PublicacionPersonalAdapter.PublicacionViewHolder>{
    private Context mContext;
    private List<PublicacionDatosPersonal> mPublicacionDatosPersonal;

    public PublicacionPersonalAdapter(Context context, List<PublicacionDatosPersonal> datosPublicacionList) {
        mContext = context;
        mPublicacionDatosPersonal = datosPublicacionList;
    }
    @NonNull
    @Override
    public PublicacionPersonalAdapter.PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cascaron_card_personal, parent, false);
        return new PublicacionPersonalAdapter.PublicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionPersonalAdapter.PublicacionViewHolder holder, int position) {
        PublicacionDatosPersonal data = mPublicacionDatosPersonal.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mPublicacionDatosPersonal.size();
    }

    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        InflarPublicacionPersonal mInflarPublicacion;
        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            mInflarPublicacion = new InflarPublicacionPersonal(itemView);
        }

        public void bind(PublicacionDatosPersonal datos) {
            mInflarPublicacion.unirDatos(datos);
        }
    }
}
