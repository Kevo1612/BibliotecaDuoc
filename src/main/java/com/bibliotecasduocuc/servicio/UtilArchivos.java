package com.bibliotecasduocuc.servicio;

import java.io.*;
import java.util.*;

import com.bibliotecasduocuc.modelo.*;

/**
 * 
 * Facilita la importación/exportación de datos a la biblioteca desde archivos externos.
 * Proporciona métodos para <ul>
 * <li>Cargar libros y usuarios desde archivos CSV.
 * <li>Exportar libros, usuarios y préstamos a archivos CSV.
 */
public class UtilArchivos {

    /**
     * Carga los libros desde un archivo CSV.
     * El archivo debe tener el formato: titulo,autor,isbn
     * 
     * @param rutaArchivo Ruta del archivo CSV de libros.
     * @return Una lista de objetos Libro cargados desde el archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static List<Libro> cargarLibros(String rutaArchivo) throws IOException {
        List<Libro> libros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Leer y descartar la primera línea (cabecera)
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

    /**
     * Carga los usuarios desde un archivo CSV.
     * El archivo debe tener el formato: id,nombre
     * 
     * @param rutaArchivo Ruta del archivo CSV de usuarios.
     * @return Un mapa de usuarios con el ID como clave y el objeto Usuario como valor.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static Map<String, Usuario> cargarUsuarios(String rutaArchivo) throws IOException {
        Map<String, Usuario> usuarios = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Leer y descartar la primera línea (cabecera)
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    usuarios.put(id, new Usuario(id, nombre));
                }
            }
            return usuarios;
        }
    }

       /**
     * Exporta una lista de libros únicos a un archivo CSV.
     * El archivo se crea en el directorio "exportaciones" con un nombre basado en la fecha actual.
     *
     * @param ejemplares Lista de objetos Ejemplar a exportar.
     * @throws IOException Si ocurre un error al escribir el archivo.
     * @implNote El archivo CSV tendrá la cabecera: Título,Autor,ISBN
     */
    public static void exportarLibros(List<Ejemplar> ejemplares) throws IOException {
        String rutaExportacion = "exportaciones";
        String fechaActual = new java.text.SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String nombreArchivo = "Libros_Exportados" + fechaActual + ".csv";
        File directorio = new File(rutaExportacion);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        File librosExportadosFile = new File(rutaExportacion + File.separator + nombreArchivo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(librosExportadosFile))) {
            bw.write("Título,Autor,ISBN\n"); // Cabecera del CSV
            Set<String> isbnsExportados = new HashSet<>();
            for (Ejemplar ejemplar : ejemplares) {
                String isbn = ejemplar.getLibro().getIsbn();
                if (!isbnsExportados.contains(isbn)) {
                    bw.write(ejemplar.getLibro().getTitulo() + "," + ejemplar.getLibro().getAutor() + "," + isbn + "\n");
                    isbnsExportados.add(isbn);
                }
            }
        }
    }
    /**
     * Exporta una lista de usuarios a un archivo CSV.
     * El archivo se crea en el directorio "exportaciones" con un nombre basado en la fecha actual.
     * 
     * @param usuarios Mapa de usuarios con ID como clave y objeto Usuario como valor.
     * @throws IOException Si ocurre un error al escribir el archivo.
     * @return void
     * @implNote El archivo CSV tendrá la cabecera: ID,Nombre
     */
    public static void exportarUsuarios(Map<String, Usuario> usuarios) throws IOException {
        String rutaExportacion = "exportaciones";
        String fechaActual = new java.text.SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String nombreArchivo = "Usuarios_Exportados" + fechaActual + ".csv";
        File directorio = new File(rutaExportacion);
        // Verificar si el directorio de exportación existe
        if (!directorio.exists()) {
            directorio.mkdirs(); // Crear el directorio si no existe
        }
        // Crear el archivo CSV en el directorio por defecto
        File usuariosExportadosFile = new File(rutaExportacion + File.separator + nombreArchivo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(usuariosExportadosFile))) {
            bw.write("ID,Nombre\n"); // Cabecera del CSV
            for (Usuario usuario : usuarios.values()) {
                bw.write(usuario.getId() + "," + usuario.getNombre() + "\n");
            }
        }
    }

    /**
     * Exporta los préstamos a un archivo CSV.
     * El archivo se crea en el directorio "exportaciones" con un nombre basado en la fecha actual.
     * 
     * @param prestamos Lista de préstamos a exportar.
     * @throws IOException Si ocurre un error al escribir el archivo.
     * @return void
     * @implNote El archivo CSV tendrá la cabecera: ID Usuario,Título Libro,Fecha Préstamo,Fecha Devolución
     */
    public static void exportarPrestamos(List<Prestamo> prestamos) throws IOException {
        String rutaExportacion = "exportaciones";
        String fechaActual = new java.text.SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String nombreArchivo = "Prestamos_Exportados" + fechaActual + ".csv";
        File directorio = new File(rutaExportacion);
        // Verificar si el directorio de exportación existe
        if (!directorio.exists()) {
            directorio.mkdirs(); // Crear el directorio si no existe
        }
        // Crear el archivo CSV en el directorio por defecto
        File prestamosExportadosFile = new File(rutaExportacion + File.separator + nombreArchivo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(prestamosExportadosFile))) {
            bw.write("ID Usuario,Nombre Usuario,Título Libro\n"); // Cabecera del CSV
            for (Prestamo prestamo : prestamos) {
                bw.write(prestamo.getUsuario().getId() + "," + prestamo.getUsuario().getNombre()+ ","+ prestamo.getEjemplar().getLibro().getTitulo()+ "\n");
            }
        }
    }
}
