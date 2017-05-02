package com.asotorui.gestionreservacoto;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.asotorui.gestionreservacoto.modelo.SingletonListUsuariosCotoCaza;
import com.asotorui.gestionreservacoto.modelo.UsuariosCotoCaza;
import com.asotorui.gestionreservacoto.utilidades.FormateadorContactosTelefonicos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soto8 on 11/04/2017.
 */

public class ListaGenerarContactosFragment extends Fragment {

    private RecyclerView mRecyclerListaGenContactos;
    private ListaContactosAdpater mAdapter;

    private String[] mUsuCotoCaza;
    private String[][] mUsuCotoCazaFormato;
    private String fechaInicioTemporada;
    private String fechaFinTemporada;

    public ListaGenerarContactosFragment(){
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

        View view = inflater.inflate(R.layout.fragment_lista_generar_contactos, container, false);

        mRecyclerListaGenContactos = (RecyclerView) view
                .findViewById(R.id.recyclerListaGenContactos);

        mRecyclerListaGenContactos.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){

        SingletonListUsuariosCotoCaza singletonListUsuariosCotoCaza = SingletonListUsuariosCotoCaza.get(mUsuCotoCazaFormato, fechaInicioTemporada, fechaFinTemporada);
        List<UsuariosCotoCaza> usuariosCotoCazas = singletonListUsuariosCotoCaza.getUsuariosCotoCaza();

        mAdapter = new ListaContactosAdpater(usuariosCotoCazas);
        mRecyclerListaGenContactos.setAdapter(mAdapter);
    }


    private class ListaContactosHolder extends RecyclerView.ViewHolder {
        /*
         *Implemenacion del View Holder para la lista de contactos
         */
        private UsuariosCotoCaza mUsuariosCotoCaza;

        private TextView mNombrePersonaTextView;
        private TextView mTelefonoPersonaTextView;

        public ListaContactosHolder (View itemView){
            super(itemView);

            mNombrePersonaTextView = (TextView) itemView.findViewById(R.id.item_lista_generar_contactos_nombre_text_view);
            mTelefonoPersonaTextView = (TextView) itemView.findViewById(R.id.item_lista_generar_contactos_telefono_text_view);
        }

        public void bindUsuariosCotosCaza(UsuariosCotoCaza usuariosCotoCaza){
            mUsuariosCotoCaza = usuariosCotoCaza;
            mNombrePersonaTextView.setText(mUsuariosCotoCaza.getNombreUsuario());
            mTelefonoPersonaTextView.setText(mUsuariosCotoCaza.getTelefonoUsuario());
        }

    }

    private class ListaContactosAdpater extends RecyclerView.Adapter<ListaContactosHolder>{

        private List<UsuariosCotoCaza> mUsuariosCotoCaza;

        public ListaContactosAdpater(List<UsuariosCotoCaza> usuariosCotosCaza){
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
        public ListaContactosHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.item_lista_generar_contactos, parent, false);

            return new ListaContactosHolder(view);
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
        public void onBindViewHolder(ListaContactosHolder holder, int position) {
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


}
