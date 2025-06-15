package com.bibliotecasduocuc.servicio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.bibliotecasduocuc.excepciones.*;
import com.bibliotecasduocuc.modelo.*;

/**
 * Clase Biblioteca que representa una biblioteca con funcionalidades para
 * gestionar libros, usuarios y préstamos.
 * Permite agregar libros y usuarios, prestar y devolver libros, importar y
 * exportar datos, y listar información.
 */
public class Biblioteca {
    // Atributos de la biblioteca
    private List<Ejemplar> ejemplares = new ArrayList<>();
    private List<Prestamo> prestamos = new ArrayList<>();
    private Map<String, Usuario> usuarios = new HashMap<>();

    // Métodos de la biblioteca

    /**
     * Método para agregar una lista de libros a la biblioteca.
     * Cada libro se convierte en un ejemplar y se agrega a la lista de ejemplares.
     * 
     * @param libros Lista de libros a agregar.
     */
    public void agregarLibros(List<Libro> libros) {
        for (Libro libro : libros) {
            ejemplares.add(new Ejemplar(libro));
        }
    }

    /**
     * Método para agregar un mapa de usuarios a la biblioteca.
     * 
     * @param nuevosUsuarios Lista de usuarios a agregar.
     */
    public void agregarUsuarios(Map<String, Usuario> nuevosUsuarios) {
        usuarios.putAll(nuevosUsuarios);
    }

    /**
     * Método para prestar un libro basado en su ISBN.
     * Busca un ejemplar disponible del libro y lo asocia con el usuario.
     * 
     * @param isbn      El ISBN del libro a prestar.
     * @param idUsuario El ID del usuario que solicita el préstamo.
     * @throws LibroNoEncontradoException
     * @throws LibroYaPrestadoException
     */
    public void prestarLibro(String isbn, String idUsuario)
            throws LibroNoEncontradoException, LibroYaPrestadoException {
        // Verificar si el usuario existe
        Usuario usuario = usuarios.get(idUsuario);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        // Buscar el ejemplar del libro por ISBN
        Ejemplar ejemplarEncontrado = null;
        for (Ejemplar ejemplar : ejemplares) {
            if (ejemplar.getLibro().getIsbn().equals(isbn)) {
                ejemplarEncontrado = ejemplar;
                break;
            }

        }

        // Excepciones
        if (ejemplarEncontrado == null) {
            throw new LibroNoEncontradoException("No se encontró un libro con el ISBN proporcionado.");
        }
        if (!ejemplarEncontrado.isDisponible()) {
            throw new LibroYaPrestadoException("El libro no está disponible para préstamo.");
        }

        // Agregar el préstamo a la lista
        prestamos.add(new Prestamo(usuario, ejemplarEncontrado));
        // Marcar el ejemplar como prestado
        ejemplarEncontrado.prestar();
        System.out.println("Libro prestado correctamente.");
    }

