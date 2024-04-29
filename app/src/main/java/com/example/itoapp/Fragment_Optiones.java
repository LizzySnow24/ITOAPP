package com.example.itoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Fragment_Optiones extends Fragment {

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
    }
}