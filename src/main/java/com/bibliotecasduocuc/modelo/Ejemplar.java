package com.bibliotecasduocuc.modelo;

public class Ejemplar {
    private Libro libro;
    private boolean disponible = true;

    public Ejemplar(Libro libro) {
        this.libro = libro;
    }

    public Libro getLibro() { return libro; }
    public boolean isDisponible() { return disponible; }

    public void prestar() { disponible = false; }
    public void devolver() { disponible = true; }

    @Override
    public String toString() {
        return libro.toString() + (disponible ? " [Disponible]" : " [Prestado]");
    }
}
