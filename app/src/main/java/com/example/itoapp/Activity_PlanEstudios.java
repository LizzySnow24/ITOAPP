package com.example.itoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.UUID;

public class Activity_PlanEstudios extends AppCompatActivity {

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    Spinner materias_carreras;
    private static String rol;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private DocumentReference documentoRef;
    private DocumentReference documentoRef2;
    private TextView textmateria1;
    private TextView textmateria2;
    private TextView textmateria3;
    private TextView textmateria4;
    private TextView textmateria5;
    private TextView textmateria6;
    private TextView textmateria7;
    String imagenurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_estudios);

        FloatingActionButton boton_editar = findViewById(R.id.boton_editar);

        textmateria1 = findViewById(R.id.materia1);
        textmateria2= findViewById(R.id.materia2);
        textmateria3 = findViewById(R.id.materia3);
        textmateria4 = findViewById(R.id.materia4);
        textmateria5 = findViewById(R.id.materia5);
        textmateria6 = findViewById(R.id.materia6);
        textmateria7 = findViewById(R.id.materia7);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar.setVisibility(View.VISIBLE);
            //ABRIR VENTANA EDITAR
        } else {
            boton_editar.setVisibility(View.GONE);
        }

        boton_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Obtener referencia al Spinner
        Spinner lista_Carreras = findViewById(R.id.lista_carreras);

        // Obtener referencia al Spinner
        materias_carreras = findViewById(R.id.materias_carrera);

        // Agregar un listener al Spinner para detectar cambios de selección
        // Listener para el Spinner lista_Carreras
        lista_Carreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener la carrera seleccionada
                String CarreraSeleccionada = lista_Carreras.getSelectedItem().toString();
                // Obtener el semestre actualmente seleccionado
                String SemestreSeleccionado = materias_carreras.getSelectedItem().toString();
                // Llamar a MandarDatos con la carrera seleccionada y el semestre actualmente seleccionado
                MandarDatos(CarreraSeleccionada, SemestreSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones si no se selecciona nada
            }
        });

