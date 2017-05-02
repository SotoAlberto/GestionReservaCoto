package com.asotorui.gestionreservacoto.utilidades;

import android.telephony.SmsManager;

import com.asotorui.gestionreservacoto.modelo.LlamadasEntrantes;
import com.asotorui.gestionreservacoto.modelo.SingletonListJornadaDeCaza;
import com.asotorui.gestionreservacoto.modelo.SingletonListUsuariosCotoCaza;
import com.asotorui.gestionreservacoto.modelo.SmsEntrantes;
import com.asotorui.gestionreservacoto.modelo.UsuariosCotoCaza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by soto8 on 30/04/2017.
 */

public class ProcesoReservaCotoCaza {



    public ProcesoReservaCotoCaza() {

    }

    public void procesoPrincipalReservaCotoCaza(ArrayList<LlamadasEntrantes> llamadasEntrantesIn, ArrayList<SmsEntrantes> smsEntrantesIn, String fechaDeReserva) {

        ArrayList<SmsEntrantes> listaDefinitivaSms = new ArrayList<>();
        ArrayList<LlamadasEntrantes> listaDefinitivaLlamadas = new ArrayList<>();



        if (smsEntrantesIn.size() > 0){
            listaDefinitivaSms = procesoReservaSms(smsEntrantesIn);

        }

        if (llamadasEntrantesIn.size() > 0){
            listaDefinitivaLlamadas = procesoReservaLlamadas(llamadasEntrantesIn);
        }

        SingletonListJornadaDeCaza singletonListJornadaCotoCaza = SingletonListJornadaDeCaza.get();


        for (int i = 0; i < listaDefinitivaSms.size(); i++){

            //IVOIVO DESASTERIScar
            //envioSmsUsuario("", listaDefinitivaSms.get(i).getNumeroTelefono());
            singletonListJornadaCotoCaza.addSingletonListJornadaDeCaza(
                    listaDefinitivaSms.get(i).getNombrePersona(),
                    listaDefinitivaSms.get(i).getNumeroTelefono(),
                    "LAS CHIQUILLAS",
                    fechaDeReserva);

        }


        for (int j = 0; j < listaDefinitivaLlamadas.size(); j++){
            //IVOIVO DESASTERIScar
            //envioSmsUsuario("", listaDefinitivaLlamadas.get(j).getNumeroTelefono());

            singletonListJornadaCotoCaza.addSingletonListJornadaDeCaza(
                    listaDefinitivaLlamadas.get(j).getNombrePersona(),
                    listaDefinitivaLlamadas.get(j).getNumeroTelefono(),
                    "EL RAMIRO",
                    fechaDeReserva);

        }

        if (smsEntrantesIn.size() > 0 ||
                llamadasEntrantesIn.size() > 0){
            actualizarContadoresDeCaza(listaDefinitivaLlamadas, listaDefinitivaSms);


        }



    }

    private ArrayList<SmsEntrantes> procesoReservaSms(ArrayList<SmsEntrantes> smsEntrantesIn) {

        ArrayList<SmsEntrantes> procesoReservaSmsOut = new ArrayList<>();

        //Proceso para obtener la lista definitiva de Sms
        //Dos cazadores como maximo en el Coto de reserva a traves de Sms

        Collections.sort(smsEntrantesIn);
        int contadorRegistrosSms = 0;
        int tamSms = smsEntrantesIn.size();

        while (contadorRegistrosSms < 2 &&
                !smsEntrantesIn.get(contadorRegistrosSms).getNombrePersona().equals("") &&
                tamSms > 0) {

            procesoReservaSmsOut.add(smsEntrantesIn.get(contadorRegistrosSms));
            contadorRegistrosSms++;

        }

        return procesoReservaSmsOut;
    }

    private ArrayList<LlamadasEntrantes> procesoReservaLlamadas(ArrayList<LlamadasEntrantes> llamadasEntrantesIn) {

        ArrayList<LlamadasEntrantes> procesoReservaLlamadasOut = new ArrayList<>();

        //Proceso para obtener la lista definitiva de Sms
        //Tres cazadores como maximo en el Coto de reserva a traves de llamadas

        Collections.sort(llamadasEntrantesIn);
        int contadorRegistrosLlamadas = 0;
        int tamLlamadas = llamadasEntrantesIn.size();

        while (contadorRegistrosLlamadas < 3 &&
                !llamadasEntrantesIn.get(contadorRegistrosLlamadas).getNombrePersona().equals("") &&
                tamLlamadas > 0) {

            procesoReservaLlamadasOut.add(llamadasEntrantesIn.get(contadorRegistrosLlamadas));
            contadorRegistrosLlamadas++;

        }

        return procesoReservaLlamadasOut;
    }

    private void envioSmsUsuario(String mensajeEnvioIn, String numeroTelefonoIn){

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numeroTelefonoIn, null, mensajeEnvioIn, null, null);
    }

    private void actualizarContadoresDeCaza(ArrayList<LlamadasEntrantes> llamadasEntrantesIn, ArrayList<SmsEntrantes> smsEntrantesIn){

        String[][] usu = new String[1][1];
        usu[0][0] = "";
        SingletonListUsuariosCotoCaza usuariosCotoCaza = SingletonListUsuariosCotoCaza.get(usu, "2017-01-01", "2017-12-31");
        List<UsuariosCotoCaza> usuariosCotoCazaList =  usuariosCotoCaza.getUsuariosCotoCaza();

        for (int i = 0; i < llamadasEntrantesIn.size(); i++){
            for (int j = 0; j < usuariosCotoCazaList.size(); j++){
                if (llamadasEntrantesIn.get(i).getNombrePersona().equals(usuariosCotoCazaList.get(j).getNombreUsuario()) &&
                    llamadasEntrantesIn.get(i).getNumeroTelefono().equals(usuariosCotoCazaList.get(j).getTelefonoUsuario())){

                    int numeroVecesCazadasLlamAux = usuariosCotoCazaList.get(j).getNumeroVecesCazadas();
                    numeroVecesCazadasLlamAux++;

                    usuariosCotoCazaList.get(j).setNumeroVecesCazadas(numeroVecesCazadasLlamAux);
                }
            }
        }

        for (int i = 0; i < smsEntrantesIn.size(); i++){
            for (int j = 0; j < usuariosCotoCazaList.size(); j++){
                if (smsEntrantesIn.get(i).getNombrePersona().equals(usuariosCotoCazaList.get(j).getNombreUsuario()) &&
                    smsEntrantesIn.get(i).getNumeroTelefono().equals(usuariosCotoCazaList.get(j).getTelefonoUsuario())){

                    int numeroVecesCazadasLlamAux = usuariosCotoCazaList.get(j).getNumeroVecesCazadas();
                    numeroVecesCazadasLlamAux++;

                    usuariosCotoCazaList.get(j).setNumeroVecesCazadas(numeroVecesCazadasLlamAux);
                }
            }
        }

    }
}
