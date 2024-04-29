package com.example.itoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class Activity_PlanEstudios extends AppCompatActivity {

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    private static String rol;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private DocumentReference documentoRef;
    String imagenurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_estudios);

        FloatingActionButton boton_editar = findViewById(R.id.boton_editar);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar.setVisibility(View.VISIBLE);
            //ABRIR VENTANA EDITAR
        } else {
            boton_editar.setVisibility(View.GONE);
        }
        // Obtener referencia al Spinner
        Spinner lista_Carreras = findViewById(R.id.lista_carreras);

        // Obtener referencia al Spinner
        Spinner materias_carrera = findViewById(R.id.materias_carrera);

        // Agregar un listener al Spinner para detectar cambios de selección
        lista_Carreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Llamar a MandarDatos() cuando se selecciona un elemento en el Spinner
                String CarreraSeleccionada = lista_Carreras.getSelectedItem().toString();
                String SemestreSeleccionado = lista_Carreras.getSelectedItem().toString();
                MandarDatos(CarreraSeleccionada,SemestreSeleccionado);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String[] datosCarreras = new String[]{"Seleccionar Carrera","Sistemas Computacionales", "Electromecánica",
                "Industrial", "Contador Público", "Gestión Empresarial", "Logística"};
        ArrayAdapter adapterCarreras = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, datosCarreras);
        adapterCarreras.setDropDownViewResource(R.layout.spinner_item);
        lista_Carreras.setAdapter(adapterCarreras);

        String[] materiasCarrera = materiasCarrera = new String[]{"Seleccionar Semestre","1er. Semestre", "2do. Semestre",
                "3er. Semestre", "4to. Semestre", "5to. Semestre", "6to. Semestre",
                "7mo. Semestre", "8vo. Semestre"};
        ArrayAdapter adapterMaterias = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, materiasCarrera);
        adapterMaterias.setDropDownViewResource(R.layout.spinner_item);
        materias_carrera.setAdapter(adapterMaterias);
    }

    public void MandarDatos(String CarreraSeleccionada,String materias_carrera){
        ImageView imagen_reticula = findViewById(R.id.reticula_image);


        if(CarreraSeleccionada.equals("Seleccionar semestre")) {
            Toast.makeText(this, "Por favor, seleccione un semestre para continuar", Toast.LENGTH_SHORT).show();
            return;
        }
        StorageReference imagenRef=null;

        switch(CarreraSeleccionada){
            case "Sistemas Computacionales":
                Toast.makeText(Activity_PlanEstudios.this, "entro", Toast.LENGTH_SHORT).show();
                //referencia a storage
                imagenRef = storageRef.child("imagenes_reticula/reticula_sistemas" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_sistemas");

                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":

                        break;
                    case "2do. Semestre":
                        break;
                    case "3er. Semestre":
                        break;
                    case "4to. Semestre":
                        break;
                    case "5to. Semestre":
                        break;
                    case "6to. Semestre":
                        break;
                    case "7mo. Semestre":
                        break;
                    case "8vo. Semestre":
                        break;
                }
                break;
            case "Electromecánica":
                imagenRef = storageRef.child("imagenes_reticula/reticula_electro" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_electro");
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        break;
                    case "2do. Semestre":
                        break;
                    case "3er. Semestre":
                        break;
                    case "4to. Semestre":
                        break;
                    case "5to. Semestre":
                        break;
                    case "6to. Semestre":
                        break;
                    case "7mo. Semestre":
                        break;
                    case "8vo. Semestre":
                        break;
                }
                break;
            case "Industrial":
                imagenRef = storageRef.child("imagenes_reticula/reticula_industrial" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_industrial");
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        break;
                    case "2do. Semestre":
                        break;
                    case "3er. Semestre":
                        break;
                    case "4to. Semestre":
                        break;
                    case "5to. Semestre":
                        break;
                    case "6to. Semestre":
                        break;
                    case "7mo. Semestre":
                        break;
                    case "8vo. Semestre":
                        break;
                }
                break;
            case "Contador Público":
                imagenRef = storageRef.child("imagenes_reticula/reticula_contador" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_contador");
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        break;
                    case "2do. Semestre":
                        break;
                    case "3er. Semestre":
                        break;
                    case "4to. Semestre":
                        break;
                    case "5to. Semestre":
                        break;
                    case "6to. Semestre":
                        break;
                    case "7mo. Semestre":
                        break;
                    case "8vo. Semestre":
                        break;
                }
                break;
            case "Gestión Empresarial":
                imagenRef = storageRef.child("imagenes_reticula/reticula_gestion" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_gestion");
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        break;
                    case "2do. Semestre":
                        break;
                    case "3er. Semestre":
                        break;
                    case "4to. Semestre":
                        break;
                    case "5to. Semestre":
                        break;
                    case "6to. Semestre":
                        break;
                    case "7mo. Semestre":
                        break;
                    case "8vo. Semestre":
                        break;
                }
                break;
            case "Logística":
                imagenRef = storageRef.child("imagenes_reticula/reticula_logistica" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_logistica");
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        break;
                    case "2do. Semestre":
                        break;
                    case "3er. Semestre":
                        break;
                    case "4to. Semestre":
                        break;
                    case "5to. Semestre":
                        break;
                    case "6to. Semestre":
                        break;
                    case "7mo. Semestre":
                        break;
                    case "8vo. Semestre":
                        break;
                }
                break;
        }


        if (imagenRef != null) {
            documentoRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        imagenurl = documentSnapshot.getString("image");
                        // Carga la imagen
                        Glide.with(getApplicationContext())
                                .load(imagenurl)
                                .into(imagen_reticula);
                        // imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imagen_reticula.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mostrarImagenAgrandada(imagenurl);
                            }
                        });

                    } else {
                        Log.d(TAG, "El documento no existe");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Error al obtener el documento", e);
                }
            });
        }
    }
    private void mostrarImagenAgrandada(String imagenUrl) {
// Crear un nuevo diálogo
        Dialog dialog = new Dialog(this);
        // Inflar el layout del diálogo que contiene la imagen que deseas agrandar
        dialog.setContentView(R.layout.imagen_agrandada_layout);
        // Obtener referencia a la ImageView en el layout del diálogo
        ImageView imageView = dialog.findViewById(R.id.imagen_agrandada);

        // Configurar la imagen en la ImageView para que se pueda agrandar al hacer clic en ella
        // Cargar la imagen en la ImageView
        Glide.with(this)
                .load(imagenUrl)
                .into(imageView);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener(imageView));

        gestureDetector = new GestureDetector(this, new GestureListener(imageView));

        // Combinar eventos de toque para la ImageView
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Pasar eventos de toque a ambos detectores de gestos
                scaleGestureDetector.onTouchEvent(event);
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        // Configurar el diálogo para que se pueda cerrar haciendo clic fuera de él
        dialog.setCancelable(true);


        // Configurar el diálogo para que se pueda cerrar haciendo clic fuera de él
        dialog.setCancelable(true);

        // Mostrar el diálogo
        dialog.show();
    }
}