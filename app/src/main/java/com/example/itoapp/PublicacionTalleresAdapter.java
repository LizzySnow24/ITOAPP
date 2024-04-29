package com.example.itoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PublicacionTalleresAdapter extends RecyclerView.Adapter<PublicacionTalleresAdapter.PublicacionViewHolder> {
    private Context mContext;
    private List<DatosPublicacionTalleres> mDatosPublicacionTalleres;

    public PublicacionTalleresAdapter(Context context, List<DatosPublicacionTalleres> datosPublicacionList) {
        mContext = context;
        mDatosPublicacionTalleres = datosPublicacionList;
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cascaron_taller_civico, parent, false);
        return new PublicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position) {
        DatosPublicacionTalleres data = mDatosPublicacionTalleres.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mDatosPublicacionTalleres.size();
    }

    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        InflarPublicacionTalleres mInflarPublicacion;

        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            mInflarPublicacion = new InflarPublicacionTalleres(itemView);
        }

        public void bind(DatosPublicacionTalleres datos) {
            mInflarPublicacion.unir(datos);
        }
    }
}