package com.asotorui.gestionreservacoto.utilidades;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;

import com.asotorui.gestionreservacoto.modelo.LlamadasEntrantes;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by soto8 on 25/04/2017.
 */

public class ListaDeLlamadasEntrantes {

    /*
     * Se filtran las llamadas diarias perdidas por dia y hora
     */

    public ListaDeLlamadasEntrantes() {

    }

    public ArrayList<LlamadasEntrantes> getCallDetails(Context context, String fechaFiltroIn, String horaInicioFiltroIn, String horaFinFiltroIn) {

        ArrayList<LlamadasEntrantes> llamadasEntrantes = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");

        int numeroDeTelefono = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int nombreDePersona = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int tipoDeLlamada = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int fechaDeLlamada = cursor.getColumnIndex(CallLog.Calls.DATE);

        FormateadorFechaAaaaMmDd formatearFechaEntrada = new FormateadorFechaAaaaMmDd();
        FormateadorHoraHhMmSs formatearHoraEntrada = new FormateadorHoraHhMmSs();
        FormateadorContactosTelefonicos formatearNumTelefono = new FormateadorContactosTelefonicos();

        while (cursor.moveToNext()) {

            String telefNumeroDeTelefon = cursor.getString(numeroDeTelefono);
            String telefTipoDeLllamada = cursor.getString(tipoDeLlamada);
            String telefNombreDePersona = cursor.getString(nombreDePersona);

            Calendar c = Calendar.getInstance();

            long longFecha = new Long(cursor.getString(fechaDeLlamada));
            c.setTimeInMillis(longFecha);

            int dircode = Integer.parseInt(telefTipoDeLllamada);

            String fechaDeEnvioLlamada = formatearFechaEntrada.FormatearFechaAaaaMmDdCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            String horaDeEnvioLlamada = formatearHoraEntrada.FormateadorHoraHhMmSs(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));

            if ((CallLog.Calls.INCOMING_TYPE == dircode) &&
                (fechaDeEnvioLlamada.equals(fechaFiltroIn)) &&
                ((horaDeEnvioLlamada .replace(":","").compareTo(horaInicioFiltroIn.replace(":","")) == 0 || //igual a las 14:00
                  horaDeEnvioLlamada .replace(":","").compareTo(horaInicioFiltroIn.replace(":","")) > 0) && //mayor que 14:00
                 (horaDeEnvioLlamada .replace(":","").compareTo(horaFinFiltroIn.replace(":","")) == 0 || //igual a las 15:00
                  horaDeEnvioLlamada .replace(":","").compareTo(horaFinFiltroIn.replace(":","")) < 0))){ //menor que 15:00     ){

                //Devuelve false en caso de que el ususario solo haya realizado una llamada

                if (!eliminarDuplicadosLlamadas(llamadasEntrantes, formatearNumTelefono.FormateoTelefonoSinPrefijo(telefNumeroDeTelefon), telefNombreDePersona)){
                    LlamadasEntrantes llamadasIn = new LlamadasEntrantes();
                    llamadasIn.setNombrePersona(telefNombreDePersona);
                    llamadasIn.setNumeroTelefono(formatearNumTelefono.FormateoTelefonoSinPrefijo(telefNumeroDeTelefon));
                    llamadasIn.setFechaEntradaLlamada(fechaDeEnvioLlamada);
                    llamadasIn.setHoraEntradaLlamada(horaDeEnvioLlamada);

                    //llamadasIn.setFechaEntradaLlamada(formatearFechaEntrada.FormatearFechaAaaaMmDdCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)));
                    //llamadasIn.setHoraEntradaLlamada(formatearHoraEntrada.FormateadorHoraHhMmSs(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));

                    llamadasEntrantes.add(llamadasIn);
                }

            }

        }

        cursor.close();

        return llamadasEntrantes;
    }

    private boolean eliminarDuplicadosLlamadas(ArrayList<LlamadasEntrantes> llamadasEntrantesIn, String numeroTelefonoIn, String nombrePersonaIn){
        /*
         * Devuelve false en caso de que el ususario solo haya enviado una llamada
         */
        boolean extisteDuplicado = false;

        for (int i = 0; i < llamadasEntrantesIn.size(); i++){
            if (numeroTelefonoIn.equals(llamadasEntrantesIn.get(i).getNumeroTelefono()) &&
                    nombrePersonaIn.equals(llamadasEntrantesIn.get(i).getNombrePersona())){
                extisteDuplicado = true;
            }
        }

        return extisteDuplicado;
    }


}
