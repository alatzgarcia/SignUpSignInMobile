package com.example.a2dam.uiregistrer.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2dam.uiregistrer.R;
import com.example.a2dam.uiregistrer.exceptions.EmailExistsException;
import com.example.a2dam.uiregistrer.exceptions.IncorrectPasswordException;
import com.example.a2dam.uiregistrer.exceptions.LoginEmailExistException;
import com.example.a2dam.uiregistrer.exceptions.LoginExistsException;
import com.example.a2dam.uiregistrer.exceptions.RegisterFailedException;
import com.example.a2dam.uiregistrer.logic.ConnectionThread;
import com.example.a2dam.uiregistrer.logic.ILogic;
import com.example.a2dam.uiregistrer.logic.ILogicImplementationFactory;

import signupsigninutilities.model.User;

public class UIRegister extends AppCompatActivity implements View.OnClickListener, Thread.UncaughtExceptionHandler {


    Button registerButton;
    ImageButton exitButton;
    EditText editTxtUser;
    EditText editTxtFullName;
    EditText editTxtEmail;
    EditText editTxtPassword;
    EditText editTxtCheckPassword;
    TextView loginError;
    TextView fullNameError;
    TextView emailError;
    TextView passwordError;
    TextView stPasswordError;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiregister);



         registerButton=(Button)findViewById((R.id.btnRegister));
         registerButton.setOnClickListener(this);

        exitButton=(ImageButton)findViewById(R.id.imgBtnExit);
        exitButton.setOnClickListener(this);

        editTxtUser=findViewById(R.id.editTxtUser);

        editTxtFullName=findViewById(R.id.editTxtFullName);

        editTxtEmail=findViewById(R.id.editTxtEmail);

        editTxtPassword=findViewById(R.id.editTxtPassword);

        editTxtCheckPassword=findViewById(R.id.editTxtCheckPassword);


        loginError=findViewById(R.id.txtUserError);
        loginError.setTextColor(Color.RED);

        fullNameError=findViewById(R.id.txtFullNameError);
        fullNameError.setTextColor(Color.RED);

        emailError=findViewById(R.id.txtEmailError);
        emailError.setTextColor(Color.RED);

        passwordError=findViewById(R.id.txtPasswordError);
        passwordError.setTextColor(Color.RED);

        stPasswordError=findViewById(R.id.txtCheckPasswordError);
        stPasswordError.setTextColor(Color.RED);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnRegister:
                User user = new User();
                boolean missingData = false;

                if(editTxtUser.getText().toString().length()<8 ||
                    editTxtUser.getText().toString().length()>30){
                    if(!missingData) {
                     missingData = true;
                    }
                    loginError.setText( "El campo debe contener entre 8 y 30 caracteres.");

                }else{
                    loginError.setText("");
                }
                if(editTxtFullName.getText().toString().length()<8 ||
                        editTxtFullName.getText().toString().length()>50){
                    if(!missingData) {
                        missingData = true;
                    }
                    fullNameError.setText("El campo debe contener " + "entre 8 y 50 caracteres.");
                    /*Toast.makeText(this, "El campo debe contener " +
                                    "entre 8 y 50 caracteres.",
                            Toast.LENGTH_LONG).show();*/
                }else{
                    fullNameError.setText("");
                 }
                if(!editTxtEmail.getText().toString().
                        matches("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$")){
                    if(!missingData) {
                        missingData = true;
                    }
                    emailError.setText("El campo debe contener "+ "un email correcto.");
                    emailError.setTextColor(Color.RED);
                    /*Toast.makeText(this, "El campo debe contener un email correcto",
                            Toast.LENGTH_LONG).show();*/
                }else{
                    emailError.setText("");
                }
                if(editTxtPassword.getText().toString().length()<8 ||
                        editTxtPassword.getText().toString().length()>30){
                    if(!missingData) {
                        missingData = true;
                    }
                    passwordError.setText("El campo debe contener "+"entre 8 y 30 caracteres.");
                    passwordError.setTextColor(Color.RED);
                    /*Toast.makeText(this, "El campo debe contener " +
                                    "entre 8 y 30 caracteres.",
                            Toast.LENGTH_LONG).show();*/
                }else{
                    passwordError.setText("");
                }
                if(editTxtCheckPassword.getText().toString().length()<8 ||
                        editTxtCheckPassword.getText().toString().length()>30){
                    if(!missingData) {
                        missingData = true;
                    }
                    stPasswordError.setText("El campo debe contener "+ "entre 8 y 30 caracteres");
                    stPasswordError.setTextColor(Color.RED);
                    /*Toast.makeText(this, "El campo debe contener " +
                                    "entre 8 y 30 caracteres.",
                            Toast.LENGTH_LONG).show();*/
                }else{
                    stPasswordError.setText("");
                }
                if(!editTxtPassword.getText().toString().
                        equals(editTxtCheckPassword.getText().toString())) {
                    if(!missingData) {
                        missingData = true;
                    }else{
                        //Mostrar ventana de error.
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Las constraseñas no coinciden. Comprueba las contraseñas")
                                .setTitle("ERROR").setCancelable(false)
                                .setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }

                if(!missingData){
                    User user1 = new User(editTxtUser.getText().toString(),editTxtEmail.getText().toString(),editTxtFullName.getText().toString(),editTxtPassword.getText().toString());
                    //combrobaciones y llevarlo a lógica
                    ILogic logicController = ILogicImplementationFactory.getLogic();
                    Thread thread = new Thread(new ConnectionThread("register", logicController, user1));

                    thread.setUncaughtExceptionHandler(this::uncaughtException);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // logicController.register(user);
                    //checkear que la operación ha ido correctamente
                    Intent goLogged=new Intent(this, UILogged.class);//meter el logged entre()
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("USER", user1);
                    goLogged.putExtras(bundle);
                    startActivity(goLogged);
                    finish();
                }
                break;
            case R.id.imgBtnExit:
                //System.exit(0);
                //Android.os.Process.killProcess(Android.os.Process.myPid());
                finishAffinity();
                break;
        }

    }

    public void onRespuesta(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable t) {
        if(t.getCause() instanceof LoginExistsException){
            errorText();
            Toast.makeText(getApplicationContext(),"Login on use",Toast.LENGTH_SHORT).show();
            loginError.setText("Login name already on use");
        }else if(t.getCause() instanceof EmailExistsException){
            errorText();
            Toast.makeText(getApplicationContext(),"Email on use",Toast.LENGTH_SHORT).show();
            emailError.setText("Email already on use");
        }else if(t.getCause() instanceof LoginEmailExistException){
            errorText();
            Toast.makeText(getApplicationContext(),"Email and login name on use",Toast.LENGTH_SHORT).show();
            loginError.setText("Login name already on use");
            emailError.setText("Email already on use");
        } else if(t.getCause() instanceof RegisterFailedException){
            errorText();
            Toast.makeText(getApplicationContext(),"Error, register failed",Toast.LENGTH_SHORT).show();
        }else if(t.getCause() instanceof Exception){
            Toast.makeText(getApplicationContext(),"Error.",Toast.LENGTH_SHORT).show();
        }
    }
    private void errorText() {
        loginError.setTextColor(Color.RED);
        emailError.setTextColor(Color.RED);
    }
}
