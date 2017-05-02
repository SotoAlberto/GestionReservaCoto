package com.asotorui.gestionreservacoto.utilidades;

/**
 * Created by soto8 on 13/04/2017.
 *
 * FormatearFechaAaaaMmDdCalendar
 *
 * DEVUELVE FECHA A PARTIR DE INT CON : AAAA-MM-DD
 *
 * 2017-1-1 :  2017-01-01 - Para la clase calendar, devuelve un mes menos
 *
 */

public class FormateadorFechaAaaaMmDd {


    public String FormatearFechaAaaaMmDdCalendar (int annio, int mesAnio, int diaMesAnio){

        final String separadorFecha = "-";

        String fechaFormateada;
        String strDiaMesAnio;
        String strMesAnio;
        String strAnnio;

        strAnnio = String.valueOf(annio);

        mesAnio = mesAnio + 1;
        strMesAnio = String.valueOf(mesAnio);

        if (strMesAnio.length() == 1){
            strMesAnio = "0" + strMesAnio;
        }

        strDiaMesAnio = String.valueOf(diaMesAnio);

        if (strDiaMesAnio.length() == 1){
            strDiaMesAnio = "0" + strDiaMesAnio;
        }

        fechaFormateada = strAnnio + separadorFecha + strMesAnio + separadorFecha + strDiaMesAnio;

        return fechaFormateada;
    }

}
