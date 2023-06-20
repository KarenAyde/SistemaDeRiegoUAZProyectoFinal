package com.example.sistemaderiegouaz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private ListView listaRiego;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
    private DatabaseReference databaseReference;
    Alarma aSelc;


    private List<Alarma> lAlarma = new ArrayList<Alarma>();
    ArrayAdapter<Alarma> arrayAdapterAlarma;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle("Alarmas de riego");

        DatabaseReference datoRef = FirebaseDatabase.getInstance().getReference().child("alarma").push();


        listaRiego = findViewById(R.id.lblListaRiego);

        //  referencia a la base de datos de Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("alarma");

        // Agrega el listener para obtener los datos de Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpia la lista actual
                lAlarma.clear();

                // Itera sobre los datos recibidos y agrega cada elemento a la lista
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                    Alarma d = snapshot.getValue(Alarma.class);
                    // Extraer los valores del mapa y agregarlos a la lista
                   // String dato = (String) dataMap.get("fecha");
                    //String dato2 = (String) dataMap.get("hora");
                    //String datos = dato + " " +dato2;

                    lAlarma.add(d);
                    //dataList.add(datos);

                    arrayAdapterAlarma = new ArrayAdapter<Alarma>(MainActivity2.this, android.R.layout.simple_list_item_1, lAlarma);
                    listaRiego.setAdapter(arrayAdapterAlarma);
                }

                // Notifica al adaptador que los datos han cambiado
               // adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja cualquier error de Firebase aquí
            }
        });



        listaRiego.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aSelc = (Alarma) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("Eliminar elemento");
                builder.setMessage("¿Estás seguro de que deseas eliminar este elemento?");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarElemento(position);
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
            }
        });

    }

    public void abrirVentana3(View view){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);

    }

    private void eliminarElemento(int posicion) {

        Alarma a = new Alarma();
        a.setId(aSelc.getId());
        //databaseReference.child("riegos").child(d.getUid()).removeValue();


        //Verifica que el ID del elemento no esté vacío
       if (!aSelc.getId().isEmpty()) {
            //Obtén la referencia a la base de datos de Firebase
            databaseReference = FirebaseDatabase.getInstance().getReference().child("alarma");
            // Elimina el elemento de Firebase
            databaseReference.child(a.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Actualiza el adaptador o la lista de datos local para reflejar el cambio
                            if(dataList != null) {
                                dataList.remove(posicion);
                                adapter.notifyDataSetChanged();
                            }
                            Toast.makeText(MainActivity2.this, "Elemento eliminado", Toast.LENGTH_SHORT).show();
                       }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Maneja el error
                            Toast.makeText(MainActivity2.this, "Error al eliminar el elemento", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }



}