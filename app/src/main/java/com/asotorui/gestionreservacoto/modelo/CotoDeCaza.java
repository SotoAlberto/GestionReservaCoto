package com.asotorui.gestionreservacoto.modelo;

/**
 * Created by soto8 on 20/04/2017.
 */

public class CotoDeCaza{

    private String nombreCotoCaza;
    private String fechaInicioCaza;
    private String fechaFinCaza;
    private int numeroMaximoCazadores;

    public String getNombreCotoCaza() {
        return nombreCotoCaza;
    }

    public void setNombreCotoCaza(String nombreCotoCaza) {
        this.nombreCotoCaza = nombreCotoCaza;
    }

    public String getFechaInicioCaza() {
        return fechaInicioCaza;

    }

    public void setFechaInicioCaza(String fechaInicioCaza) {
        this.fechaInicioCaza = fechaInicioCaza;
    }

    public String getFechaFinCaza() {
        return fechaFinCaza;
    }

    public void setFechaFinCaza(String fechaFinCaza) {
        this.fechaFinCaza = fechaFinCaza;
    }

    public int getNumeroMaximoCazadores() {
        return numeroMaximoCazadores;
    }

    public void setNumeroMaximoCazadores(int numeroMaximoCazadores) {
        this.numeroMaximoCazadores = numeroMaximoCazadores;
    }
}
