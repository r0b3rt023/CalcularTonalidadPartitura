package com.rob.calculartonalidad;

import java.util.Objects;

public class Armadura implements Comparable<Armadura> {

    private String nombreArmadura;

    private Tonalidad tonalidadUno;
    private Tonalidad tonalidadDos;

    private boolean isBemol;
    private boolean isSostenido;

    public Armadura(String nombreArmadura, Tonalidad tonalidadUno, Tonalidad tonalidadDos) {
        this.nombreArmadura = nombreArmadura;
        this.tonalidadUno = tonalidadUno;
        this.tonalidadDos = tonalidadDos;
    }

    public Armadura(String nombreArmadura) {
        this.nombreArmadura = nombreArmadura;
    }

    public String getNombreArmadura() {
        return nombreArmadura;
    }

    public void setNombreArmadura(String nombreArmadura) {
        this.nombreArmadura = nombreArmadura;
    }

    public Tonalidad getTonalidadUno() {
        return tonalidadUno;
    }

    public void setTonalidadUno(Tonalidad tonalidadUno) {
        this.tonalidadUno = tonalidadUno;
    }

    public Tonalidad getTonalidadDos() {
        return tonalidadDos;
    }

    public void setTonalidadDos(Tonalidad tonalidadDos) {
        this.tonalidadDos = tonalidadDos;
    }

    @Override
    public String toString() {
        return nombreArmadura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Armadura)) return false;
        Armadura armadura = (Armadura) o;
        return Objects.equals(getNombreArmadura(), armadura.getNombreArmadura());
    }

    @Override
    public int compareTo(Armadura armadura) {
        if(this.equals(armadura)) {
            return 1;
        } else {
            return 0;
        }
    }
}
