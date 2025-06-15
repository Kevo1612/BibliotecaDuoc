package com.bibliotecasduocuc.modelo;

/**
 * Clase Usuario que representa un usuario de la biblioteca.
 * Contiene atributos como ID y nombre del usuario.
 */
public class Usuario {
    private String id;
    private String nombre;

    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ")";
    }
}
