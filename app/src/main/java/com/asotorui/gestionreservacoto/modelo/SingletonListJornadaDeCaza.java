package com.asotorui.gestionreservacoto.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soto8 on 20/04/2017.
 */

public class SingletonListJornadaDeCaza {

    private static SingletonListJornadaDeCaza sSingletonListJornadaDeCaza;
    private static ArrayList<JornadaDeCaza> mJornadaDeCaza;


    public SingletonListJornadaDeCaza() {

    }

    public void addSingletonListJornadaDeCaza(String nombreUsuario, String telefonoUsuario, String nombreCotoCaza, String fechaDeCaza) {

        JornadaDeCaza jornadaCotoCaza = new JornadaDeCaza();

        jornadaCotoCaza.setNombreUsuario(nombreUsuario);
        jornadaCotoCaza.setTelefonoUsuario(telefonoUsuario);
        jornadaCotoCaza.setNombreCotoCaza(nombreCotoCaza);
        jornadaCotoCaza.setFechaDeCaza(fechaDeCaza);

        mJornadaDeCaza.add(jornadaCotoCaza);

    }


    public List<JornadaDeCaza> getJornadaDeCaza() {

        return mJornadaDeCaza;
    }

    public static SingletonListJornadaDeCaza get() {
        if (sSingletonListJornadaDeCaza == null){
            sSingletonListJornadaDeCaza = new SingletonListJornadaDeCaza();
            mJornadaDeCaza = new ArrayList<>();

        }

        return sSingletonListJornadaDeCaza;
    }

    //Obtener datos de la Joranada de Caza
    public JornadaDeCaza getInfoJornadaDeCaza(String nombreUsuario, String telefonoUsuario, String nombreCotoCaza, String fechaDeCaza){
        for (JornadaDeCaza jornadaDeCaza : mJornadaDeCaza){
            if (jornadaDeCaza.getNombreCotoCaza().equals(nombreCotoCaza) &&
                jornadaDeCaza.getNombreUsuario().equals(nombreUsuario) &&
                jornadaDeCaza.getTelefonoUsuario().equals(telefonoUsuario) &&
                jornadaDeCaza.getFechaDeCaza().equals(fechaDeCaza)){

                return jornadaDeCaza;
            }
        }

        return null;
    }
}