// Listener para el Spinner materias_carreras
        materias_carreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el semestre seleccionado
                String SemestreSeleccionado = materias_carreras.getSelectedItem().toString();
                // Obtener la carrera actualmente seleccionada
                String CarreraSeleccionada = lista_Carreras.getSelectedItem().toString();
                // Llamar a MandarDatos con el semestre seleccionado y la carrera actualmente seleccionada
                MandarDatos(CarreraSeleccionada, SemestreSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones si no se selecciona nada
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
        materias_carreras.setAdapter(adapterMaterias);
    }

    public void MandarDatos(String CarreraSeleccionada,String materias_carrera){
        ImageView imagen_reticula = findViewById(R.id.reticula_image);

        StorageReference imagenRef=null;

        switch(CarreraSeleccionada){
            case "Sistemas Computacionales":
                //referencia a storage
                imagenRef = storageRef.child("imagenes_reticula/reticula_sistemas" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_sistemas");

                materias_carreras.setVisibility(View.VISIBLE);

                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        Llenar("1er. Semestre", "sistemas");
                        break;
                    case "2do. Semestre":
                        Llenar("2do. Semestre","sistemas");
                        break;
                    case "3er. Semestre":
                        Llenar("3er. Semestre","sistemas");
                        break;
                    case "4to. Semestre":
                        Llenar("4to. Semestre","sistemas");
                        break;
                    case "5to. Semestre":
                        Llenar("5to. Semestre","sistemas");
                        break;
                    case "6to. Semestre":
                        Llenar("6to. Semestre","sistemas");
                        break;
                    case "7mo. Semestre":
                        Llenar("7mo. Semestre","sistemas");
                        break;
                    case "8vo. Semestre":
                        Llenar("8vo. Semestre","sistemas");
                        break;
                }
                break;
            case "Electromecánica":
                imagenRef = storageRef.child("imagenes_reticula/reticula_electro" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_electro");
                materias_carreras.setVisibility(View.VISIBLE);
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        Llenar("1er. Semestre", "electro");
                        break;
                    case "2do. Semestre":
                        Llenar("2do. Semestre", "electro");
                        break;
                    case "3er. Semestre":
                        Llenar("3er. Semestre", "electro");
                        break;
                    case "4to. Semestre":
                        Llenar("4to. Semestre", "electro");
                        break;
                    case "5to. Semestre":
                        Llenar("5to. Semestre", "electro");
                        break;
                    case "6to. Semestre":
                        Llenar("6to. Semestre", "electro");
                        break;
                    case "7mo. Semestre":
                        Llenar("7mo. Semestre", "electro");
                        break;
                    case "8vo. Semestre":
                        Llenar("8vo. Semestre", "electro");
                        break;
                }
                break;
            case "Industrial":
                imagenRef = storageRef.child("imagenes_reticula/reticula_industrial" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_industrial");
                materias_carreras.setVisibility(View.VISIBLE);
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        Llenar("1er. Semestre", "industrial");
                        break;
                    case "2do. Semestre":
                        Llenar("2do. Semestre", "industrial");
                        break;
                    case "3er. Semestre":
                        Llenar("3er. Semestre", "industrial");
                        break;
                    case "4to. Semestre":
                        Llenar("4to. Semestre", "industrial");
                        break;
                    case "5to. Semestre":
                        Llenar("5to. Semestre", "industrial");
                        break;
                    case "6to. Semestre":
                        Llenar("6to. Semestre", "industrial");
                        break;
                    case "7mo. Semestre":
                        Llenar("7mo. Semestre", "industrial");
                        break;
                    case "8vo. Semestre":
                        Llenar("8vo. Semestre", "industrial");
                        break;
                }
                break;
            case "Contador Público":
                imagenRef = storageRef.child("imagenes_reticula/reticula_contador" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_contador");
                materias_carreras.setVisibility(View.VISIBLE);
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        Llenar("1er. Semestre", "contador");
                        break;
                    case "2do. Semestre":
                        Llenar("2do. Semestre", "contador");
                        break;
                    case "3er. Semestre":
                        Llenar("3er. Semestre", "contador");
                        break;
                    case "4to. Semestre":
                        Llenar("4to. Semestre", "contador");
                        break;
                    case "5to. Semestre":
                        Llenar("5to. Semestre", "contador");
                        break;
                    case "6to. Semestre":
                        Llenar("6to. Semestre", "contador");
                        break;
                    case "7mo. Semestre":
                        Llenar("7mo. Semestre", "contador");
                        break;
                    case "8vo. Semestre":
                        Llenar("8vo. Semestre", "contador");
                        break;
                }
                break;
            case "Gestión Empresarial":
                imagenRef = storageRef.child("imagenes_reticula/reticula_gestion" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_gestion");
                materias_carreras.setVisibility(View.VISIBLE);
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        Llenar("1er. Semestre", "gestion");
                        break;
                    case "2do. Semestre":
                        Llenar("2do. Semestre", "gestion");
                        break;
                    case "3er. Semestre":
                        Llenar("3er. Semestre", "gestion");
                        break;
                    case "4to. Semestre":
                        Llenar("4to. Semestre", "gestion");
                        break;
                    case "5to. Semestre":
                        Llenar("5to. Semestre", "gestion");
                        break;
                    case "6to. Semestre":
                        Llenar("6to. Semestre", "gestion");
                        break;
                    case "7mo. Semestre":
                        Llenar("7mo. Semestre", "gestion");
                        break;
                    case "8vo. Semestre":
                        Llenar("8vo. Semestre", "gestion");
                        break;
                }
                break;
            case "Logística":
                imagenRef = storageRef.child("imagenes_reticula/reticula_logistica" + UUID.randomUUID().toString());
                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                documentoRef = db.collection("Plan_Estudios")
                        .document("reticula_logistica");
                materias_carreras.setVisibility(View.VISIBLE);
                //materias carrera de sistemas
                switch(materias_carrera){
                    case "1er. Semestre":
                        Llenar("1er. Semestre", "logistica");
                        break;
                    case "2do. Semestre":
                        Llenar("2do. Semestre", "logistica");
                        break;
                    case "3er. Semestre":
                        Llenar("3er. Semestre", "logistica");
                        break;
                    case "4to. Semestre":
                        Llenar("4to. Semestre", "logistica");
                        break;
                    case "5to. Semestre":
                        Llenar("5to. Semestre", "logistica");
                        break;
                    case "6to. Semestre":
                        Llenar("6to. Semestre", "logistica");
                        break;
                    case "7mo. Semestre":
                        Llenar("7mo. Semestre", "logistica");
                        break;
                    case "8vo. Semestre":
                        Llenar("8vo. Semestre", "logistica");
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

    public void Llenar(String semestre, String carrera){
        documentoRef2 =  db.collection("Plan_Estudios").document("reticula_"+carrera)
                .collection("semestre").document(semestre);
        documentoRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String materia1 = documentSnapshot.getString("materia1");
                    String materia2 = documentSnapshot.getString("materia2");
                    String materia3 = documentSnapshot.getString("materia3");
                    String materia4 = documentSnapshot.getString("materia4");
                    String materia5 = documentSnapshot.getString("materia5");

                    String enlace1 = documentSnapshot.getString("enlace1");
                    String enlace2 = documentSnapshot.getString("enlace2");
                    String enlace3 = documentSnapshot.getString("enlace3");
                    String enlace4 = documentSnapshot.getString("enlace4");
                    String enlace5 = documentSnapshot.getString("enlace5");

                    //verificamos que hay un campo materia 6 y 7, si no hay setteamos una cadena vacia ("")
                    String materia6 = documentSnapshot.contains("materia6") ? documentSnapshot.getString("materia6") : "";
                    String materia7 = documentSnapshot.contains("materia7") ? documentSnapshot.getString("materia7") : "";

                    //verificamos que hay un campo materia 6 y 7, si no hay setteamos una cadena vacia ("")
                    String enlace6 = documentSnapshot.contains("enlace6") ? documentSnapshot.getString("enlace6") : "";
                    String enlace7 = documentSnapshot.contains("enlace7") ? documentSnapshot.getString("enlace7") : "";


                    textmateria1.setText(materia1);
                    textmateria2.setText(materia2);
                    textmateria3.setText(materia3);
                    textmateria4.setText(materia4);
                    textmateria5.setText(materia5);
                    textmateria6.setText(materia6);
                    textmateria7.setText(materia7);

                        // Mostrar un mensaje si el documento no existe
                        //Toast.makeText(Activity_PlanEstudios.this, "Materia1"+materia1, Toast.LENGTH_SHORT).show();

                    Clic(textmateria1,enlace1);
                    Clic(textmateria2,enlace2);
                    Clic(textmateria3,enlace3);
                    Clic(textmateria4,enlace4);
                    Clic(textmateria5,enlace5);
                    Clic(textmateria6,enlace6);
                    Clic(textmateria7,enlace7);
                }
            }
        });
    }
    private void abrirEnlacePDF(String enlace) {
        // Crear un Intent para abrir el navegador web con el enlace dado
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(enlace));
        Uri fileUri = Uri.parse(enlace);
        intent.setDataAndType(fileUri, "application/pdf");
        startActivity(intent);
    }
    private void Clic(TextView texto, String enlace){
        texto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEnlacePDF(enlace);
            }
        });
    }
}