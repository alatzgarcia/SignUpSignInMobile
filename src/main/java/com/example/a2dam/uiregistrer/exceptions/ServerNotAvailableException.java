package com.example.a2dam.uiregistrer.exceptions;

public class ServerNotAvailableException extends Exception{
    private static final String MESSAGE = "Error. El servidor no se encuentra"
            + "disponible en estos momentos. Por favor, vuelva a intentarlo "
            + "en otro momento.";

    @Override
    public String getMessage(){
        return MESSAGE;
    }
}