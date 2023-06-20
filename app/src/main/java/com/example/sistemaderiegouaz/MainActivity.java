package com.example.sistemaderiegouaz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
     private DatabaseReference databaseReference;
     private TextView humedadTxt;
     private Button btnInicio;
     private Button btnFinal;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        humedadTxt = (TextView) findViewById(R.id.txtHumedad);
        btnInicio = findViewById(R.id.btnRegar);
        btnFinal = findViewById(R.id.btnPararRiego);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene la fecha y hora actual
                //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                //SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                //String fecha = dateFormat.format(new Date());
                //String hora = timeFormat.format(new Date());

               // Map<String, Object> hopperUpdates = new HashMap<>();
                //hopperUpdates.put("fecha", fecha);
                //hopperUpdates.put("hora", hora);

                //Datos d = new Datos();
                //d.getInicio();

                int valor = 1;
                databaseReference.child("riegos").child("riego").setValue(valor);

                Toast.makeText(MainActivity.this, "A iniciado el riego", Toast.LENGTH_SHORT).show();
            }
        });

        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtiene la fecha y hora actual
               // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                //SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                //String fecha = dateFormat.format(new Date());
                //String hora = timeFormat.format(new Date());

                //DatosFinRiego df = new DatosFinRiego();
                //df.setUid(UUID.randomUUID().toString());
                //df.setHora(hora);
                //df.setFecha(fecha);

                int valor = 0;

                databaseReference.child("riegos").child("riego").setValue(valor);

                Toast.makeText(MainActivity.this, "A parado el riego", Toast.LENGTH_SHORT).show();
            }
        });


        //Hace la llamada a la base de datos y muestra en pantalla el nivel de humedad
        databaseReference.child("humedad").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String humedad = snapshot.child("nivelHumedad").getValue().toString();
                    humedadTxt.setText(""+humedad);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void abirVentana(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}