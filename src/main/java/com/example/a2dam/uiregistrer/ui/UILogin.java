package com.example.a2dam.uiregistrer.ui;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2dam.uiregistrer.R;
import com.example.a2dam.uiregistrer.exceptions.IncorrectLoginException;
import com.example.a2dam.uiregistrer.exceptions.IncorrectPasswordException;
import com.example.a2dam.uiregistrer.exceptions.LoginExistsException;
import com.example.a2dam.uiregistrer.exceptions.ServerNotAvailableException;
import com.example.a2dam.uiregistrer.logic.ConnectionThread;
import com.example.a2dam.uiregistrer.logic.ILogic;
import com.example.a2dam.uiregistrer.logic.ILogicImplementationFactory;

import java.sql.Connection;

import signupsigninutilities.model.Message;
import signupsigninutilities.model.User;


public class UILogin extends AppCompatActivity implements View.OnClickListener,Thread.UncaughtExceptionHandler   {

    ImageButton btnSalir;
    Button buttonL;
    Button buttonR;
    ImageButton imageButton;
    EditText username;
    EditText pass;
    TextView userError;
    TextView passError;
    boolean excepcion = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSalir =(ImageButton) findViewById(R.id.btnSalir);
        buttonL = (Button) findViewById(R.id.btnLogin);
        buttonL.setOnClickListener(this);
        buttonR = (Button) findViewById(R.id.btnRegister);
        buttonR.setOnClickListener(this);
        imageButton = (ImageButton) findViewById(R.id.btnSalir);
        imageButton.setOnClickListener(this);
        username = (EditText) findViewById(R.id.txtUser);
        pass = (EditText) findViewById(R.id.txtPassword);
        userError= (TextView) findViewById(R.id.txtUserError);
        userError.setTextColor(Color.RED);
        passError= (TextView) findViewById(R.id.txtPassError);
        passError.setTextColor(Color.RED);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                try{
                    boolean error=false;
                    if(username.getText().toString().isEmpty()){
                        if(!error){
                            error=true;
                        }
                        userError.setText("El campo no puede estar vacío");
                    }else{
                        userError.setText("");
                    }
                    if(pass.getText().toString().isEmpty()){
                        passError.setText("El campo no puede estar vacío");
                        error=true;
                    }else{
                        passError.setText("");
                    }
                    if(username.getText().toString().length()<8||username.getText().toString().length()>30){
                        userError.setText("La longitud del campo no es correcta");
                        error=true;
                    }else{
                        passError.setText("");
                    }
                    if(pass.getText().toString().length()<8||pass.getText().toString().length()>30){
                        passError.setText("La longitud del campo no es correcta");
                        error=true;
                    }else{
                        passError.setText("");

                    }
                    if(!error){
                        User user = new User(username.getText().toString(),pass.getText().toString());
                        ILogic logicController = ILogicImplementationFactory.getLogic();

                        Thread thread = new Thread(new ConnectionThread("login", logicController, user));

                        thread.setUncaughtExceptionHandler(this::uncaughtException);
                        thread.start();
                        thread.join();

                        if(!excepcion){
                            Intent intent = new Intent(this, UILogged.class);
                            Bundle bundle = new Bundle();
                            //bundle.putString("LOGIN", user.getLogin());
                            //bundle.putString("PASSWORD", user.getPassword());
                            bundle.putSerializable("USER", user);
                            intent.putExtras(bundle);
                            startActivity(intent);

                            clearLoginData();
                        }
                    }
                }
                catch(Exception e){
                }
                finally{
                    break;
                }
            case R.id.btnRegister:
                Intent intent = new Intent(this, UIRegister.class);
                startActivity(intent);
                clearLoginData();
                break;
            case R.id.btnSalir:
                finishAffinity();
                break;
             default:
        }
    }

    private void clearLoginData() {
        username.setText("");
        pass.setText("");
    }

    @Override
    public void uncaughtException(Thread thread, Throwable t) {
        excepcion = true;
        if(t.getCause() instanceof IncorrectLoginException){
            //--TOFIX
            errorText("user");
            Toast.makeText(getApplicationContext(),"Login doesn't exist",Toast.LENGTH_SHORT).show();
            userError.setText("Login Incorrect");
        }else if(t.getCause() instanceof IncorrectPasswordException) {
            errorText("password");
            Toast.makeText(getApplicationContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();
            passError.setText("Password Incorrect");
        }else if(t.getCause() instanceof ServerNotAvailableException){
            Toast.makeText(getApplicationContext(), "Server is not available", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }
    }

    private void errorText(String field) {
        if(field.equalsIgnoreCase("user")){
            userError.setTextColor(Color.RED);
        }
        else if(field.equalsIgnoreCase("password")){
            passError.setTextColor(Color.RED);
        }
    }
}
