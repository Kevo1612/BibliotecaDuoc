package com.bibliotecasduocuc.modelo;

/**
 * Clase Prestamo que representa un préstamo de un libro a un usuario.
 * Contiene información sobre el usuario y el ejemplar prestado.
*/
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

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "Préstamo: " + ejemplar.getLibro().getTitulo() + " a " + usuario.getNombre();
    }
}
