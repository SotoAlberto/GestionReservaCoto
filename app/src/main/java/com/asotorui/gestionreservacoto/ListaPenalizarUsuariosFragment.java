package com.asotorui.gestionreservacoto;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asotorui.gestionreservacoto.modelo.SingletonListUsuariosCotoCaza;
import com.asotorui.gestionreservacoto.modelo.UsuariosCotoCaza;
import com.asotorui.gestionreservacoto.utilidades.FormateadorContactosTelefonicos;
import com.asotorui.gestionreservacoto.utilidades.FormateadorFechaAaaaMmDd;

import java.util.Calendar;
import java.util.List;

/**
 * Created by soto8 on 13/04/2017.
 */

public class ListaPenalizarUsuariosFragment extends Fragment{

    private RecyclerView mRecyclerListaPenalizarUsuarios;
    private ListaPenalizarUsuariosFragment.ListaPenalizarContactosAdapter mAdapter;

    private String[] mUsuCotoCaza;
    private String[][] mUsuCotoCazaFormato;
    private String fechaInicioTemporada;
    private String fechaFinTemporada;

    private static final String DIALOG_DATE = "DialogDate";


    public ListaPenalizarUsuariosFragment() {
        //Constructor vacio
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            mUsuCotoCaza = getArguments().getStringArray("contactosRegistradosList");
            fechaInicioTemporada = getArguments().getString("fechaInicioTemporada");
            fechaFinTemporada = getArguments().getString("fechaFinTemporada");

            FormateadorContactosTelefonicos formateadorContactosTelefonicos = new FormateadorContactosTelefonicos();
            mUsuCotoCazaFormato = formateadorContactosTelefonicos.FormateoListaGenerarContactosFragment(mUsuCotoCaza);

        }
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

        View view = inflater.inflate(R.layout.fragment_lista_penalizar_usuarios, container, false);

        mRecyclerListaPenalizarUsuarios = (RecyclerView) view
                .findViewById(R.id.recyclerListaPenalizarUsuarios);

