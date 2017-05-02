package com.asotorui.gestionreservacoto;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.asotorui.gestionreservacoto.modelo.LlamadasEntrantes;
import com.asotorui.gestionreservacoto.modelo.SmsEntrantes;
import com.asotorui.gestionreservacoto.utilidades.EliminarUsuariosNoValidos;
import com.asotorui.gestionreservacoto.utilidades.FormateadorFechaAaaaMmDd;
import com.asotorui.gestionreservacoto.utilidades.ListaDeLlamadasEntrantes;
import com.asotorui.gestionreservacoto.utilidades.ListaDeSmsEntrantes;
import com.asotorui.gestionreservacoto.utilidades.ProcesoReservaCotoCaza;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ProcesoReservaPoolIntentService extends IntentService {

    private static final String HORA_INICIO_FILTRO = "14:00:00";
    private static final String HORA_FIN_FILTRO = "15:00:00";


    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.asotorui.gestionreservacoto.action.FOO";
    private static final String ACTION_BAZ = "com.asotorui.gestionreservacoto.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.asotorui.gestionreservacoto.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.asotorui.gestionreservacoto.extra.PARAM2";

    private static final int INTERVALO_EJECUCION = 1000 * 60; // 60 segundos

    public static Intent newIntent(Context context){
        return new Intent(context, ProcesoReservaPoolIntentService.class);
    }

    public ProcesoReservaPoolIntentService() {
        super("ProcesoReservaPoolIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ProcesoReservaPoolIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ProcesoReservaPoolIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("DESIASR : Proceso", "Intent recibido :" + intent);

        FormateadorFechaAaaaMmDd formatearFechaFiltro = new FormateadorFechaAaaaMmDd();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        String fechaInicioFiltro = formatearFechaFiltro.FormatearFechaAaaaMmDdCalendar
                (calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
                 calendar.get(Calendar.DAY_OF_MONTH));

        EliminarUsuariosNoValidos elminiarUsuariosNoValidos = new EliminarUsuariosNoValidos();
        ArrayList<SmsEntrantes> listaDeSmsEntrantes = new ArrayList<>();
        ArrayList<SmsEntrantes> listaDeSmsEntrantesDefinitiva = new ArrayList<>();
        ArrayList<LlamadasEntrantes> listaDeLlamadasEntrantes = new ArrayList<>();
        ArrayList<LlamadasEntrantes> listaDeLlamadasEntrantesDefinitiva = new ArrayList<>();

        ListaDeSmsEntrantes mListaSms = new ListaDeSmsEntrantes();
        listaDeSmsEntrantes = mListaSms.getSmsDetails(getApplicationContext(), fechaInicioFiltro, HORA_INICIO_FILTRO, HORA_FIN_FILTRO);
        if (listaDeSmsEntrantes.size() > 0){
            listaDeSmsEntrantesDefinitiva = elminiarUsuariosNoValidos.eliminarUsuariosNoValidosSms(listaDeSmsEntrantes, fechaInicioFiltro);
        }


        ListaDeLlamadasEntrantes mListaLlamadas = new ListaDeLlamadasEntrantes();
        listaDeLlamadasEntrantes = mListaLlamadas.getCallDetails(getApplicationContext(), fechaInicioFiltro, HORA_INICIO_FILTRO, HORA_FIN_FILTRO);

        if (listaDeLlamadasEntrantes.size() > 0) {
            listaDeLlamadasEntrantesDefinitiva = elminiarUsuariosNoValidos.eliminarUsuariosNoValidosLlamadas(listaDeLlamadasEntrantes, fechaInicioFiltro);
        }

        if (listaDeSmsEntrantes.size() > 0 ||
            listaDeLlamadasEntrantes.size() > 0){

            ProcesoReservaCotoCaza procesoReservaCotoCaza = new ProcesoReservaCotoCaza();
            procesoReservaCotoCaza.procesoPrincipalReservaCotoCaza(listaDeLlamadasEntrantesDefinitiva, listaDeSmsEntrantesDefinitiva, fechaInicioFiltro);

        }




        /*if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }*/
    }



    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static void setServiceAlarm(Context context, boolean isOn){
        Intent i = ProcesoReservaPoolIntentService.newIntent(context);

        PendingIntent intentPendiente = PendingIntent.getService(context, 0, i, 0);

        //IVOIVO DESIASR : AlarmManager.RTC establecer alrama en un momento fijo del tiempo

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 01);


        Log.d("DESIASR HORA", calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));

        //IVOIVO

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Log.d("DESIASR TIMEV :", String.valueOf(calendar.getTimeInMillis()));

        //IVOIVO

        /*if (isOn){
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), INTERVALO_EJECUCION, intentPendiente);

            Log.i("DESIASR SCM:", String.valueOf(System.currentTimeMillis()));
            //alarmManager.setRepeating(AlarmManager.RTC,
            //System.currentTimeMillis() , INTERVALO_EJECUCION, intentPendiente);
        } else{
            alarmManager.cancel(intentPendiente);
            intentPendiente.cancel();
        }*/
        //IVOIVO

        //alarmManager.setRepeating(AlarmManager.RTC, calendar.get(Calendar.HOUR),AlarmManager.INTERVAL_DAY, intentPendiente);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, intentPendiente);
        //alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
          //      calendar.get(Calendar.HOUR), AlarmManager.INTERVAL_DAY, intentPendiente);
        Log.d("DESIASR SCM:", String.valueOf(System.currentTimeMillis()));

    }
}
