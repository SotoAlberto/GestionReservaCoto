package com.asotorui.gestionreservacoto.utilidades;

import com.asotorui.gestionreservacoto.modelo.LlamadasEntrantes;
import com.asotorui.gestionreservacoto.modelo.SingletonListUsuariosCotoCaza;
import com.asotorui.gestionreservacoto.modelo.SmsEntrantes;
import com.asotorui.gestionreservacoto.modelo.UsuariosCotoCaza;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soto8 on 26/04/2017.
 */

public class EliminarUsuariosNoValidos {

    /*
     * Se filtran y eliminan (dejando una lista aplanada) aquellos usuarios no validos
     * Filtro : no estar penalizados
     * Filtro : no haber superado el numero de veces cazado
     * Filtro : encontrarse dados de alta en la lista de contactos
     * Filtro : pertenecer al coto especificado de caza
     */

    public EliminarUsuariosNoValidos() {

    }

    public ArrayList<SmsEntrantes> eliminarUsuariosNoValidosSms(ArrayList<SmsEntrantes> smsEntranteIn, String fechaFormateadaIn){

        String[][] usu = new String[1][1];
        usu[0][0] = "";
        ArrayList<SmsEntrantes> smsEntrantesOut = new ArrayList<>();
        SingletonListUsuariosCotoCaza usuariosCotoCaza = SingletonListUsuariosCotoCaza.get(usu, "2017-01-01", "2017-12-31");
        List<UsuariosCotoCaza> usuariosCotoCazaList =  usuariosCotoCaza.getUsuariosCotoCaza();

        //IVOIVO : COMPROBAR QUE LOS QUE REALIZAN ENVIOS DE SMS ESTAN ASIGNADOS AL COTO CORRESPONDIENTE


        for (int i = 0; i < smsEntranteIn.size(); i++){
            for (int j = 0; j < usuariosCotoCazaList.size(); j++){
                if (smsEntranteIn.get(i).getNombrePersona().equals(usuariosCotoCazaList.get(j).getNombreUsuario()) &&
                    smsEntranteIn.get(i).getNumeroTelefono().equals(usuariosCotoCazaList.get(j).getTelefonoUsuario())){
                    //No estar penalizados : se comprueba a traves de la fecha de inicio de caza, debe
                    //ser menor o igual a la fecha del dia, en caso contrario se eliminan
                    //No haber superado el numero de veces cazado (15 veces maximo)
                    if ((usuariosCotoCazaList.get(j).getFechaInicioCaza().compareTo(fechaFormateadaIn) == 0 ||
                         usuariosCotoCazaList.get(j).getFechaInicioCaza().compareTo(fechaFormateadaIn) < 0) &&
                        (usuariosCotoCazaList.get(j).getNumeroVecesCazadas() < 16)){
                        smsEntranteIn.get(i).setNumeroDeVecesCazadasSms(usuariosCotoCazaList.get(j).getNumeroVecesCazadas());
                        smsEntrantesOut.add(smsEntranteIn.get(i));


                    }
                }
            }
        }

        return smsEntrantesOut;
    }

    public ArrayList<LlamadasEntrantes> eliminarUsuariosNoValidosLlamadas(ArrayList<LlamadasEntrantes> llamadasEntranteIn, String fechaFormateadaIn){

        ArrayList<LlamadasEntrantes> llamadasEntrantesOut = new ArrayList<>();
        SingletonListUsuariosCotoCaza usuariosCotoCaza = null;
        List<UsuariosCotoCaza> usuariosCotoCazaList =  usuariosCotoCaza.getUsuariosCotoCaza();

        //IVOIVO : COMPROBAR QUE LOS QUE REALIZAN LLAMADAS ESTAN ASIGNADOS AL COTO CORRESPONDIENTE

        for (int i = 0; i < llamadasEntranteIn.size(); i++){
            for (int j = 0; j < usuariosCotoCazaList.size(); j++){
                if (llamadasEntranteIn.get(i).getNombrePersona().equals(usuariosCotoCazaList.get(j).getNombreUsuario()) &&
                    llamadasEntranteIn.get(i).getNumeroTelefono().equals(usuariosCotoCazaList.get(j).getTelefonoUsuario())){
                    //No estar penalizados : se comprueba a traves de la fecha de inicio de caza, debe
                    //ser menor o igual a la fecha del dia, en caso contrario se eliminan
                    //No haber superado el numero de veces cazado (15 veces maximo)
                    if ((usuariosCotoCazaList.get(j).getFechaInicioCaza().compareTo(fechaFormateadaIn) == 0 ||
                         usuariosCotoCazaList.get(j).getFechaInicioCaza().compareTo(fechaFormateadaIn) < 0) &&
                        (usuariosCotoCazaList.get(j).getNumeroVecesCazadas() < 16)){
                        llamadasEntranteIn.get(i).setNumeroDeVecesCazadasLlamada(usuariosCotoCazaList.get(j).getNumeroVecesCazadas());
                        llamadasEntrantesOut.add(llamadasEntranteIn.get(i));

                    }
                }
            }
        }

        return llamadasEntrantesOut;
    }

}