    /**
     * Método para devolver un libro basado en su ISBN.
     * Busca el préstamo correspondiente y lo marca como devuelto.
     * 
     * @param isbn El ISBN del libro a devolver.
     * @throws LibroNoEncontradoException
     * @throws LibroNoPrestadoException
     */
    public void devolverLibro(String isbn) throws LibroNoEncontradoException, LibroNoPrestadoException {
        // Buscar el préstamo del libro por ISBN
        Prestamo prestamoEncontrado = null;
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getEjemplar().getLibro().getIsbn().equals(isbn)) {
                prestamoEncontrado = prestamo;
                break;
            }
        }
        // Excepciones
        if (prestamoEncontrado == null) {
            // Busca el libro en la biblioteca
            boolean libroExist = ejemplares.stream().anyMatch(e -> e.getLibro().getIsbn().equals(isbn));

            if (!libroExist) {
                throw new LibroNoEncontradoException("No se encontró un libro con el ISBN proporcionado.");
            } else {
                throw new LibroNoPrestadoException("Actualmente este libro no se registra con prestamo.");
            }
        }

        // Marcar el ejemplar como disponible
        prestamoEncontrado.getEjemplar().devolver();
        // Eliminar el préstamo de la lista
        prestamos.remove(prestamoEncontrado);
        System.out.println("Libro devuelto correctamente.");
    }

    /**
     * Método para cargar los datos iniciales de la biblioteca desde archivos.
     * Carga libros y usuarios desde archivos CSV predefinidos.
     */
    public void cargarDatos() {
        // Rutas de los archivos de libros y usuarios
        String rutaLibros = "src/main/java/com/bibliotecasduocuc/data/libros.csv";
        String rutaUsuarios = "src/main/java/com/bibliotecasduocuc/data/usuarios.csv";
        // Cargar libros y usuarios desde archivos
        silentImportLibros(rutaLibros);
        silentUserImport(rutaUsuarios);
    }

    /**
     * Este método carga los libros desde un archivo y los agrega al sistema.
     * 
     * @param rutaArchivo Ruta del archivo desde donde se importarán los libros.
     */
    public void importLibrosToSystem(String rutaArchivo) {
        try {
            File file = new File(rutaArchivo);
            if (!file.exists()) {
                System.out.println("El archivo no existe: " + rutaArchivo);
                return;
            }

            List<Libro> libros = UtilArchivos.cargarLibros(rutaArchivo);
            if (libros == null || libros.isEmpty()) {
                System.out.println("No se encontraron datos en el archivo.");
                return;
            }
            // Mapa para buscar libros existentes por ISBN
            Set<String> isbnsExistentes = new HashSet<>();
            for (Ejemplar e : ejemplares) {
                isbnsExistentes.add(e.getLibro().getIsbn());
            }
            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros en el archivo.");
                return;
            }

            int agregados = 0;
            for (Libro libro : libros) {
                if (!isbnsExistentes.contains(libro.getIsbn())) {
                    ejemplares.add(new Ejemplar(libro));
                    isbnsExistentes.add(libro.getIsbn());
                    agregados++;
                } else {
                    System.out.println("No se agregó el libro. Ya existe un ejemplar con ISBN: " + libro.getIsbn());
                }
            }
            System.out.println("Ejemplares importados: " + agregados);
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al importar libros: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al importar libros: " + e.getMessage());
        }
    }

    public void silentImportLibros(String rutaArchivo) {
        try {

            List<Libro> libros = UtilArchivos.cargarLibros(rutaArchivo);
            Set<String> isbnsExistentes = new HashSet<>();

            for (Ejemplar e : ejemplares) {
                isbnsExistentes.add(e.getLibro().getIsbn());
            }
            for (Libro libro : libros) {
                if (!isbnsExistentes.contains(libro.getIsbn())) {
                    ejemplares.add(new Ejemplar(libro));
                    isbnsExistentes.add(libro.getIsbn());
                } else {
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al importar libros: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al importar libros: " + e.getMessage());
        }
    }

    /**
     * Este método importa usuarios desde un archivo y los agrega al sistema.
     * 
     * @param rutaArchivo Ruta del archivo desde donde se importarán los usuarios.
     */
    public void importUsuariosToSystem(String rutaArchivo) throws IOException{
        try {
            File file = new File(rutaArchivo);
            if (!file.exists()) {
                System.out.println("El archivo no existe: " + rutaArchivo);
                return;
            }
            // Mapa para buscar usuarios existentes por ID
            Set<String> idsExistentes = new HashSet<>();
            for (Usuario u : usuarios.values()) {
                idsExistentes.add(u.getId());
            }
            // Cargar los usuarios desde el archivo
            Map<String, Usuario> nuevosUsuarios = UtilArchivos.cargarUsuarios(rutaArchivo);
            // comparar los nuevos usuarios con los existentes
            if (nuevosUsuarios == null || nuevosUsuarios.isEmpty()) {
                System.out.println("No se encontraron datos en el archivo.");
                // Si no hay usuarios en el archivo, no se agrega nada
                return;
            }
            // Agregar los nuevos usuarios al mapa de usuarios
            agregarUsuarios(nuevosUsuarios);
            System.out.println("Usuarios importados correctamente.");

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al importar usuarios: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al importar usuarios: " + e.getMessage());
        }
    }

    public void silentUserImport(String rutaArchivo) {
        try {
            Map<String, Usuario> nuevosUsuarios = UtilArchivos.cargarUsuarios(rutaArchivo);
            if (nuevosUsuarios == null || nuevosUsuarios.isEmpty()) {
                System.out.println("No se encontraron datos en el archivo.");
                return;
                
            } else {
                agregarUsuarios(nuevosUsuarios);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al importar usuarios: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al importar usuarios: " + e.getMessage());
        }
    }

    /**
     * Este método exporta los libros registrados en la biblioteca a un archivo CSV.
     * El archivo se crea en el directorio "exportaciones" con un nombre basado en
     * la fecha actual.
     */
    public void exportLibrosToFile() {
        try {
            UtilArchivos.exportarLibros(ejemplares);
            System.out.println("Libros exportados correctamente.");
        } catch (Exception e) {
            System.out.println("Error al exportar libros: " + e.getMessage());
        }
    }

    /**
     * Este método exporta los préstamos registrados en la biblioteca a un archivo
     * CSV.
     * El archivo se crea en el directorio "exportaciones" con un nombre basado en
     * la fecha actual.
     */
    public void exportPrestamosToFile() {
        try {
            UtilArchivos.exportarPrestamos(prestamos);
            System.out.println("Préstamos exportados correctamente.");
        } catch (Exception e) {
            System.out.println("Error al exportar préstamos: " + e.getMessage());
        }
    }

    /**
     * Este método exporta los usuarios registrados en la biblioteca a un archivo
     * CSV.
     * El archivo se crea en el directorio "exportaciones" con un nombre basado en
     * la fecha actual.
     */
    public void exportUsuariosToFile() {
        try {
            UtilArchivos.exportarUsuarios(usuarios);
            System.out.println("Usuarios exportados correctamente.");
        } catch (Exception e) {
            System.out.println("Error al exportar usuarios: " + e.getMessage());
        }
    }

    /**
     * Este método lista todos los libros registrados en la biblioteca.
     * Muestra el título, autor e ISBN de cada libro.
     */
    public void listarLibros() {
        System.out.println("Lista de libros:");
        System.out.println("-".repeat(90));
        System.out.printf("%-45s %-25s %-20s%n", "Título", "Autor", "ISBN");
        System.out.println("-".repeat(90));
        if (ejemplares.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        } else {
            for (Ejemplar e : ejemplares) {
                System.out.printf("%-45s %-25s %-20s%n",
                        e.getLibro().getTitulo(),
                        e.getLibro().getAutor(),
                        e.getLibro().getIsbn());
            }
        }
        System.out.println("-".repeat(90));
    }

    /**
     * Este método lista todos los usuarios registrados en la biblioteca.
     * Muestra el ID y nombre de cada usuario.
     */
    public void listarUsuarios() {
        System.out.println("Lista de usuarios:");
        System.out.println("-".repeat(50));
        System.out.printf("%-15s %-35s%n", "ID", "Nombre Usuario");
        System.out.println("-".repeat(50));
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario u : usuarios.values()) {
                System.out.printf("%-15s %-35s%n", u.getId(), u.getNombre());
            }
        }
        System.out.println("-".repeat(50));
    }

    /**
     * Este método lista todos los préstamos registrados en la biblioteca.
     * Muestra el ID del usuario, nombre del usuario y título del libro prestado.
     */
    public void listarPrestamos() {
        System.out.println("Lista de préstamos:");
        System.out.println("-".repeat(95));
        System.out.printf("%-15s %-35s %-45s%n", "ID Usuario", "Nombre Usuario", "Título del Libro");
        System.out.println("-".repeat(95));
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
        } else {
            for (Prestamo p : prestamos) {
                System.out.printf("%-15s %-35s %-45s%n",
                        p.getUsuario().getId(),
                        p.getUsuario().getNombre(),
                        p.getEjemplar().getLibro().getTitulo());

            }
        }
        System.out.println("-".repeat(95));
    }
}
