package com.asotorui.gestionreservacoto.modelo;

import android.support.annotation.NonNull;

/**
 * Created by soto8 on 28/04/2017.
 */

public class LlamadasEntrantes implements Comparable<LlamadasEntrantes>{

    private String nombrePersona;
    private String numeroTelefono;
    private String fechaEntradaLlamada;
    private String horaEntradaLlamada;
    private int numeroDeVecesCazadasLlamada;

    public int getNumeroDeVecesCazadasLlamada() {
        return numeroDeVecesCazadasLlamada;
    }

    public void setNumeroDeVecesCazadasLlamada(int numeroDeVecesCazadasLlamada) {
        this.numeroDeVecesCazadasLlamada = numeroDeVecesCazadasLlamada;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getFechaEntradaLlamada() {
        return fechaEntradaLlamada;
    }

    public void setFechaEntradaLlamada(String fechaEntradaLlamada) {
        this.fechaEntradaLlamada = fechaEntradaLlamada;
    }

    public String getHoraEntradaLlamada() {
        return horaEntradaLlamada;
    }

    public void setHoraEntradaLlamada(String horaEntradaLlamada) {
        this.horaEntradaLlamada = horaEntradaLlamada;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(@NonNull LlamadasEntrantes o) {

        int numVecesCazadas = Integer.compare(numeroDeVecesCazadasLlamada, o.numeroDeVecesCazadasLlamada);

        if ( numVecesCazadas != 0 ) {
            return numVecesCazadas;
        }
        if ( horaEntradaLlamada == null ) {
            return o.horaEntradaLlamada == null ? 0 : 1;
        }
        if ( o.horaEntradaLlamada == null ) {
            return 1;
        }
        return horaEntradaLlamada.compareToIgnoreCase(o.horaEntradaLlamada);
    }
}
