package com.ecodeup.jdbc.Exceptions;

public class NoAutorizadoException extends Exception {
    public NoAutorizadoException(String message) {
        super(message);
    }

    public NoAutorizadoException() {
        super("No tiene la autorización necesaria para realizar esta acción.");
    }}
