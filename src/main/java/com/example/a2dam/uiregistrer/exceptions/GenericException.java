package com.example.a2dam.uiregistrer.exceptions;

public class GenericException extends Exception{
    private static final String MESSAGE = "Ha ocurrido un error.";

    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
