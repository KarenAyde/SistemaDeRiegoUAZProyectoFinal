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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private ListView listaRiego;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle("Fechas de riego");

        DatabaseReference datoRef = FirebaseDatabase.getInstance().getReference().child("riegos").push();


        listaRiego = findViewById(R.id.lblListaRiego);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listaRiego.setAdapter(adapter);

        // Obtén la referencia a la base de datos de Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("riegos");

        // Agrega el listener para obtener los datos de Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpia la lista actual
                dataList.clear();

                // Itera sobre los datos recibidos y agrega cada elemento a la lista
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    // Extraer los valores del mapa y agregarlos a la lista
                    String dato = (String) dataMap.get("fecha");
                    String dato2 = (String) dataMap.get("hora");

                    String datos = dato + " " +dato2;

                    dataList.add(datos);
                }

                // Notifica al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja cualquier error de Firebase aquí
            }
        });



        listaRiego.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    private void eliminarElemento(int posicion) {


        String elementoId = "";

        if (posicion >= 0 && posicion < dataList.size()) {
            // Obtén el ID del elemento seleccionado
            String elementoCompleto = dataList.get(posicion);

            // Separa el ID del elemento de los datos mostrados
            String[] partes = elementoCompleto.split(" ");
            elementoId = partes[0];
        }

        // Verifica que el ID del elemento no esté vacío
        if (!elementoId.isEmpty()) {
            // Obtén la referencia a la base de datos de Firebase
           // databaseReference = FirebaseDatabase.getInstance().getReference().child("riegos");
            // Elimina el elemento de Firebase
            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Actualiza el adaptador o la lista de datos local para reflejar el cambio
                            dataList.remove(posicion);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity2.this, "Elemento eliminado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Maneja el error según tus necesidades
                            Toast.makeText(MainActivity2.this, "Error al eliminar el elemento", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }
}