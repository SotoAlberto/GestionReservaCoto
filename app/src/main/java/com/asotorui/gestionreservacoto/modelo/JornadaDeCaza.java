package com.asotorui.gestionreservacoto.modelo;

/**
 * Created by soto8 on 20/04/2017.
 */

public class JornadaDeCaza {

    //DESIASR : METER OBJETOS COTO DE CAZA Y USUARIOS COTO DE CAZA

    private String nombreUsuario;
    private String telefonoUsuario;
    private String nombreCotoCaza;
    private String fechaDeCaza;

    private UsuariosCotoCaza mUsuarioCotoCaza;
    private CotoDeCaza mCotoDeCaza;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public String getNombreCotoCaza() {
        return nombreCotoCaza;
    }

    public void setNombreCotoCaza(String nombreCotoCaza) {
        this.nombreCotoCaza = nombreCotoCaza;
    }

    public String getFechaDeCaza() {
        return fechaDeCaza;
    }

    public void setFechaDeCaza(String fechaDeCaza) {
        this.fechaDeCaza = fechaDeCaza;
    }

    public UsuariosCotoCaza getmUsuarioCotoCaza() {
        return mUsuarioCotoCaza;
    }

    public void setmUsuarioCotoCaza(UsuariosCotoCaza mUsuarioCotoCaza) {
        this.mUsuarioCotoCaza = mUsuarioCotoCaza;
    }

    public CotoDeCaza getmCotoDeCaza() {
        return mCotoDeCaza;
    }

    public void setmCotoDeCaza(CotoDeCaza mCotoDeCaza) {
        this.mCotoDeCaza = mCotoDeCaza;
    }
}
