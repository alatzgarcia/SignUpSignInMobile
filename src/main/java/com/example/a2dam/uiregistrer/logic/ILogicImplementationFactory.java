package com.example.a2dam.uiregistrer.logic;

public class ILogicImplementationFactory {
    public static ILogic getLogic(){
        return new ILogicImplementation();
    }
}
