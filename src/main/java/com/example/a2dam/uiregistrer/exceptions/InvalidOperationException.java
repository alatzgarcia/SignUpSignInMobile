package com.example.a2dam.uiregistrer.exceptions;

public class InvalidOperationException extends Exception{
    private static final String MESSAGE = "Error. Tipo de operación"
            + "no reconocido por el sistema.";

    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
