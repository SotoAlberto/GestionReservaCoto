package com.asotorui.gestionreservacoto.utilidades;

/**
 * Created by soto8 on 12/04/2017.
 */

public class FormateadorContactosTelefonicos {

    // +34 655145981
    static final String sTextoBuscadoMas = "+";
    // 655145981
    static final String sTextoBuscadoSeis = "6";

    public FormateadorContactosTelefonicos() {

    }

    public String[][] FormateoListaGenerarContactosFragment (String[] contactosDuplicadosIn){

        /**
         * Formato devuelto :
         * usuariosContactosOut[i][0] Nombre de persona
         * usuariosContactosOut[i][1] Telefono de personas sin espacios y sin +34
         */

        int tamanioListaOutAux = calcularTamSinDuplicados(contactosDuplicadosIn);

        String[][] usuContactosOut = new String[tamanioListaOutAux][2];
        usuContactosOut = FormatearCampos(contactosDuplicadosIn, tamanioListaOutAux);

        return usuContactosOut;
    }

    public String FormateoTelefonoSinPrefijo(String numeroTelefonoIn){

        /*
         * Formato de entrada : +34655145981
         * Formateo de salida : 655145981
         */

        String numeroTelefonoFormateado;

        if (numeroTelefonoIn.indexOf(sTextoBuscadoMas) > -1) {
            numeroTelefonoFormateado = numeroTelefonoIn.substring(numeroTelefonoIn.indexOf(sTextoBuscadoMas) + 3);
        }else{
            numeroTelefonoFormateado = numeroTelefonoIn;
        }

        return numeroTelefonoFormateado;
    }

    private int calcularTamSinDuplicados(String[] calcularTamSinDuplicadosIn){

        /*
         * Calcula el tamaño de la lista de contactos sin duplicados
         * y seleccionando los siguientes numeros
         *
         * +34610330380
         * +34 680 15 97 18
         * 610330380
         * 680 15 97 18
         *
         *      Entrada : String[]
         *      Salida  : int
         */

        int tamanioLista = 0;
        String nombrePersona = "";
        String nombreFormateado = "";


        for (int i = 0; i < calcularTamSinDuplicadosIn.length; i++) {

            if (calcularTamSinDuplicadosIn[i].indexOf(sTextoBuscadoMas) > -1) {
                nombreFormateado = calcularTamSinDuplicadosIn[i].substring(0, calcularTamSinDuplicadosIn[i].indexOf(sTextoBuscadoMas));

                if(!nombreFormateado.equals(nombrePersona)){
                    tamanioLista++;

                    nombrePersona = nombreFormateado;

                }

            }else if (calcularTamSinDuplicadosIn[i].indexOf(sTextoBuscadoSeis) > -1) {
                nombreFormateado = calcularTamSinDuplicadosIn[i].substring(0, calcularTamSinDuplicadosIn[i].indexOf(sTextoBuscadoSeis));

                if(!nombreFormateado.equals(nombrePersona)){
                    tamanioLista++;

                    nombrePersona = nombreFormateado;

                }
            }

        }

        return tamanioLista;

    }

    private String[][] FormatearCampos(String[] contactosSinFormateoIn, int tamSinDuplicadosIn) {

        /**
         * Se formatean los campos :
         *
         * [i][0] Nombre de Persona
         * [i][1] Telefono Persona : Se formatea si espacios y sin prefijos
         *
         */

        String[][] usuarioFormatearCamposOut = new String[tamSinDuplicadosIn][2];
        int auxTamSinDuplicadosIn = 0;

        String nombrePersona = "";
        String nombreFormateado ="";
        String numeroTelefonoAux = "";

        for (int i = 0; i < contactosSinFormateoIn.length; i++) {

            if (contactosSinFormateoIn[i].indexOf(sTextoBuscadoMas) > -1) {

                /*
                 * Se ha encontrado un +34610330380
                 * Se ha encontrado un +34 680 15 97 18
                 *
                 */

                nombreFormateado = contactosSinFormateoIn[i].substring(0, contactosSinFormateoIn[i].indexOf(sTextoBuscadoMas));

                if(!nombreFormateado.equals(nombrePersona)){
                    usuarioFormatearCamposOut[auxTamSinDuplicadosIn][0] = nombreFormateado;
                    numeroTelefonoAux = contactosSinFormateoIn[i].substring(contactosSinFormateoIn[i].indexOf(sTextoBuscadoMas) + 3).trim();
                    numeroTelefonoAux = numeroTelefonoAux.replace(" ","");

                    usuarioFormatearCamposOut[auxTamSinDuplicadosIn][1] = numeroTelefonoAux;

                    auxTamSinDuplicadosIn++;

                    nombrePersona = nombreFormateado;

                }

            } else if (contactosSinFormateoIn[i].indexOf(sTextoBuscadoSeis) > -1) {

                /*
                 * Se ha encontrado un 610330380
                 * Se ha encontrado un 680 15 97 18
                 *
                 */

                nombreFormateado = contactosSinFormateoIn[i].substring(0, contactosSinFormateoIn[i].indexOf(sTextoBuscadoSeis));

                if (!nombreFormateado.equals(nombrePersona)) {
                    usuarioFormatearCamposOut[auxTamSinDuplicadosIn][0] = nombreFormateado;
                    numeroTelefonoAux = contactosSinFormateoIn[i].substring(contactosSinFormateoIn[i].indexOf(sTextoBuscadoSeis)).trim();
                    numeroTelefonoAux = numeroTelefonoAux.replace(" ","");

                    usuarioFormatearCamposOut[auxTamSinDuplicadosIn][1] = numeroTelefonoAux;

                    auxTamSinDuplicadosIn++;

                    nombrePersona = nombreFormateado;

                }
            }
        }
        return usuarioFormatearCamposOut;
    }
}


