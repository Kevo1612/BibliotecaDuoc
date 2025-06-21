package com.bibliotecasduocuc.servicio;

/**
 * Clase Menu que representa el menú principal y submenús del sistema de biblioteca.
 * Proporciona opciones para realizar préstamos, devoluciones, importar y exportar datos,
 * y visualizar información de la biblioteca.
 */
public class Menu {

    /**
     * Muestra el menú principal y las opciones disponibles.
     * Permite al usuario seleccionar una acción a realizar en el sistema de biblioteca.
     */
    public void mainMenu(){
        System.out.println("\n=== MENÚ ===");
            System.out.println("1. Realizar préstamo");
            System.out.println("2. Realizar devolución");
            System.out.println("3. Importación de datos");
            System.out.println("4. Exportación de datos");
            System.out.println("5. Visualización de datos");
            System.out.println("6. Salir");
            System.out.print("> Selecciona una opción: ");
    }

    /**
     * Muestra el submenú de importación de datos y las opciones disponibles.
     * Permite cargar libros y usuarios desde archivos o individualmente.
     */
    public void importSubMenu(){
        System.out.println("\n=== CARGA DE DATOS ===");
        System.out.println("1. Carga masiva de libros desde archivo");
        System.out.println("2. Cargar un libro al sistema");
        System.out.println("3. Carga masiva usuarios desde archivo");
        System.out.println("4. Cargar un usuario al sistema");
        System.out.println("5. Volver al menú principal");
        System.out.print("> Selecciona una opción: ");
    }

    /**
     * Muestra el submenú de exportación de datos y las opciones disponibles.
     * Permite exportar libros, usuarios y préstamos a archivos.
     */
    public void exportSubMenu(){
        System.out.println("\n=== EXPORTACIÓN DE DATOS ===");
        System.out.println("1. Exportar libros a archivo");
        System.out.println("2. Exportar usuarios a archivo");
        System.out.println("3. Exportar préstamos a archivo");
        System.out.println("4. Volver al menú principal");
        System.out.print("> Selecciona una opción: ");
    }

    /**
     * Muestra el submenú de visualización de datos y las opciones disponibles.
     * Permite listar libros, usuarios y préstamos de diferentes maneras.
     */
    public void viewSubMenu(){
        System.out.println("\n=== VISUALIZACIÓN DE DATOS ===");
        System.out.println("1. Listar libros (Sin orden)");
        System.out.println("2. Listar libros ordenados por titulo");
        System.out.println("3. Listar libros ordenados por autor");
        System.out.println("4. Listar usuarios (Sin orden)");
        System.out.println("5. Listar usuarios ordenados por ID");
        System.out.println("6. Listar usuarios ordenados por nombre");
        System.out.println("7. Listar usuarios ordenados por apellido");
        System.out.println("8. Listar préstamos");
        System.out.println("9. Volver al menú principal");
        System.out.print("> Selecciona una opción: ");
    }

    /**
     * Muestra un mensaje de salida del menú.
     * Indica que el usuario ha salido del sistema.
     */
    public void exitMenu(){
        System.out.println("\n=== GRACIAS POR USAR EL SISTEMA ===");
        System.out.println("¡Hasta luego!");
    }


}
