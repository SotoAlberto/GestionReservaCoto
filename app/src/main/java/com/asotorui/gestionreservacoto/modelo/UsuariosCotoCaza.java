package com.asotorui.gestionreservacoto.modelo;

/**
 * Created by soto8 on 11/04/2017.
 */

public class UsuariosCotoCaza {

    private String nombreUsuario;
    private String telefonoUsuario;
    private String fechaInicioCaza;
    private String fechaFinCaza;
    private String cotoAsignadoCaza;
    private String nombreCotoAsignado;
    private int numeroVecesCazadas;

    public UsuariosCotoCaza() {

    }

    public String getNombreCotoAsignado() {
        return nombreCotoAsignado;
    }

    public void setNombreCotoAsignado(String nombreCotoAsignado) {
        this.nombreCotoAsignado = nombreCotoAsignado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
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

    public String getCotoAsignadoCaza() {
        return cotoAsignadoCaza;
    }

    public void setCotoAsignadoCaza(String cotoAsignadoCaza) {
        this.cotoAsignadoCaza = cotoAsignadoCaza;
    }

    public int getNumeroVecesCazadas() {
        return numeroVecesCazadas;
    }

    public void setNumeroVecesCazadas(int numeroVecesCazadas) {
        this.numeroVecesCazadas = numeroVecesCazadas;
    }
}
