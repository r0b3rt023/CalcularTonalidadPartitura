package com.rob.calculartonalidad;

import java.util.ArrayList;

public class Tonalidad {

    private String nombreTonalidad;
    private ArrayList<Nota> notasImportantes;
    private ArrayList<Nota> notasReposo;

    public Tonalidad(String nombreTonalidad, ArrayList<Nota> notasImportantes, ArrayList<Nota> notasReposo) {
        this.nombreTonalidad = nombreTonalidad;
        this.notasImportantes = notasImportantes;
        this.notasReposo = notasReposo;
    }

    public String getNombreTonalidad() {
        return nombreTonalidad;
    }

    public void setNombreTonalidad(String nombreTonalidad) {
        this.nombreTonalidad = nombreTonalidad;
    }

    public ArrayList<Nota> getNotasImportantes() {
        return notasImportantes;
    }

    public void setNotasImportantes(ArrayList<Nota> notasImportantes) {
        this.notasImportantes = notasImportantes;
    }

    public ArrayList<Nota> getNotasReposo() {
        return notasReposo;
    }

    public void setNotasReposo(ArrayList<Nota> notasReposo) {
        this.notasReposo = notasReposo;
    }

    @Override
    public String toString() {
        return nombreTonalidad;
    }

}
