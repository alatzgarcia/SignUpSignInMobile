package com.example.a2dam.uiregistrer.exceptions;

public class IncorrectLoginException extends Exception {
    private static final String MESSAGE = "Error. El login introducido "
            + "es incorrecto.";

    @Override
    public String getMessage(){
        return MESSAGE;
    }
}