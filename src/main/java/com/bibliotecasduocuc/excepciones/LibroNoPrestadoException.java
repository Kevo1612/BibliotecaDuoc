package com.bibliotecasduocuc.excepciones;

public class LibroNoPrestadoException extends Exception {
    public LibroNoPrestadoException(String mensaje){
        super(mensaje);
    }
}
