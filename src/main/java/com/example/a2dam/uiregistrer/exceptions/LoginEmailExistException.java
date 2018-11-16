package com.example.a2dam.uiregistrer.exceptions;

public class LoginEmailExistException extends Exception{
    private static final String MESSAGE = "Error. El nombre de usuario"
            + "y el email introducidos ya existen.";

    public String getMessage(){
        return MESSAGE;
    }
}
