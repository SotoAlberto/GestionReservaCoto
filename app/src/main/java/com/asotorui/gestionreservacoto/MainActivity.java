package com.asotorui.gestionreservacoto;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.asotorui.gestionreservacoto.modelo.SingletonListCotoDeCaza;
import com.asotorui.gestionreservacoto.utilidades.FormateadorFechaAaaaMmDd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context contexto = this;
    private String fechaInicioTemporada = "";
    private String fechaFinTemporada ="";
    private ArrayList<Object> mUsuariosCotoCaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Envio de Email", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                lanzarDatePickerEstadisticas("Seleccionar Fecha");

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String[] usuariosContactos;

        if (id == R.id.nav_generar_contactos || id == R.id.nav_penalizar_usuarios) {

            usuariosContactos = obtCargarListaContactos();

            if (id == R.id.nav_generar_contactos){
                if(fechaInicioTemporada.equals("") || fechaFinTemporada.equals("")){
                    Toast toast = Toast.makeText(contexto, "Pendiente introducir fechas de temporada", Toast.LENGTH_SHORT);
                    toast.show();

                }else{

                    Bundle bundleContactosTelef = new Bundle();

                    bundleContactosTelef.putStringArray ("contactosRegistradosList", usuariosContactos);
                    bundleContactosTelef.putString("fechaInicioTemporada", fechaInicioTemporada);
                    bundleContactosTelef.putString("fechaFinTemporada",fechaFinTemporada);

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    ListaGenerarContactosFragment fragmentGenContactos = new ListaGenerarContactosFragment();
                    fragmentGenContactos.setArguments(bundleContactosTelef);

                    fragmentTransaction.replace(R.id.frameLayoutGestionCotosCaza, fragmentGenContactos);
                    fragmentTransaction.commit();
                }

            }else{

                if(fechaInicioTemporada.equals("") || fechaFinTemporada.equals("")){
                    Toast toast = Toast.makeText(contexto, "Pendiente introducir fechas de temporada", Toast.LENGTH_SHORT);
                    toast.show();

                }else{

                    Bundle bundlePenalizarUsuarios = new Bundle();

                    bundlePenalizarUsuarios.putStringArray ("contactosRegistradosList", usuariosContactos);
                    bundlePenalizarUsuarios.putString("fechaInicioTemporada", fechaInicioTemporada);
                    bundlePenalizarUsuarios.putString("fechaFinTemporada",fechaFinTemporada);

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    ListaPenalizarUsuariosFragment fragmentPenalizarUsuarios = new ListaPenalizarUsuariosFragment();
                    fragmentPenalizarUsuarios.setArguments(bundlePenalizarUsuarios);

                    fragmentTransaction.replace(R.id.frameLayoutGestionCotosCaza, fragmentPenalizarUsuarios);
                    fragmentTransaction.commit();
                }

            }


        } else if (id == R.id.nav_generar_periodo_caza) {

            lanzarDatePickerInicio("Fecha Incio Temporada");

        //} else if (id == R.id.nav_penalizar_usuarios) {

        } else if (id == R.id.nav_restringir_cotos) {

            /*Bundle bundlePenalizarUsuarios = new Bundle();

            bundlePenalizarUsuarios.putStringArray ("contactosRegistradosList", usuariosContactos);
            bundlePenalizarUsuarios.putString("fechaInicioTemporada", fechaInicioTemporada);
            bundlePenalizarUsuarios.putString("fechaFinTemporada",fechaFinTemporada);*/

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            RestringirCotosFragment mRestringirCotosFragment = new RestringirCotosFragment();
            //fragmentPenalizarUsuarios.setArguments(bundlePenalizarUsuarios);

            fragmentTransaction.replace(R.id.frameLayoutGestionCotosCaza, mRestringirCotosFragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_estadisticas_uso) {

        } else if (id == R.id.nav_comenzar_aplicacion) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ProcesoReservaCotoFragment mProcesoReservaCotoFragment = new ProcesoReservaCotoFragment();
            //fragmentPenalizarUsuarios.setArguments(bundlePenalizarUsuarios);

            fragmentTransaction.replace(R.id.frameLayoutGestionCotosCaza, mProcesoReservaCotoFragment);
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String[] obtCargarListaContactos() {

        //Genera los contactos a partir de la agenda de contactos
        //Devuelve un String para pasarlo al Frame Layout

        // Query: contactos ordenados por nombre
        Cursor mCursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[] { ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.TYPE },
                ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                        + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL", null,
                ContactsContract.Data.DISPLAY_NAME + " ASC");


        int numContactos = mCursor.getCount();
        String[] contactosTelefono = new String[numContactos];

        mCursor.moveToFirst();

        for (int i = 0; i < numContactos; i++) {

            contactosTelefono[i] = (mCursor.getString(1) + mCursor.getString(2));

            mCursor.moveToNext();
        }

        return contactosTelefono;

    }

    protected void lanzarDatePickerInicio(final String tituloDatePicker) {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                FormateadorFechaAaaaMmDd formateadorFecha = new FormateadorFechaAaaaMmDd();
                fechaInicioTemporada = formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth);

                //Toast.makeText(contexto, tituloDatePicker + " : " + fechaInicioTemporada, Toast.LENGTH_LONG).show();

                lanzarDatePickerFin("Fecha Fin Temporada");
            }
        };

        Calendar mObtenerFecha = Calendar.getInstance();
        long mObtenerFechaLong = new Long(mObtenerFecha.getTimeInMillis());
        mObtenerFecha.setTimeInMillis(mObtenerFechaLong);

        int obtenerDia = mObtenerFecha.get(Calendar.DAY_OF_MONTH);
        int obtenerMes = mObtenerFecha.get(Calendar.MONTH);
        int obtenerAnio = mObtenerFecha.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(contexto, onDateSetListener, obtenerAnio, obtenerMes, obtenerDia);
        datePickerDialog.setTitle(tituloDatePicker);

        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    protected void lanzarDatePickerFin(final String tituloDatePicker) {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                FormateadorFechaAaaaMmDd formateadorFecha = new FormateadorFechaAaaaMmDd();

                if (formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth).compareTo(fechaInicioTemporada) > 0){

                    fechaFinTemporada = formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth);

                    String [] nombreCotoCaza = new String[]{"LAS CHIQUILLAS", "EL RAMIRO"};
                    SingletonListCotoDeCaza singletonListCotoCaza = SingletonListCotoDeCaza.get(nombreCotoCaza, fechaInicioTemporada, fechaFinTemporada);

                    Toast.makeText(contexto, "Fecha de inicio de temporada : " + fechaInicioTemporada + " Fecha de fin de temporada : " + fechaFinTemporada, Toast.LENGTH_LONG).show();

                }else{
                    fechaInicioTemporada = "";
                    fechaFinTemporada = "";
                    Toast.makeText(contexto, "La fecha de fin de temporada es menor o igual que la fecha de inicio de temporada, se debe volver a introducir", Toast.LENGTH_LONG).show();
                }

            }
        };

        Calendar mObtenerFecha = Calendar.getInstance();
        long mObtenerFechaLong = new Long(mObtenerFecha.getTimeInMillis());
        mObtenerFecha.setTimeInMillis(mObtenerFechaLong);

        int obtenerDia = mObtenerFecha.get(Calendar.DAY_OF_MONTH);
        int obtenerMes = mObtenerFecha.get(Calendar.MONTH);
        int obtenerAnio = mObtenerFecha.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(contexto, onDateSetListener, obtenerAnio, obtenerMes, obtenerDia);
        datePickerDialog.setTitle(tituloDatePicker);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    protected void lanzarDatePickerEstadisticas(final String tituloDatePicker) {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                FormateadorFechaAaaaMmDd formateadorFecha = new FormateadorFechaAaaaMmDd();



                /*if (formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth).compareTo(fechaInicioTemporada) > 0){

                    fechaFinTemporada = formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth);

                    String [] nombreCotoCaza = new String[]{"LAS CHIQUILLAS", "EL RAMIRO"};
                    SingletonListCotoDeCaza singletonListCotoCaza = SingletonListCotoDeCaza.get(nombreCotoCaza, fechaInicioTemporada, fechaFinTemporada);

                    Toast.makeText(contexto, "Fecha de inicio de temporada : " + fechaInicioTemporada + " Fecha de fin de temporada : " + fechaFinTemporada, Toast.LENGTH_LONG).show();

                }else{
                    fechaInicioTemporada = "";
                    fechaFinTemporada = "";
                    Toast.makeText(contexto, "La fecha de fin de temporada es menor o igual que la fecha de inicio de temporada, se debe volver a introducir", Toast.LENGTH_LONG).show();
                }*/

            }
        };

        Calendar mObtenerFecha = Calendar.getInstance();
        long mObtenerFechaLong = new Long(mObtenerFecha.getTimeInMillis());
        mObtenerFecha.setTimeInMillis(mObtenerFechaLong);

        int obtenerDia = mObtenerFecha.get(Calendar.DAY_OF_MONTH);
        int obtenerMes = mObtenerFecha.get(Calendar.MONTH);
        int obtenerAnio = mObtenerFecha.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(contexto, onDateSetListener, obtenerAnio, obtenerMes, obtenerDia);
        datePickerDialog.setTitle(tituloDatePicker);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();

    }

    protected void envioEmailInformacion (){

        final String direccionEmail = "gestionCotoDeCaza@gmail.com";
        final String passwordEmail = "ElRamiro_LasChiquillas_2017";

        Session session = null;
        String mensaje = "Prueba de email";

        Properties propertiesEmail = new Properties();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        propertiesEmail.put("mail.smtp.host", "smtp.googlemail.com");
        propertiesEmail.put("mail.smtp.socketFactory.port", "465");
        propertiesEmail.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        propertiesEmail.put("mail.smtp.auth", "true");
        propertiesEmail.put("mail.smtp.port", "465");

        try{
            session = Session.getDefaultInstance(propertiesEmail, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(direccionEmail, passwordEmail);
                }


            });

            if (session != null){
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(direccionEmail));
                message.setSubject("Subject");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("soto80@hotmail.com"));
                message.setContent(mensaje.toString(), "text/html; charset=utf-8");

                Transport.send(message);
            }


        }catch(Exception e){
            e.printStackTrace();
        }

    }


}
