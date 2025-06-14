package com.bibliotecasduocuc.servicio;

import java.io.*;
import java.util.*;

import com.bibliotecasduocuc.modelo.Libro;
import com.bibliotecasduocuc.modelo.Usuario;

public class UtilArchivos {
    public static List<Libro> cargarLibros(String rutaArchivo) throws IOException {
        List<Libro> libros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String titulo = partes[0].trim();
                    String autor = partes[1].trim();
                    String isbn = partes[2].trim();
                    libros.add(new Libro(titulo, autor, isbn));
                }
            }
        }
        return libros;
    }

    public static Map<String, Usuario> cargarUsuarios(String rutaArchivo) throws IOException {
        Map<String, Usuario> usuarios = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    usuarios.put(id, new Usuario(id, nombre));
                }
            }
        }
        return usuarios;
    }
}
