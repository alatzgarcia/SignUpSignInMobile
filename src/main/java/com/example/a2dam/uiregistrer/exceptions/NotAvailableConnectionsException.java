package com.example.a2dam.uiregistrer.exceptions;

public class NotAvailableConnectionsException extends Exception {
    private static final String MESSAGE = "Error. El servidor de la base"
            + "de datos no puede atender su petición en este momento."
            + "Inténtelo más tarde.";

    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
