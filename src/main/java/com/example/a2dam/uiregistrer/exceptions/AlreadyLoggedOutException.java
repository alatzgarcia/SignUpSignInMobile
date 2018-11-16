package com.example.a2dam.uiregistrer.exceptions;

public class AlreadyLoggedOutException extends Exception{
    private static final String MESSAGE = "Error. El usuario ya está desconectado";

    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
