package com.bibliotecasduocuc;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.bibliotecasduocuc.servicio.*;
import com.bibliotecasduocuc.excepciones.*;
import com.bibliotecasduocuc.modelo.*;

/**
 * Clase principal de la aplicación Biblioteca.
 * Esta clase contiene el método run que inicia la aplicación y gestiona el menú
 * principal.
 */
public class App {

    private final Scanner scanner = new Scanner(System.in);
    private final Biblioteca biblioteca = new Biblioteca();
    private final Menu menu = new Menu();

    /**
     * Método principal que inicia la aplicación y gestiona el menú.
     * Carga los datos iniciales y permite al usuario interactuar con el sistema
     * a través de un menú.
     */
    public void run() {
        boolean salir = false;
        // Datos de ejemplo
        biblioteca.cargarDatos();
        // Iteración del menú principal
        while (!salir) {
            menu.mainMenu();
            int opcionMain = leerOpcion();
            switch (opcionMain) {
                case 1 -> realizarPrestamo();
                case 2 -> realizarDevolucion();
                case 3 -> menuImportacion();
                case 4 -> menuExportacion();
                case 5 -> menuVisualizacion();
                case 6 -> {
                    menu.exitMenu();
                    salir = true;
                    break;
                }
                default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
        scanner.close();
    }

    /**
     * Lee una opción del usuario y maneja excepciones de entrada.
     * 
     * @return La opción leída como un entero.
     */
    private int leerOpcion() {
        int op = -1;
        while (true) {
            try {
                op = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scanner.next(); // Limpiar la entrada no válida
            }
        }
        return op;
    }

    /**
     * Realiza el préstamo de un libro a un usuario.
     * Solicita el ISBN del libro y el ID del usuario, y realiza el préstamo.
     */
    private void realizarPrestamo() {
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        System.out.print("ID del usuario: ");
        String idUsuario = scanner.nextLine();
        try {
            biblioteca.prestarLibro(isbn, idUsuario);
        } catch (LibroNoEncontradoException | LibroYaPrestadoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al realizar el préstamo: " + e.getMessage());
        }

    }

    /**
     * Realiza la devolución de un libro.
     */
    private void realizarDevolucion() {
        System.out.print("ISBN del libro a devolver: ");
        String isbnDev = scanner.nextLine();
        try {
            biblioteca.devolverLibro(isbnDev);
        } catch (LibroNoEncontradoException | LibroNoPrestadoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al realizar la devolución: " + e.getMessage());
        }
    }

    /**
     * Muestra el menú de importación de datos y maneja las opciones seleccionadas.
     * Permite importar libros y usuarios desde archivos o agregarlos manualmente.
     */
    private void menuImportacion() {
        menu.importSubMenu();
        int opcionImport = leerOpcion();
        switch (opcionImport) {
            case 1 -> cargaMasiva("libros");
            case 2 -> registraLibro();
            case 3 -> cargaMasiva("usuarios");
            case 4 -> registraUsuario();
            case 5 -> System.out.println("\nVolviendo al menú principal...");
            default -> System.out.println("Opción no válida.");
        }
    }
    /**
     * Muestra el menú de exportación de datos y maneja las opciones seleccionadas.
     * Permite exportar libros, usuarios y préstamos a archivos.
     */
    private void menuExportacion() {
        menu.exportSubMenu();
        int opcionExport = leerOpcion();
        switch (opcionExport) {
            case 1 -> biblioteca.exportLibrosToFile();
            case 2 -> biblioteca.exportUsuariosToFile();
            case 3 -> biblioteca.exportPrestamosToFile();
            case 4 -> System.out.println("\nVolviendo al menú principal...");
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Muestra el menú de visualización de datos y maneja las opciones
     * seleccionadas.
     * Permite listar libros, usuarios y préstamos.
     */
    private void menuVisualizacion() {
        menu.viewSubMenu();
        int opcionView = leerOpcion();
        switch (opcionView) {
            case 1 -> biblioteca.listarLibros();
            case 2 -> biblioteca.listarLibrosOrdenadosPorTitulo();
            case 3 -> biblioteca.listarLibrosOrdenadosPorAutor();
            case 4 -> biblioteca.listarUsuarios();
            case 5 -> biblioteca.listarUsuariosPorID();
            case 6 -> biblioteca.listarUsuariosPorNombre();
            case 7 -> biblioteca.listarUsuariosPorApellido();
            case 8 -> biblioteca.listarPrestamos();
            case 9 -> System.out.println("\nVolviendo al menú principal...");
            default -> System.out.println("Opción no válida.");
        }
    }
    
    /**
     * Realiza una carga masiva de datos desde un archivo.
     * Dependiendo del tipo de datos (usuarios o libros), importa los datos desde
     * el archivo especificado por el usuario.
     *
     * @param tipo El tipo de datos a importar ("usuarios" o "libros").
     *             El usuario debe proporcionar la ruta del archivo correspondiente.
     *             Si el tipo no es válido, se muestra un mensaje de error.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private void cargaMasiva(String tipo){
        System.out.println("Ruta del archivo "+tipo+": ");
        String ruta = scanner.nextLine();
        try {
            if (tipo.equals("usuarios")) {
                biblioteca.importUsuariosToSystem(ruta);
            } else if (tipo.equals("libros")) {
                biblioteca.importLibrosToSystem(ruta);
            } else {
                System.out.println("Tipo de carga masiva no válido.");
            }
        } catch (IOException e) {
            System.out.println("Error al importar datos de "+tipo+": " + e.getMessage());
        }
    }

    /**
     * Registra un nuevo libro en el sistema.
     * Solicita el título, autor e ISBN del libro, y lo agrega al sistema.
     */
    private void registraLibro() {
        System.out.print("Título del libro: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor del libro: ");
        String autor = scanner.nextLine();
        System.out.print("ISBN del libro: ");
        String isbnNuevo = scanner.nextLine();
        biblioteca.agregarLibros(List.of(new Libro(titulo, autor, isbnNuevo)));
        System.out.println("Libro agregado correctamente.");
    }

/**
     * Registra un nuevo usuario en el sistema.
     * Solicita el ID y el nombre del usuario, y lo agrega al sistema.
     */
    private void registraUsuario() {
        System.out.print("ID del usuario: ");
        String idUsuarioNuevo = scanner.nextLine();
        System.out.print("Nombre del usuario: ");
        String nombreUsuario = scanner.nextLine();
        if (idUsuarioNuevo.isEmpty() || nombreUsuario.isEmpty()) {
            System.out.println("ID y nombre de usuario no pueden estar vacíos.");
            return;
        } else {
            biblioteca.agregarUsuarios(
                    Map.of(idUsuarioNuevo, new Usuario(idUsuarioNuevo, nombreUsuario)));
            System.out.println("Usuario agregado correctamente.");
        }
    }

}
