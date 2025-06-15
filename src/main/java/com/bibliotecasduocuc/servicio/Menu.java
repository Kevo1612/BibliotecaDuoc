package com.bibliotecasduocuc.servicio;

/**
 * Clase Menu que representa el menú principal y submenús del sistema de biblioteca.
 * Proporciona opciones para realizar préstamos, devoluciones, importar y exportar datos,
 * y visualizar información de la biblioteca.
 */
public class Menu {
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

    public void importSubMenu(){
        System.out.println("\n=== CARGA DE DATOS ===");
        System.out.println("1. Carga masiva de libros desde archivo");
        System.out.println("2. Cargar un libro al sistema");
        System.out.println("3. Carga masiva usuarios desde archivo");
        System.out.println("4. Cargar un usuario al sistema");
        System.out.println("5. Volver al menú principal");
        System.out.print("> Selecciona una opción: ");
    }

    public void exportSubMenu(){
        System.out.println("\n=== EXPORTACIÓN DE DATOS ===");
        System.out.println("1. Exportar libros a archivo");
        System.out.println("2. Exportar usuarios a archivo");
        System.out.println("3. Exportar préstamos a archivo");
        System.out.println("4. Volver al menú principal");
        System.out.print("> Selecciona una opción: ");
    }

    public void viewSubMenu(){
        System.out.println("\n=== VISUALIZACIÓN DE DATOS ===");
        System.out.println("1. Listar libros");
        System.out.println("2. Listar usuarios");
        System.out.println("3. Listar préstamos");
        System.out.println("4. Volver al menú principal");
        System.out.print("> Selecciona una opción: ");
    }

    public void exitMenu(){
        System.out.println("\n=== GRACIAS POR USAR EL SISTEMA ===");
        System.out.println("¡Hasta luego!");
    }


}
