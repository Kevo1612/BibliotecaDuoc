package com.bibliotecasduocuc.modelo;

/**
 * Clase Ejemplar que representa un ejemplar de un libro en la biblioteca.
 * Cada ejemplar está asociado a un libro y tiene un estado de disponibilidad.
 * Permite prestar y devolver el ejemplar, y verificar su disponibilidad.
 */

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
