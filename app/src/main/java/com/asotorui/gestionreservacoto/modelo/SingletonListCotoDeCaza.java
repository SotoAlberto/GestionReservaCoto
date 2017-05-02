package com.asotorui.gestionreservacoto.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soto8 on 20/04/2017.
 */

public class SingletonListCotoDeCaza {

    private static SingletonListCotoDeCaza sSingletonListCotoDeCaza;
    private ArrayList<CotoDeCaza> mCotoDeCaza;

    //DESIASR IVOIVO : IMPLEMENTAR CARGA DE COTO FECHAS
    //DESIASR IVOIVO : IMPLEMENTAR ACTUALIZACION DE FECHAS
    public SingletonListCotoDeCaza(String[] mCotoDeCazaIn, String fechaInicioTemporada, String fechaFinTemporada) {
        mCotoDeCaza = new ArrayList<>();

        for (int i = 0; i < mCotoDeCazaIn.length; i++){
            CotoDeCaza cotoDeCaza = new CotoDeCaza();
            cotoDeCaza.setNombreCotoCaza(mCotoDeCazaIn[i]);
            cotoDeCaza.setFechaInicioCaza(fechaInicioTemporada);
            cotoDeCaza.setFechaFinCaza(fechaFinTemporada);

            if (mCotoDeCazaIn[i].equals("LAS CHIQUILLAS")){
                cotoDeCaza.setNumeroMaximoCazadores(2);
            }else{
                cotoDeCaza.setNumeroMaximoCazadores(3);
            }


            mCotoDeCaza.add(cotoDeCaza);

        }

    }

    public List<CotoDeCaza> getCotoCaza(){

        return mCotoDeCaza;
    }

    public static SingletonListCotoDeCaza get(String[] mCotoDeCaza, String fechaInicioTemporada, String fechaFinTemporada) {
        if (sSingletonListCotoDeCaza == null){
            sSingletonListCotoDeCaza = new SingletonListCotoDeCaza(mCotoDeCaza, fechaInicioTemporada, fechaFinTemporada);

        }

        return sSingletonListCotoDeCaza;
    }

    //Obtener el Coto de Caza
    public CotoDeCaza getInfoCoto(String nombreCotoCaza){
        for (CotoDeCaza cotosCazaDisponible : mCotoDeCaza){
            if (cotosCazaDisponible.getNombreCotoCaza().equals(nombreCotoCaza)){
                return cotosCazaDisponible;
            }
        }

        return null;
    }
}
