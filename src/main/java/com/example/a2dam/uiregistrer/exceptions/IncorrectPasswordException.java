package com.example.a2dam.uiregistrer.exceptions;

public class IncorrectPasswordException extends Exception{
    private static final String MESSAGE = "Error. La contraseña introducida "
            + "es incorrecta.";

    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
