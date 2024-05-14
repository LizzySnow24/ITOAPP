package com.example.itoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class noticiasAdapter  extends RecyclerView.Adapter<noticiasAdapter.PublicacionViewHolder> {
    private Context mContext;
    private List<Datos_Publicacion> mDatosPublicacionList;
    private OnDeleteClickListener mDeleteCLickListener;
    ImageButton boton_delete;

    public noticiasAdapter(Context context, List<Datos_Publicacion> datosPublicacionList) {
        mContext = context;
        mDatosPublicacionList = datosPublicacionList;
    }
    public void setOnClickListener(OnDeleteClickListener listener){
        mDeleteCLickListener = listener;
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_publicaciones, parent, false);
        PublicacionViewHolder viewHolder = new PublicacionViewHolder(view);
        boton_delete = view.findViewById(R.id.boton_delete);

        String rol = Menu.getRol();
        if (rol != null && rol.equals("admin")) {
            boton_delete.setVisibility(view.VISIBLE);
        }

        // Asigna el oyente de clics definido en el adaptador al botón de eliminar
        boton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && mDeleteCLickListener != null) {
                    String publicacionId = mDatosPublicacionList.get(position).getID();
                    String semestre = mDatosPublicacionList.get(position).getSemestre();
                    Log.d("¡¡¡ID!!! ", publicacionId);
                    showDeleteConfirmationDialog(publicacionId, semestre);
                }
            }
        });

        return viewHolder;
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
    public interface OnDeleteClickListener{
        void onDeleteClick(String publicacionId, String semestre);
    }

    private void showDeleteConfirmationDialog(String publicacionId, String semestre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Eliminar Publicación");
        builder.setMessage("¿Estás seguro de que quieres eliminar esta publicación?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Llama al método onDeleteClick con los datos
                mDeleteCLickListener.onDeleteClick(publicacionId, semestre);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
