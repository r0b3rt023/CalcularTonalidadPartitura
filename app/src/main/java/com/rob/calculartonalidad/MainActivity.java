package com.rob.calculartonalidad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Armadura> listaSpinnerArmaduras;
    private ArrayList<Armadura> listaArmaduras;
    private ArrayList<Nota> listaSpinnerNotas;

    /*COMPONENTES DE LA APLICACIÓN*/
    private Spinner spinnerArmadura, spinnerPrimeraNota, spinnerUltimaNota, spinnerNotaReposo;
    private TextView textoTonalidad, textoAuxiliar;
    private Button botonCalcular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //Desactivo el modo nocturno
        //Elimino la ActionBar de la Activity
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        cargarListaNotas();
        cargarArmaduras();
        cargarListaArmaduras();

        setSpinnerArmadura();
        setSpinnersNotas();
        setSpinnerNotaReposo();
        setTextoTonalidad();
        setTextoAuxiliar();
        setBotonCalcular();
    }

    private void setSpinnerArmadura() {
        spinnerArmadura = findViewById(R.id.spinnerArmadura);

        ArrayAdapter<Armadura> adapterArmadura = new ArrayAdapter(this, R.layout.spinners_custom, listaArmaduras);
        adapterArmadura.setDropDownViewResource(R.layout.spinners_dropdown_custom);

        spinnerArmadura.setAdapter(adapterArmadura);
    }

    private void setSpinnersNotas() {
        spinnerPrimeraNota = findViewById(R.id.spinnerPrimeraNota);
        spinnerUltimaNota = findViewById(R.id.spinnerUltimaNota);

        ArrayAdapter<Nota> adapterNotas = new ArrayAdapter(this, R.layout.spinners_custom, listaSpinnerNotas);
        adapterNotas.setDropDownViewResource(R.layout.spinners_dropdown_custom);

        spinnerPrimeraNota.setAdapter(adapterNotas);
        spinnerUltimaNota.setAdapter(adapterNotas);
    }

    private void setSpinnerNotaReposo() {
        spinnerNotaReposo = findViewById(R.id.spinnerNotaReposo);

        ArrayAdapter<Nota> adapterNotaReposo = new ArrayAdapter(this, R.layout.spinners_custom, listaSpinnerNotas);
        adapterNotaReposo.setDropDownViewResource(R.layout.spinners_dropdown_custom);

        spinnerNotaReposo.setAdapter(adapterNotaReposo);
    }

    private void setTextoTonalidad() {
        textoTonalidad = findViewById(R.id.textoTonalidad);
    }

    private void setTextoAuxiliar() {
        textoAuxiliar = findViewById(R.id.textoAuxiliar);
    }

    private void setBotonCalcular() {
        botonCalcular = findViewById(R.id.botonCalcular);
        botonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Armadura armaduraSelected = (Armadura) spinnerArmadura.getSelectedItem();
                Nota notaPrimera = (Nota) spinnerPrimeraNota.getSelectedItem();
                Nota notaUltima = (Nota) spinnerUltimaNota.getSelectedItem();
                Nota notaReposo = (Nota) spinnerNotaReposo.getSelectedItem();

                System.out.println(notaPrimera + ", " + notaUltima);
                int posArmadura = listaArmaduras.indexOf(armaduraSelected);
                Armadura armadura = listaArmaduras.get(posArmadura);
                System.out.println(armadura.toString());

                textoTonalidad.setText(obtenerTonalidad(armadura, notaPrimera, notaUltima, notaReposo));
            }
        });
    }

    private String obtenerTonalidad(Armadura armadura, Nota notaPrimera, Nota notaUltima, Nota notaReposo) {
        Tonalidad tonalidadUno = armadura.getTonalidadUno();
//        System.out.println(tonalidadUno.getNotasImportantes().toString());
        Tonalidad tonalidadDos = armadura.getTonalidadDos();
//        System.out.println(tonalidadDos.getNotasImportantes().toString());

//      TODO Cambiar el valor de (Elija Nota) y meterlo en string.xml
        if (notaPrimera.getNombre().equals(getString(R.string.elijaNota)) || notaUltima.getNombre().equals(getString(R.string.elijaNota))) {
            return getString(R.string.textoPredeterminadoTonalidad);
        } else {

            int posNotaTonalidadUno = tonalidadUno.getNotasImportantes().indexOf(notaPrimera) > tonalidadUno.getNotasImportantes().indexOf(notaUltima) ?
                    tonalidadUno.getNotasImportantes().indexOf(notaPrimera) : tonalidadUno.getNotasImportantes().indexOf(notaUltima);

            int posNotaTonalidadDos = tonalidadDos.getNotasImportantes().indexOf(notaPrimera) > tonalidadDos.getNotasImportantes().indexOf(notaPrimera) ?
                    tonalidadDos.getNotasImportantes().indexOf(notaPrimera) : tonalidadDos.getNotasImportantes().indexOf(notaUltima);

            System.out.println(posNotaTonalidadUno);
            System.out.println(posNotaTonalidadDos);

            if (posNotaTonalidadDos == -1) {
                textoAuxiliar.setText("");
                return tonalidadUno.toString();
            } else if (posNotaTonalidadUno == -1) {
                textoAuxiliar.setText("");
                return tonalidadDos.toString();
            } else {

                if (notaReposo.getNombre().equals(getString(R.string.elijaNota))) {
                    if (posNotaTonalidadUno < posNotaTonalidadDos) {
                        textoAuxiliar.setText("(Podría ser " + tonalidadDos + ". De ser posible, especifique con alguna nota de reposo)");
                        return tonalidadUno.toString();
                    } else if (posNotaTonalidadUno > posNotaTonalidadDos) {
                        textoAuxiliar.setText("(Podría ser " + tonalidadUno + ". De ser posible, especifique con alguna nota de reposo)");
                        return tonalidadDos.toString();
                    } else {
                        textoAuxiliar.setText("(Podrían ser ambas tonalidades. De ser posible, especifique con alguna nota de reposo)");
                        return tonalidadUno.toString() + "/" + tonalidadDos.toString();
                    }
                } else {
                    if (tonalidadUno.getNotasReposo().contains(notaReposo) && !tonalidadDos.getNotasReposo().contains(notaReposo)) {
                        textoAuxiliar.setText("");
                        return tonalidadUno.toString();
                    } else if (!tonalidadUno.getNotasReposo().contains(notaReposo) && tonalidadDos.getNotasReposo().contains(notaReposo)) {
                        textoAuxiliar.setText("");
                        return tonalidadDos.toString();
                    } else {
                        textoAuxiliar.setText("(Podrían ser ambas tonalidades. De ser posible, especifique con alguna nota de reposo)");
                        return tonalidadUno.toString() + "/" + tonalidadDos.toString();
                    }
                }
            }
        }
    }

    private void cargarListaNotas() {
        listaSpinnerNotas = new ArrayList<>();
        //Nota Vacia
        listaSpinnerNotas.add(new Nota(getString(R.string.elijaNota), false, false));
        //Normales
        listaSpinnerNotas.add(new Nota("Do", false, false));
        listaSpinnerNotas.add(new Nota("Re", false, false));
        listaSpinnerNotas.add(new Nota("Mi", false, false));
        listaSpinnerNotas.add(new Nota("Fa", false, false));
        listaSpinnerNotas.add(new Nota("Sol", false, false));
        listaSpinnerNotas.add(new Nota("La", false, false));
        listaSpinnerNotas.add(new Nota("Si", false, false));
        //Sostenido
        listaSpinnerNotas.add(new Nota("Do", false, true));
        listaSpinnerNotas.add(new Nota("Re", false, true));
        listaSpinnerNotas.add(new Nota("Mi", false, true));
        listaSpinnerNotas.add(new Nota("Fa", false, true));
        listaSpinnerNotas.add(new Nota("Sol", false, true));
        listaSpinnerNotas.add(new Nota("La", false, true));
        listaSpinnerNotas.add(new Nota("Si", false, true));
        //Bemoles
        listaSpinnerNotas.add(new Nota("Do", true, false));
        listaSpinnerNotas.add(new Nota("Re", true, false));
        listaSpinnerNotas.add(new Nota("Mi", true, false));
        listaSpinnerNotas.add(new Nota("Fa", true, false));
        listaSpinnerNotas.add(new Nota("Sol", true, false));
        listaSpinnerNotas.add(new Nota("La", true, false));
        listaSpinnerNotas.add(new Nota("Si", true, false));
    }

    private void cargarListaArmaduras() {
        listaSpinnerArmaduras = new ArrayList<>();
        listaSpinnerArmaduras.add(new Armadura("-"));
        listaSpinnerArmaduras.add(new Armadura("1♯"));
        listaSpinnerArmaduras.add(new Armadura("2♯"));
        listaSpinnerArmaduras.add(new Armadura("3♯"));
        listaSpinnerArmaduras.add(new Armadura("4♯"));
        listaSpinnerArmaduras.add(new Armadura("5♯"));
        listaSpinnerArmaduras.add(new Armadura("6♯"));
        listaSpinnerArmaduras.add(new Armadura("7♯"));
    }

    private void cargarArmaduras() {
        listaArmaduras = new ArrayList<>();
        Tonalidad tonalidadUno;
        Tonalidad tonalidadDos;

        ArrayList<Nota> notasImportantes;
        ArrayList<Nota> notasReposo;
        /*Sin Armadura*/
        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Do", false, false));
        notasImportantes.add(new Nota("Sol", false, false));
        notasImportantes.add(new Nota("Fa", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Mi", false, false));
        tonalidadUno = new Tonalidad("Do M", notasImportantes, notasReposo);

        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("La", false, false));
        notasImportantes.add(new Nota("Mi", false, false));
        notasImportantes.add(new Nota("Re", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Do", false, false));
        tonalidadDos = new Tonalidad("La m", notasImportantes, notasReposo);

        listaArmaduras.add(new Armadura("-", tonalidadUno, tonalidadDos));
        /**/
        /*1♯*/
        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Sol", false, false));
        notasImportantes.add(new Nota("Re", false, false));
        notasImportantes.add(new Nota("Do", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Si", false, false));
        tonalidadUno = new Tonalidad("Sol M", notasImportantes, notasReposo);

        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Mi", false, false));
        notasImportantes.add(new Nota("Si", false, false));
        notasImportantes.add(new Nota("La", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Sol", false, false));
        tonalidadDos = new Tonalidad("Mi m", notasImportantes, notasReposo);

        listaArmaduras.add(new Armadura("1♯", tonalidadUno, tonalidadDos));
        /**/
        /*2♯*/
        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Re", false, false));
        notasImportantes.add(new Nota("La", false, false));
        notasImportantes.add(new Nota("Sol", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Si", false, false));
        tonalidadUno = new Tonalidad("Re M", notasImportantes, notasReposo);

        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Si", false, false));
        notasImportantes.add(new Nota("Fa", false, true));
        notasImportantes.add(new Nota("Mi", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Sol", false, false));
        tonalidadDos = new Tonalidad("Si m", notasImportantes, notasReposo);

        listaArmaduras.add(new Armadura("2♯", tonalidadUno, tonalidadDos));
        /**/
        /*3♯*/
        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("La", false, false));
        notasImportantes.add(new Nota("Mi", false, false));
        notasImportantes.add(new Nota("Re", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Do", false, true));
        tonalidadUno = new Tonalidad("La M", notasImportantes, notasReposo);

        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Fa", false, true));
        notasImportantes.add(new Nota("Do", false, true));
        notasImportantes.add(new Nota("Si", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Sol", false, false));
        tonalidadDos = new Tonalidad("Fa♯ m", notasImportantes, notasReposo);

        listaArmaduras.add(new Armadura("3♯", tonalidadUno, tonalidadDos));
        /**/
        /*4♯*/
        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Mi", false, false));
        notasImportantes.add(new Nota("Si", false, false));
        notasImportantes.add(new Nota("La", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Sol", false, true));
        tonalidadUno = new Tonalidad("Mi M", notasImportantes, notasReposo);

        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Do", false, true));
        notasImportantes.add(new Nota("Sol", false, true));
        notasImportantes.add(new Nota("Fa", false, true));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Mi", false, false));
        tonalidadDos = new Tonalidad("Do♯ m", notasImportantes, notasReposo);

        listaArmaduras.add(new Armadura("4♯", tonalidadUno, tonalidadDos));
        /**/
        /*5♯*/
        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Si", false, false));
        notasImportantes.add(new Nota("Fa", false, true));
        notasImportantes.add(new Nota("Mi", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Re", false, true));
        tonalidadUno = new Tonalidad("Si M", notasImportantes, notasReposo);

        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Sol", false, true));
        notasImportantes.add(new Nota("Re", false, true));
        notasImportantes.add(new Nota("Do", false, true));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Si", false, false));
        tonalidadDos = new Tonalidad("Sol♯ m", notasImportantes, notasReposo);

        listaArmaduras.add(new Armadura("5♯", tonalidadUno, tonalidadDos));
        /**/
        /*6♯*/
        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Fa", false, true));
        notasImportantes.add(new Nota("Do", false, true));
        notasImportantes.add(new Nota("Si", false, false));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("La", false, true));
        tonalidadUno = new Tonalidad("Fa♯ M", notasImportantes, notasReposo);

        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Re", false, true));
        notasImportantes.add(new Nota("La", false, true));
        notasImportantes.add(new Nota("Sol", false, true));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Fa", false, true));
        tonalidadDos = new Tonalidad("Re♯ m", notasImportantes, notasReposo);

        listaArmaduras.add(new Armadura("6♯", tonalidadUno, tonalidadDos));
        /**/
        /*7♯*/
        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("Do", false, true));
        notasImportantes.add(new Nota("Sol", false, true));
        notasImportantes.add(new Nota("Fa", false, true));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Mi", false, true));
        tonalidadUno = new Tonalidad("Do♯ M", notasImportantes, notasReposo);

        notasImportantes = new ArrayList<>();
        notasReposo = new ArrayList<>();
        notasImportantes.add(new Nota("La", false, true));
        notasImportantes.add(new Nota("Mi", false, true));
        notasImportantes.add(new Nota("Re", false, true));
        notasReposo.addAll(notasImportantes);
        notasReposo.add(new Nota("Do", false, true));
        tonalidadDos = new Tonalidad("La♯ m", notasImportantes, notasReposo);

        listaArmaduras.add(new Armadura("7♯", tonalidadUno, tonalidadDos));
        /**/
    }

}