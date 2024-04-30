package com.example.itoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Fragment_Optiones extends Fragment {
    private AlertDialog alertDialog;

    public Fragment_Optiones() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__optiones, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout Opcion_CLE = view.findViewById(R.id.Opcion_CLE);
        LinearLayout Opcion_SII = view.findViewById(R.id.Opcion_SII);
        LinearLayout Opcion_PlanEstudios = view.findViewById(R.id.Opcion_PlanEstudios);
        LinearLayout Opcion_Talleres = view.findViewById(R.id.Opcion_Talleres);
        LinearLayout Opcion_Personal = view.findViewById(R.id.Opcion_Personal);
        LinearLayout calendarioEscolar=view.findViewById(R.id.calendarioEscolar);
        LinearLayout serviciosocial=view.findViewById(R.id.serviciosocial);

        Opcion_CLE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://sites.google.com/view/itocle/p%C3%A1gina-principal"; // URL de CLE
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        Opcion_SII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://148.233.77.139/sistema/"; // URL de CLE
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        Opcion_PlanEstudios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Activity_PlanEstudios.class);
                startActivity(i);
            }
        });
        Opcion_Talleres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Activity_Menu_Talleres.class);
                startActivity(i);
            }
        });
        Opcion_Personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Activity_Personal.class);
                startActivity(i);
            }
        });
        calendarioEscolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCalen();
            }
        });
        serviciosocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Activity_ServicioSocial.class);
                startActivity(i);
            }
        });
    }
    public void abrirCalen(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflar el layout del cuadro de diálogo
        View dialogView = getLayoutInflater().inflate(R.layout.cascaron_calendario, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Descargar PDF", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Aquí debes agregar la lógica para descargar el PDF
                // Puedes utilizar la misma lógica que usaste en el OnClickListener de Opcion_Personal
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://ocotlan.tecnm.mx/img/slider/Calendario%20Interno%20Ene%20-%20Jun%202024.pdf");
                intent.setDataAndType(uri, "application/pdf");
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cerrar el cuadro de diálogo
                dialog.cancel();
            }
        });

        // Crear y mostrar el cuadro de diálogo
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        // Personaliza el estilo de los botones
        // Por ejemplo, cambia el color del texto a rojo
        positiveButton.setTextColor(Color.rgb(98,70,17));
        negativeButton.setTextColor(Color.rgb(98,70,17));

        // También puedes cambiar el tamaño del texto, tipo de letra, etc.
        positiveButton.setTextSize(18); // Cambia el tamaño del texto
        negativeButton.setTextSize(18); // Cambia el tamaño del texto
    }

}