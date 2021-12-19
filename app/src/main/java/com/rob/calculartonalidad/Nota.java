package com.rob.calculartonalidad;

import java.util.Objects;

public class Nota implements Comparable<Nota>{

    private String nombre;
    private boolean isBemol;
    private boolean isSostenido;

    public Nota(String nombre, boolean isBemol, boolean isSostenido) {
        this.nombre = nombre;
        this.isBemol = isBemol;
        this.isSostenido = isSostenido;
    }

    public Nota(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isBemol() {
        return isBemol;
    }

    public void setBemol(boolean bemol) {
        isBemol = bemol;
    }

    public boolean isSostenido() {
        return isSostenido;
    }

    public void setSostenido(boolean sostenido) {
        isSostenido = sostenido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nota)) return false;
        Nota nota = (Nota) o;
        return isBemol() == nota.isBemol() &&
                isSostenido() == nota.isSostenido() &&
                Objects.equals(getNombre(), nota.getNombre());
    }

    @Override
    public String toString() {
        if(isBemol) return nombre + "♭";
        else if(isSostenido) return nombre + "♯";
        else return nombre;
    }

    @Override
    public int compareTo(Nota nota) {
        if(this.equals(nota)) {
            return 0;
        } else {
            return 1;
        }
    }
}
