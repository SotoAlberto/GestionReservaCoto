package com.asotorui.gestionreservacoto;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.asotorui.gestionreservacoto.modelo.CotoDeCaza;
import com.asotorui.gestionreservacoto.modelo.SingletonListCotoDeCaza;
import com.asotorui.gestionreservacoto.utilidades.FormateadorFechaAaaaMmDd;

import java.util.Calendar;
import java.util.List;

/**
 * Created by soto8 on 15/04/2017.
 */

public class RestringirCotosFragment extends Fragment{

    TextView mTextViewFechaInicioRamiro;
    TextView mTextViewFechaFinRamiro;
    TextView mTextViewFechaInicioChiquillas;
    TextView mTextViewFechaFinChiquillas;

    Button mBotonModificarFecIniRamiro;
    Button mBotonModificarFecIniChiquillas;

    public RestringirCotosFragment() {
        //Constructor vacio
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restringir_cotos, container, false);

        SingletonListCotoDeCaza singletonListCotoCaza = SingletonListCotoDeCaza.get(new String[]{"", ""}, "", "");
        List<CotoDeCaza> cotoDeCaza = singletonListCotoCaza.getCotoCaza();

        mTextViewFechaInicioRamiro = (TextView) view.findViewById(R.id.textViewFechaInicioRamiro);
        mTextViewFechaFinRamiro = (TextView) view.findViewById(R.id.textViewFechaFinRamiro);
        mTextViewFechaInicioChiquillas = (TextView) view.findViewById(R.id.textViewFechaInicioChiquillas);
        mTextViewFechaFinChiquillas = (TextView) view.findViewById(R.id.textViewFechaFinChiquillas);

        String fehaInicioRamiro = "";
        String fechaFinRamiro = "";
        String fechaInicioChiquillas = "";
        String fechaFinChiquillas = "";

        for (int i = 0; i < cotoDeCaza.size(); i++) {
            if (cotoDeCaza.get(i).getNombreCotoCaza().equals("EL RAMIRO")) {
                fehaInicioRamiro = cotoDeCaza.get(i).getFechaInicioCaza();
                fechaFinRamiro = cotoDeCaza.get(i).getFechaFinCaza();
            } else if (cotoDeCaza.get(i).getNombreCotoCaza().equals("LAS CHIQUILLAS")) {
                fechaInicioChiquillas = cotoDeCaza.get(i).getFechaInicioCaza();
                fechaFinChiquillas = cotoDeCaza.get(i).getFechaFinCaza();
            }
        }

        mTextViewFechaInicioRamiro.setText(fehaInicioRamiro);
        mTextViewFechaFinRamiro.setText(fechaFinRamiro);
        mTextViewFechaInicioChiquillas.setText(fechaInicioChiquillas);
        mTextViewFechaFinChiquillas.setText(fechaFinChiquillas);

        mBotonModificarFecIniRamiro = (Button) view.findViewById(R.id.botonModificarFecIniRamiro);
        mBotonModificarFecIniChiquillas = (Button) view.findViewById(R.id.botonModificarFecIniChiquillas);

        mBotonModificarFecIniRamiro.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                lanzarDatePickerRamiro("EL RAMIRO");

            }
        });

        mBotonModificarFecIniChiquillas.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                lanzarDatePickerChiquillas("LAS CHIQUILLAS");

            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void lanzarDatePickerRamiro(final String tituloDatePicker) {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                FormateadorFechaAaaaMmDd formateadorFecha = new FormateadorFechaAaaaMmDd();

                SingletonListCotoDeCaza singletonListCotoCaza = SingletonListCotoDeCaza.get(new String[]{"", ""}, "", "");
                List<CotoDeCaza> cotoDeCazaRamiro = singletonListCotoCaza.getCotoCaza();

                for (int i = 0; i < cotoDeCazaRamiro.size(); i++) {
                    if (cotoDeCazaRamiro.get(i).getNombreCotoCaza().equals("EL RAMIRO")) {
                        if(cotoDeCazaRamiro.get(i).getFechaInicioCaza().compareTo(formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth)) < 0){
                            cotoDeCazaRamiro.get(i).setFechaInicioCaza(formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth));

                            mTextViewFechaInicioRamiro.setText(cotoDeCazaRamiro.get(i).getFechaInicioCaza());

                            Toast.makeText(getActivity(), "Modificacion correcta para EL RAMIRO : " + formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth), Toast.LENGTH_LONG).show();


                        }else{
                            Toast.makeText(getActivity(), "Fecha seleccionada menor o igual que fecha de inicio. Fecha Inicio : " + cotoDeCazaRamiro.get(i).getFechaInicioCaza() + " - Fecha Seleccionada : " + formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth) , Toast.LENGTH_LONG).show();

                        }

                    }

                }

            }
        };

        Calendar mObtenerFecha = Calendar.getInstance();
        long mObtenerFechaLong = new Long(mObtenerFecha.getTimeInMillis());
        mObtenerFecha.setTimeInMillis(mObtenerFechaLong);

        int obtenerDia = mObtenerFecha.get(Calendar.DAY_OF_MONTH);
        int obtenerMes = mObtenerFecha.get(Calendar.MONTH);
        int obtenerAnio = mObtenerFecha.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, obtenerAnio, obtenerMes, obtenerDia);
        datePickerDialog.setTitle(tituloDatePicker);

        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void lanzarDatePickerChiquillas(final String tituloDatePicker) {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                FormateadorFechaAaaaMmDd formateadorFecha = new FormateadorFechaAaaaMmDd();

                SingletonListCotoDeCaza singletonListCotoCaza = SingletonListCotoDeCaza.get(new String[]{"", ""}, "", "");
                List<CotoDeCaza> cotoDeCazaChiquillas = singletonListCotoCaza.getCotoCaza();

                for (int i = 0; i < cotoDeCazaChiquillas.size(); i++) {
                    if (cotoDeCazaChiquillas.get(i).getNombreCotoCaza().equals("LAS CHIQUILLAS")) {

                        if(cotoDeCazaChiquillas.get(i).getFechaInicioCaza().compareTo(formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth)) < 0){
                            cotoDeCazaChiquillas.get(i).setFechaInicioCaza(formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth));

                            cotoDeCazaChiquillas.get(i).setFechaInicioCaza(formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth));

                            mTextViewFechaInicioChiquillas.setText(cotoDeCazaChiquillas.get(i).getFechaInicioCaza());

                            Toast.makeText(getActivity(), "Modificacion correcta para LAS CHIQUILLAS : " + formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth), Toast.LENGTH_LONG).show();


                        }else{
                            Toast.makeText(getActivity(), "Fecha seleccionada menor o igual que fecha de inicio. Fecha Inicio : " + cotoDeCazaChiquillas.get(i).getFechaInicioCaza() + " - Fecha Seleccionada : " + formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth) , Toast.LENGTH_LONG).show();

                        }

                    }

                }

            }
        };

        Calendar mObtenerFecha = Calendar.getInstance();
        long mObtenerFechaLong = new Long(mObtenerFecha.getTimeInMillis());
        mObtenerFecha.setTimeInMillis(mObtenerFechaLong);

        int obtenerDia = mObtenerFecha.get(Calendar.DAY_OF_MONTH);
        int obtenerMes = mObtenerFecha.get(Calendar.MONTH);
        int obtenerAnio = mObtenerFecha.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, obtenerAnio, obtenerMes, obtenerDia);
        datePickerDialog.setTitle(tituloDatePicker);

        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }
}
