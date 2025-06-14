package com.bibliotecasduocuc.modelo;

public class Prestamo {
    private Usuario usuario;
    private Ejemplar ejemplar;

    public Prestamo(Usuario usuario, Ejemplar ejemplar) {
        this.usuario = usuario;
        this.ejemplar = ejemplar;
        ejemplar.prestar();
    }

    public void devolver() {
        ejemplar.devolver();
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    @Override
    public String toString() {
        return "Préstamo: " + ejemplar.getLibro().getTitulo() + " a " + usuario.getNombre();
    }
}
