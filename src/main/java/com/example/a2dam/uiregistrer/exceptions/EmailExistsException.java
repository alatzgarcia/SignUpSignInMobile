package com.example.a2dam.uiregistrer.exceptions;

public class EmailExistsException extends Exception{
    private static final String MESSAGE = "Error. El email introducido "
            + "ya existe. Por favor, introduzca otro email diferente.";

    @Override
    public String getMessage(){
        return MESSAGE;
    }
}