        mRecyclerListaPenalizarUsuarios.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){

        SingletonListUsuariosCotoCaza singletonListUsuariosCotoCaza = SingletonListUsuariosCotoCaza.get(mUsuCotoCazaFormato, fechaInicioTemporada, fechaFinTemporada);

        List<UsuariosCotoCaza> usuariosCotoCazas = singletonListUsuariosCotoCaza.getUsuariosCotoCaza();

        mAdapter = new ListaPenalizarUsuariosFragment.ListaPenalizarContactosAdapter(usuariosCotoCazas);
        mRecyclerListaPenalizarUsuarios.setAdapter(mAdapter);
    }

    private class ListaPenalizarContactosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /*
         *Implemenacion del View Holder para la lista de contactos
         */
        private UsuariosCotoCaza mUsuariosCotoCaza;

        private TextView mNombrePersonaTextView;
        private TextView mTelefonoPersonaTextView;
        private ImageView mImagenPenalizar;


        public ListaPenalizarContactosHolder (View itemView){
            super(itemView);

            mNombrePersonaTextView = (TextView) itemView.findViewById(R.id.item_lista_penalizar_usuarios_nombre_text_view);
            mTelefonoPersonaTextView = (TextView) itemView.findViewById(R.id.item_lista_penalizar_usuarios_telefono_text_view);
            mImagenPenalizar = (ImageView) itemView.findViewById(R.id.ic_pan_tool_black_24dp);

            //Capturar onClick en la imagen
            mImagenPenalizar.setOnClickListener(this);
        }

        public void bindUsuariosCotosCaza(UsuariosCotoCaza usuariosCotoCaza){
            mUsuariosCotoCaza = usuariosCotoCaza;
            mNombrePersonaTextView.setText(mUsuariosCotoCaza.getNombreUsuario());
            mTelefonoPersonaTextView.setText(mUsuariosCotoCaza.getTelefonoUsuario());
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {

            final Dialog dialogDetPenalizarContacto = new Dialog(getActivity());

            dialogDetPenalizarContacto.setContentView(R.layout.dialog_detalle_penalizar_contactos);
            dialogDetPenalizarContacto.setTitle("Penalizar " + mUsuariosCotoCaza.getNombreUsuario());
            //Bloqueo pulsacion fuera de pantalla
            dialogDetPenalizarContacto.setCancelable(false);

            Button botonAceptar = (Button) dialogDetPenalizarContacto.findViewById(R.id.botonAceptar);
            Button botonCancelar = (Button) dialogDetPenalizarContacto.findViewById(R.id.botonCancelar);

            TextView textViewFechaInicio = (TextView) dialogDetPenalizarContacto.findViewById(R.id.textViewFechaInicio);
            TextView textViewFechaFin = (TextView) dialogDetPenalizarContacto.findViewById(R.id.textViewFechaFin);
            TextView textViewFechaNuevaInicio = (TextView) dialogDetPenalizarContacto.findViewById(R.id.textViewFechaNuevaInicio);

            //IVOIVO CALCULAR FECHA NUEVA DE INICIO : FECHA ACTUAL + 15 DIAS
            textViewFechaInicio.setText(mUsuariosCotoCaza.getFechaInicioCaza());
            textViewFechaFin.setText(mUsuariosCotoCaza.getFechaFinCaza());
            textViewFechaNuevaInicio.setText(mUsuariosCotoCaza.getFechaInicioCaza());

            dialogDetPenalizarContacto.show();

            botonAceptar.setOnClickListener(new View.OnClickListener() {

                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */

                @Override
                public void onClick(View v) {

                    //IVOICO RENOMBRAR Y PONERLO BIEN, EMPEZANDO POR LA NUEVA FECHA DE INICIO
                    //RECUPERAR LA FECHA Y PASARELA AL NUEVO CONTACTO
                    lanzarDatePickerFin("Nueva Fecha para empezar a cazar");
                    dialogDetPenalizarContacto.dismiss();



                }
            });

            botonCancelar.setOnClickListener(new View.OnClickListener() {

                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), mUsuariosCotoCaza.getNombreUsuario(), Toast.LENGTH_SHORT).show();

                    dialogDetPenalizarContacto.dismiss();
                }
            });

        }
    }

    private class ListaPenalizarContactosAdapter extends RecyclerView.Adapter<ListaPenalizarContactosHolder>{

        private List<UsuariosCotoCaza> mUsuariosCotoCaza;

        public ListaPenalizarContactosAdapter(List<UsuariosCotoCaza> usuariosCotosCaza){
            mUsuariosCotoCaza = usuariosCotosCaza;
        }


        /**
         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
         * The new ViewHolder will be used to display items of the adapter using
         * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * @see #onBindViewHolder(ViewHolder, int)
         */
        @Override
        public ListaPenalizarUsuariosFragment.ListaPenalizarContactosHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.item_lista_penalizar_usuarios, parent, false);

            return new ListaPenalizarUsuariosFragment.ListaPenalizarContactosHolder(view);
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         * Note that unlike {@link ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         * <p>
         * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
         * handle efficient partial bind.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */

        @Override
        public void onBindViewHolder(ListaPenalizarUsuariosFragment.ListaPenalizarContactosHolder holder, int position) {
            UsuariosCotoCaza usuariosCotoCaza = mUsuariosCotoCaza.get(position);
            holder.bindUsuariosCotosCaza(usuariosCotoCaza);
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {

            return mUsuariosCotoCaza.size();
        }
    }

    protected void lanzarDatePickerFin(final String tituloDatePicker) {

        final String[] nuevaFecha = new String[1];
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                FormateadorFechaAaaaMmDd formateadorFecha = new FormateadorFechaAaaaMmDd();

                nuevaFecha[0] = formateadorFecha.FormatearFechaAaaaMmDdCalendar(year, monthOfYear, dayOfMonth);
                Toast.makeText(getActivity(), tituloDatePicker + " : " + nuevaFecha[0], Toast.LENGTH_LONG).show();

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
