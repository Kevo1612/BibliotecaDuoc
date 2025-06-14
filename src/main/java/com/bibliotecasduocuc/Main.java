package com.bibliotecasduocuc;

import java.util.*;

import com.bibliotecasduocuc.modelo.Libro;
import com.bibliotecasduocuc.modelo.Usuario;
import com.bibliotecasduocuc.servicio.Biblioteca;
import com.bibliotecasduocuc.servicio.UtilArchivos;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ ===");
            System.out.println("1. Cargar libros");
            System.out.println("2. Cargar usuarios");
            System.out.println("3. Prestar libro");
            System.out.println("4. Devolver libro");
            System.out.println("5. Listar libros");
            System.out.println("6. Listar usuarios");
            System.out.println("7. Salir");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    try {
                        List<Libro> libros = UtilArchivos.cargarLibros("src/main/java/com/bibliotecasduocuc/data/libros.csv");
                        biblioteca.agregarLibros(libros);
                        System.out.println("Libros cargados correctamente.");
                    } catch (IOException e) {
                        System.out.println("Error al cargar libros: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        Map<String, Usuario> usuarios = UtilArchivos.cargarUsuarios("src/main/java/com/bibliotecasduocuc/data/usuarios.csv");
                        biblioteca.agregarUsuarios(usuarios);
                        System.out.println("Usuarios cargados correctamente.");
                    } catch (IOException e) {
                        System.out.println("Error al cargar usuarios: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("ISBN del libro: ");
                    String isbn = scanner.nextLine();
                    System.out.print("ID del usuario: ");
                    String idUsuario = scanner.nextLine();
                    biblioteca.prestarLibro(isbn, idUsuario);
                    break;
                case 4:
                    System.out.print("ISBN del libro a devolver: ");
                    String isbnDev = scanner.nextLine();
                    biblioteca.devolverLibro(isbnDev);
                    break;
                case 5:
                    biblioteca.listarLibros();
                    break;
                case 6:
                    biblioteca.listarUsuarios();
                    break;
                case 7:
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }
}
