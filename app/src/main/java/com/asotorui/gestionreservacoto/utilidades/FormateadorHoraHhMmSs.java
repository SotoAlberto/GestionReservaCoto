package com.asotorui.gestionreservacoto.utilidades;

/**
 * Created by soto8 on 27/04/2017.
 *
 * DEVUELVE HORA A PARTIR DE INT CON FORMATO : HH:MM:SS
 *
 */

public class FormateadorHoraHhMmSs {

    static final String separadorHora = ":";

    public String FormateadorHoraHhMmSs(int hora, int minuto, int segundo){

        String horaFormateada;
        String strHora;
        String strMinuto;
        String strSegundo;

        strHora = String.valueOf(hora);
        strMinuto = String.valueOf(minuto);
        strSegundo = String.valueOf(segundo);

        if (strHora.length() == 1){
            strHora = "0" + strHora;
        }

        if (strMinuto.length() == 1){
            strMinuto = "0" + strMinuto;
        }

        if (strSegundo.length() == 1){
            strSegundo = "0" + strSegundo;
        }

        horaFormateada = strHora + separadorHora + strMinuto + separadorHora + strSegundo;

        return horaFormateada;
    }
}
