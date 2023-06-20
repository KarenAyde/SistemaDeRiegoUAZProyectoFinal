package com.example.sistemaderiegouaz;

public class Alarma {
    private String id;
    private String hora;
    private String fecha;

    public Alarma() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String toString() {
        return "Datos{" +
                "fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
