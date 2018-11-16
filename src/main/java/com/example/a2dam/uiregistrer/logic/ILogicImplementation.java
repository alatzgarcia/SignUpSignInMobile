
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.a2dam.uiregistrer.logic;

import com.example.a2dam.uiregistrer.exceptions.EmailExistsException;
import com.example.a2dam.uiregistrer.exceptions.GenericException;
import com.example.a2dam.uiregistrer.exceptions.IncorrectLoginException;
import com.example.a2dam.uiregistrer.exceptions.IncorrectPasswordException;
import com.example.a2dam.uiregistrer.exceptions.LoginEmailExistException;
import com.example.a2dam.uiregistrer.exceptions.LoginExistsException;
import com.example.a2dam.uiregistrer.exceptions.NoCurrentSessionException;
import com.example.a2dam.uiregistrer.exceptions.NotAvailableConnectionsException;
import com.example.a2dam.uiregistrer.exceptions.RegisterFailedException;
import com.example.a2dam.uiregistrer.exceptions.ServerNotAvailableException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import signupsigninutilities.model.Message;
import signupsigninutilities.model.User;

/**
 * Class that implements the socket for the client side of the application
 * and allows the client to connect to the server
 * @author Alatz
 */
public class ILogicImplementation implements ILogic{
    private static final Logger LOGGER =
            Logger.getLogger("signupsigninuidesktop.logic.ILogicImplementation");
    private final String IP = "LAPINF01.TartangaLH.eus";
    private final int PORT = 5011; //--TOFIX

    private Socket client;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    /**
     * Method that connects client and server for the "login" of a user
     * @param user
     * @return logged in user
     * @throws IncorrectLoginException
     * @throws IncorrectPasswordException
     */
    @Override
    public User login(User user) throws IncorrectLoginException, IncorrectLoginException,
            ServerNotAvailableException, GenericException, IncorrectPasswordException {
        try{

            start();
            LOGGER.info("IP: " + IP);
            LOGGER.info(String.valueOf("PORT: " + PORT));
            oos = new ObjectOutputStream(client.getOutputStream());
            LOGGER.info("Sending message to the server...");
            oos.writeObject(new Message("login", user));

            LOGGER.info("Awaiting for the server message...");
            ois = new ObjectInputStream(client.getInputStream());
            Message msg = (Message)ois.readObject();


            LOGGER.info("Server message arrived to the client.");
            LOGGER.info(msg.getMessage());
            if(msg.getMessage().equalsIgnoreCase("ok")){
                User dbUser;
                dbUser = (User)msg.getData();
                return dbUser;
            } else if(msg.getMessage().equalsIgnoreCase("incorrectLogin")){
                throw new IncorrectLoginException();
            } else if(msg.getMessage().equalsIgnoreCase("incorrectPassword")){
                throw new IncorrectPasswordException();
            } else if(msg.getMessage().equalsIgnoreCase("serverNotAvailable")){
                throw new ServerNotAvailableException();
            } else if(msg.getMessage().equalsIgnoreCase("error")){
                throw new GenericException();
            } else{
                throw new GenericException();
            }
        } catch(IncorrectPasswordException ipe){
            throw new IncorrectPasswordException();
        } catch(IncorrectLoginException ile) {
            throw new IncorrectLoginException();
        } catch(ServerNotAvailableException snae){
            throw new ServerNotAvailableException();
        } catch(Exception e){
            LOGGER.severe(e.getMessage());
            throw new GenericException();
        } finally {
            try {
                if(oos != null){
                    oos.close();
                }
                if(ois != null){
                    ois.close();
                }
                if(client != null){
                    client.close();
                }
            } catch (IOException ex) {
                LOGGER.severe(ex.getMessage());
            }
        }
    }

    /**
     * Method that connects client and server for the "register" of a user
     * @param user
     * @return registered user
     * @throws LoginExistsException
     * @throws EmailExistsException
     * @throws LoginEmailExistException
     */
    @Override
    public User register(User user) throws LoginExistsException,EmailExistsException,
            LoginEmailExistException, RegisterFailedException,ServerNotAvailableException,
            NotAvailableConnectionsException,GenericException{
        try{
            start();

            oos = new ObjectOutputStream(client.getOutputStream());
            LOGGER.info("Sending message to the server...");
            oos.writeObject(new Message("register", user));

            LOGGER.info("Awaiting for the server message...");
            ois = new ObjectInputStream(client.getInputStream());
            Message msg = (Message)ois.readObject();

            LOGGER.info(msg.getMessage());
            LOGGER.info("Server message arrived to the client.");
            if(msg.getMessage().equalsIgnoreCase("ok")){
                User dbUser;
                dbUser = (User)msg.getData();
                return dbUser;
            } else if(msg.getMessage().equalsIgnoreCase("loginExists")){
                throw new LoginExistsException();
            } else if(msg.getMessage().equalsIgnoreCase("emailExists")){
                throw new EmailExistsException();
            } else if(msg.getMessage().equalsIgnoreCase("loginEmailExist")){

                throw new LoginEmailExistException();
            } else if(msg.getMessage().equalsIgnoreCase("registerFailed")){
                throw new RegisterFailedException();
            } else if(msg.getMessage().equalsIgnoreCase("serverNotAvailable")){
                throw new ServerNotAvailableException();
            } else if(msg.getMessage().
                    equalsIgnoreCase("notAvailableConnections")){
                throw new NotAvailableConnectionsException();
            } else if(msg.getMessage().equalsIgnoreCase("error")){
                throw new GenericException();
            }else{
                throw new GenericException();
            }
        } catch(LoginExistsException lee){
            throw new LoginExistsException();
        } catch(EmailExistsException eee){
            throw new EmailExistsException();
        } catch(LoginEmailExistException leee){
            throw new LoginEmailExistException();
        } catch(Exception e){
            LOGGER.severe(e.getMessage());
            throw new GenericException();
        } finally {
            try {
                if(oos != null){
                    oos.close();
                }
                if(ois != null){
                    ois.close();
                }
                if(client != null){
                    client.close();
                }
            } catch (IOException ex) {

                LOGGER.severe(ex.getMessage());
            }
        }
    }

    /**
     * Method to close the socket for the client when he logs out
     * @throws Exception
     */
    @Override
    public void close() throws Exception{
        if(client != null){
            client.close();
        }
        else{
            throw new NoCurrentSessionException();
        }
    }

    /**
     * Method to start the socket for the client
     */
    public void start() throws IOException{
        client = new Socket(IP, PORT);
    }
}