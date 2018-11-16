package com.example.a2dam.uiregistrer.logic;

import com.example.a2dam.uiregistrer.exceptions.IncorrectLoginException;
import com.example.a2dam.uiregistrer.exceptions.InvalidOperationException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import signupsigninutilities.model.Message;
import signupsigninutilities.model.User;

public class ConnectionThread implements Runnable{

    String msg;
    ILogic logic;
    User user;

    public ConnectionThread(String message, ILogic logic, User user) {
        this.msg = message;
        this.logic = logic;
        this.user = user;
    }

    @Override
    public void run() {
        try {
            switch (msg) {
                case "login":
                    User dbUser = logic.login(user);
                    if(dbUser.getEmail() == null ||
                            dbUser.getEmail().equalsIgnoreCase("")){
                        throw new IncorrectLoginException();
                    }
                    break;
                case "register":
                    logic.register(user);
                    break;
                default:
                    //Does`t receive any of the methods so sends exception

            }
        } catch (Exception e) {
            //--TOFIX
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
    }
}
