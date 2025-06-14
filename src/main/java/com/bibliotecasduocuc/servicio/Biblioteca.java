package com.bibliotecasduocuc.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import com.bibliotecasduocuc.modelo.Ejemplar;
import com.bibliotecasduocuc.modelo.Libro;
import com.bibliotecasduocuc.modelo.Prestamo;
import com.bibliotecasduocuc.modelo.Usuario;


public class Biblioteca {
    private List<Ejemplar> ejemplares = new ArrayList<>();
    private List<Prestamo> prestamos = new ArrayList<>();
    private Map<String, Usuario> usuarios = new HashMap<>();

    public void agregarLibros(List<Libro> libros) {
        for (Libro libro : libros) {
            ejemplares.add(new Ejemplar(libro));
        }
    }

    public void agregarUsuarios(Map<String, Usuario> nuevosUsuarios) {
        usuarios.putAll(nuevosUsuarios);
    }

    public void prestarLibro(String isbn, String idUsuario) {
        Usuario usuario = usuarios.get(idUsuario);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        for (Ejemplar ejemplar : ejemplares) {
            if (ejemplar.getLibro().getIsbn().equals(isbn) && ejemplar.isDisponible()) {
                prestamos.add(new Prestamo(usuario, ejemplar));
                System.out.println("Libro prestado correctamente.");
                return;
            }
        }
        System.out.println("No hay ejemplares disponibles de ese libro.");
    }

    public void devolverLibro(String isbn) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getEjemplar().getLibro().getIsbn().equals(isbn)) {
                prestamo.devolver();
                System.out.println("Libro devuelto con éxito.");
                return;
            }
        }
        System.out.println("No se encontró el préstamo.");
    }

    public void listarLibros() {
        for (Ejemplar e : ejemplares) {
            System.out.println(e);
        }
    }

    public void listarUsuarios() {
        for (Usuario u : usuarios.values()) {
            System.out.println(u);
        }
    }
}
