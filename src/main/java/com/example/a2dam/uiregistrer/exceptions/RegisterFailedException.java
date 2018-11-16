package com.example.a2dam.uiregistrer.exceptions;

public class RegisterFailedException extends Exception{
    private static final String MESSAGE = "Error. El registro de usuario"
            + "no se ha podido completar con éxito. Vuelva a intentarlo.";
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
