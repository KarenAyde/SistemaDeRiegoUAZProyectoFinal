package com.example.sistemaderiegouaz;

public class DatosFinRiego {

    private String uid;


    private String fecha;
    private String hora;

    public DatosFinRiego() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Datos{" +
                "fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
