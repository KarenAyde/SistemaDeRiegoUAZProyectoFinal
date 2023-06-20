package com.example.sistemaderiegouaz;

public class Datos {
    private String inicio;

    public Datos() {

    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    @Override
    public String toString() {
        return "Datos{" +
                "inicio='" + inicio + '\'' +
                '}';
    }
}
