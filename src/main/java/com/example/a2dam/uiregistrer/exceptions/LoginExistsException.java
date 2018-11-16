package com.example.a2dam.uiregistrer.exceptions;

public class LoginExistsException extends Exception{
    private static final String MESSAGE = "Error. El login introducido "
            + "ya existe. Por favor, introduzca otro login diferente.";

    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
