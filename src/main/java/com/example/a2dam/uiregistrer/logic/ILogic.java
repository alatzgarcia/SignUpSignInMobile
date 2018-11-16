package com.example.a2dam.uiregistrer.logic;

import com.example.a2dam.uiregistrer.exceptions.EmailExistsException;
import com.example.a2dam.uiregistrer.exceptions.GenericException;
import com.example.a2dam.uiregistrer.exceptions.IncorrectLoginException;
import com.example.a2dam.uiregistrer.exceptions.IncorrectPasswordException;
import com.example.a2dam.uiregistrer.exceptions.LoginEmailExistException;
import com.example.a2dam.uiregistrer.exceptions.LoginExistsException;
import com.example.a2dam.uiregistrer.exceptions.NotAvailableConnectionsException;
import com.example.a2dam.uiregistrer.exceptions.RegisterFailedException;
import com.example.a2dam.uiregistrer.exceptions.ServerNotAvailableException;

import signupsigninutilities.model.User;

public interface ILogic {
    public User login(User user) throws IncorrectLoginException, IncorrectPasswordException,
            ServerNotAvailableException, GenericException;
    public User register(User user) throws LoginExistsException, EmailExistsException,
            LoginEmailExistException, RegisterFailedException, ServerNotAvailableException,
            NotAvailableConnectionsException, GenericException;
    public void close() throws Exception;
}
