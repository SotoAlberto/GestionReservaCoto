package com.asotorui.gestionreservacoto.utilidades;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.asotorui.gestionreservacoto.modelo.SmsEntrantes;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by soto8 on 25/04/2017.
 */

public class ListaDeSmsEntrantes {

    private static final String USUARIO_NO_DADO_DE_ALTA = "USUARIO NO DADO DE ALTA";
    private static final String sTextoBuscadoMas = "+";
    private static final String sTextoBuscadoSeis = "6";

    /*
     * Metodo encargado de devolver los SMS recibidos en el dia.
     * Como parametro de entrada tiene la fecha de filtro y la hora de inicio y fin de filtro
     * Retorna un ArrayList de objetos SmsEntrantes :
     *      Numero de telefono formateado y filtrado : 649776787 (Se eliminan usuarios no guardados en contactos)
     *          Entran asi a traves del codigo
     *              Log.i("DESIASR SMS ", sms.toString());
     *
     *              I/DESIASR SMS: +34649776787
     *
     *              I/DESIASR SMS: PODEMOS - Son empresas
     *
     *              I/DESIASR SMS: FROM :+34630414799 - Usuario no guardado en Contactos
     *
     *      Nombre de persona : Se eliminan USUARIO POR DEFECTO
     *              Log.i("DESIASR PERSON ", getContactName(context, cur.getString(cur.getColumnIndex("address"))));
     *
     *              I/DESIASR PERSON: Patri
     *              I/DESIASR PERSON: USUARIO POR DEFECTO - Son empresas
     *              I/DESIASR PERSON: USUARIO POR DEFECTO - - Usuario no guardado en Contactos
     *
     *      Fecha del envio del SMS : 2017-01-01
     *          Entran asi a traves del codigo
     *              Log.i("DESIASR FECHA ", c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR) + "-" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
     *
     *              I/DESIASR FECHA: 26-3-2017-12:36:36
     *              I/DESIASR FECHA: 19-11-2016-20:21:40
     *
     *      Hora del envio del SMS - 01:01:01
     *          Entran asi a traves del codigo
     *              Log.i("DESIASR FECHA ", c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR) + "-" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
     *
     *              I/DESIASR FECHA: 26-3-2017-12:36:36
     *              I/DESIASR FECHA: 19-11-2016-8:55:44
     */

    public ListaDeSmsEntrantes() {

    }

