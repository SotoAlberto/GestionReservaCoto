package com.asotorui.gestionreservacoto.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soto8 on 11/04/2017.
 */

public class SingletonListUsuariosCotoCaza {

    private static SingletonListUsuariosCotoCaza sSingletonListUsuariosCotoCaza;
    private ArrayList<UsuariosCotoCaza> mUsuariosCotoCaza;


    public SingletonListUsuariosCotoCaza(String[][] mUsuCotoCazaFormatoIn, String fechaInicioTemporada, String fechaFinTemporada) {
        mUsuariosCotoCaza = new ArrayList<>();

        for (int i = 0; i < mUsuCotoCazaFormatoIn.length; i++){
            UsuariosCotoCaza usuariosCotoCaza = new UsuariosCotoCaza();
            usuariosCotoCaza.setNombreUsuario(mUsuCotoCazaFormatoIn[i][0]);
            usuariosCotoCaza.setTelefonoUsuario(mUsuCotoCazaFormatoIn[i][1]);
            usuariosCotoCaza.setFechaInicioCaza(fechaInicioTemporada);
            usuariosCotoCaza.setFechaFinCaza(fechaFinTemporada);
            usuariosCotoCaza.setCotoAsignadoCaza("Coto por defecto");

            mUsuariosCotoCaza.add(usuariosCotoCaza);
        }

    }

    public SingletonListUsuariosCotoCaza() {

    }

    public List<UsuariosCotoCaza> getUsuariosCotoCaza(){

        return mUsuariosCotoCaza;
    }

    public static SingletonListUsuariosCotoCaza get(String[][] mUsuCotoCazaFormato,String fechaInicioTemporada, String fechaFinTemporada) {
        if (sSingletonListUsuariosCotoCaza == null){
            sSingletonListUsuariosCotoCaza = new SingletonListUsuariosCotoCaza(mUsuCotoCazaFormato, fechaInicioTemporada, fechaFinTemporada);

        }

        return sSingletonListUsuariosCotoCaza;
    }

    //Obtener el objeto Usuario
    public UsuariosCotoCaza getUsuario(String nombrePersona, String telefonoPersona){
        for (UsuariosCotoCaza usuariosCotoCaza : mUsuariosCotoCaza){
            if (usuariosCotoCaza.getNombreUsuario().equals(nombrePersona) &&
                usuariosCotoCaza.getTelefonoUsuario().equals(telefonoPersona)){
                return usuariosCotoCaza;
            }
        }

        return null;
    }




}
