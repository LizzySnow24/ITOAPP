package com.example.itoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class noticiasAdapter  extends RecyclerView.Adapter<noticiasAdapter.PublicacionViewHolder> {
    private Context mContext;
    private List<Datos_Publicacion> mDatosPublicacionList;

    public noticiasAdapter(Context context, List<Datos_Publicacion> datosPublicacionList) {
        mContext = context;
        mDatosPublicacionList = datosPublicacionList;
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_publicaciones, parent, false);
        return new PublicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position) {
        Datos_Publicacion data = mDatosPublicacionList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mDatosPublicacionList.size();
    }

    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        inflar_Publicacion mInflarPublicacion;

        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            mInflarPublicacion = new inflar_Publicacion(itemView);
        }

        public void bind(Datos_Publicacion data) {
            mInflarPublicacion.unir(data);
        }
    }
}
