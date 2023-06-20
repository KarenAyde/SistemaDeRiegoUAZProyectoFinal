package com.example.sistemaderiegouaz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity3 extends AppCompatActivity {
    private DatabaseReference databaseReference;
    TextView Hora;
    TextView Fecha;
    Button Guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getSupportActionBar().setTitle("Programar Riego");

        Hora = findViewById(R.id.txtEstablecerHora);
        Fecha = findViewById(R.id.txtFecha);
        Guardar = findViewById(R.id.btnGuardar);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tFecha = Fecha.getText().toString();
                String tHora = Hora.getText().toString();

                // Validar si las cajas de texto contienen información
                if (tFecha.isEmpty() || tFecha.equals("Selecciona la fecha") || tHora.isEmpty() || tHora.equals("Selecciona la hora")) {
                    Toast.makeText(MainActivity3.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                    Alarma a = new Alarma();
                    a.setId(UUID.randomUUID().toString());
                    a.setFecha(tFecha);
                    a.setHora(tHora);

                    databaseReference.child("alarma").child(a.getId()).setValue(a);

                    Toast.makeText(MainActivity3.this, "Alarma guardada", Toast.LENGTH_SHORT).show();

                    finish();

                    Intent intent = new Intent(MainActivity3.this,MainActivity2.class);
                    startActivity(intent);
            }

        });

    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);
        //Toast.makeText(this, "Date: " + dateMessage, Toast.LENGTH_SHORT).show();
        Fecha.setText(dateMessage);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void processTimePickerResult(int hour, int minute) {
        String hour_string = Integer.toString(hour);
        String minute_string = Integer.toString(minute);
        String dateMessage = (hour_string+":"+minute_string );
        Hora.setText(dateMessage);
    }

}