    public ArrayList<SmsEntrantes> getSmsDetails(Context context, String fechaFiltroIn, String horaInicioFiltroIn, String horaFinFiltroIn) {

        ArrayList<SmsEntrantes> smsEntrantes = new ArrayList<>();

        Cursor cur = context.getContentResolver().query(Uri.parse("content://sms/inbox/"), null, null, null, "date DESC");

        FormateadorFechaAaaaMmDd formatearFechaEntrada = new FormateadorFechaAaaaMmDd();
        FormateadorHoraHhMmSs formatearHoraEntrada = new FormateadorHoraHhMmSs();
        FormateadorContactosTelefonicos formatearNumTelefono = new FormateadorContactosTelefonicos();

        while (cur.moveToNext()) {

            Calendar c = Calendar.getInstance();

            long longDate = new Long(cur.getString(cur.getColumnIndex("date")));
            c.setTimeInMillis(longDate);

            String numeroTelefono = cur.getString(cur.getColumnIndex("address"));
            String nombrePersona = obtenerNombreContacto(context, cur.getString(cur.getColumnIndex("address")));
            String fechaDeEnvioSms = formatearFechaEntrada.FormatearFechaAaaaMmDdCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            String horaDeEnvioSms = formatearHoraEntrada.FormateadorHoraHhMmSs(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));

            /*ivoivo ini

            if ((validarNumeroTelefono(cur.getString(cur.getColumnIndex("address")))) &&
                (validarNombrePersona(obtenerNombreContacto(context, cur.getString(cur.getColumnIndex("address"))))) &&
                (fechaDeEnvioSms.equals(fechaFiltroIn)) &&
                ((horaDeEnvioSms.replace(":","").compareTo(horaInicioFiltroIn.replace(":","")) == 0 || //igual a las 14:00
                  horaDeEnvioSms.replace(":","").compareTo(horaInicioFiltroIn.replace(":","")) > 0) && //mayor que 14:00
                 (horaDeEnvioSms.replace(":","").compareTo(horaFinFiltroIn.replace(":","")) == 0 || //igual a las 15:00
                  horaDeEnvioSms.replace(":","").compareTo(horaFinFiltroIn.replace(":","")) < 0))){ //menor que 15:00

                //Devuelve false en caso de que el ususario solo haya enviado un Sms

                if (!eliminarDuplicadosSms(smsEntrantes, numeroTelefono, nombrePersona)){
                    SmsEntrantes smsIn = new SmsEntrantes();

                    smsIn.setNumeroTelefono(formatearNumTelefono.FormateoTelefonoSinPrefijo(numeroTelefono));
                    smsIn.setNombrePersona(nombrePersona);
                    smsIn.setFechaEntradaSms(fechaDeEnvioSms);
                    smsIn.setHoraEntradaSms(horaDeEnvioSms);
                    //smsIn.setFechaEntradaSms(formatearFechaEntrada.FormatearFechaAaaaMmDdCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)));
                    //smsIn.setHoraEntradaSms(formatearHoraEntrada.FormateadorHoraHhMmSs(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));

                    smsEntrantes.add(smsIn);
                }

            }



            ivoio fin*/

            if ((validarNumeroTelefono(cur.getString(cur.getColumnIndex("address")))) &&
                    (validarNombrePersona(obtenerNombreContacto(context, cur.getString(cur.getColumnIndex("address"))))) ){

                //Devuelve false en caso de que el ususario solo haya enviado un Sms

                if (!eliminarDuplicadosSms(smsEntrantes, formatearNumTelefono.FormateoTelefonoSinPrefijo(numeroTelefono), nombrePersona)){
                    SmsEntrantes smsIn = new SmsEntrantes();

                    smsIn.setNumeroTelefono(formatearNumTelefono.FormateoTelefonoSinPrefijo(numeroTelefono));
                    smsIn.setNombrePersona(nombrePersona);
                    smsIn.setFechaEntradaSms(fechaDeEnvioSms);
                    smsIn.setHoraEntradaSms(horaDeEnvioSms);
                    //smsIn.setFechaEntradaSms(formatearFechaEntrada.FormatearFechaAaaaMmDdCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)));
                    //smsIn.setHoraEntradaSms(formatearHoraEntrada.FormateadorHoraHhMmSs(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));

                    smsEntrantes.add(smsIn);
                }

            }

        }

        return smsEntrantes;

    }

    private String obtenerNombreContacto(Context context, String phoneNumber) {

        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));

        Cursor cursor = cr.query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

        if (cursor == null) {
            return null;
        }

        String contactName = USUARIO_NO_DADO_DE_ALTA;

        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

    private boolean validarNumeroTelefono(String numTelefono){

        if ((numTelefono.indexOf(sTextoBuscadoMas) > -1) ||
            (numTelefono.indexOf(sTextoBuscadoSeis) > -1)){
            return true;
        }else{
            return false;
        }

    }

    private boolean validarNombrePersona(String nombrePersona){

        if (!nombrePersona.equals(USUARIO_NO_DADO_DE_ALTA)){
            return true;
        }else{
            return false;
        }

    }

    private boolean eliminarDuplicadosSms(ArrayList<SmsEntrantes> smsEntrantesIn, String numeroTelefonoIn, String nombrePersonaIn){
        /*
         * Devuelve false en caso de que el ususario solo haya enviado un Sms
         */

        //IVOIVO NO DETECTA DUPLICADOS
        boolean extisteDuplicado = false;

        for (int i = 0; i < smsEntrantesIn.size(); i++){
            if (numeroTelefonoIn.equals(smsEntrantesIn.get(i).getNumeroTelefono()) &&
                nombrePersonaIn.equals(smsEntrantesIn.get(i).getNombrePersona())){
                extisteDuplicado = true;
            }
        }

        return extisteDuplicado;
    }
}
