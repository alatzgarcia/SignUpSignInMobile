package com.example.a2dam.uiregistrer.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2dam.uiregistrer.R;
import com.example.a2dam.uiregistrer.exceptions.IncorrectPasswordException;
import com.example.a2dam.uiregistrer.exceptions.LoginExistsException;
import com.example.a2dam.uiregistrer.logic.ConnectionThread;
import com.example.a2dam.uiregistrer.logic.ILogic;
import com.example.a2dam.uiregistrer.logic.ILogicImplementationFactory;

import signupsigninutilities.model.User;

public class UILogged extends AppCompatActivity implements Button.OnClickListener,
        Thread.UncaughtExceptionHandler  {

    Button btnLO;
    TextView text;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uilogged);



        btnLO = findViewById(R.id.btnLogOut);
        btnLO.setOnClickListener(this);

        text= findViewById(R.id.txtWellcome);
        //Intent intent = getIntent();
        //user.setLogin(intent.getStringExtra("LOGIN"));
        //user.setPassword(intent.getStringExtra("PASSWORD"));
        Bundle bundle= getIntent().getExtras();
        user = (User) bundle.getSerializable("USER");
        text.setText("¡¡¡¡Bienvenido, " + user.getLogin() +"!!!!");
    }

    public void onAnswer(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG ).show();
    }


    @Override
    public void onClick(View v) {
        //DialogLogOut dls=new DialogLogOut();
        //dls.show(getFragmentManager(),"Mi diálogo");
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        //set the title
        builder.setTitle("Cerrar Sesión:");
        //set the question
        builder.setMessage("¿Quiere cerrar sesión?");
        //create the button of accept
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            onAnswer("Cerrando la sesión");

            ILogic logicController = ILogicImplementationFactory.getLogic();
            Thread thread = new Thread(new ConnectionThread("close", logicController, user));

            thread.setUncaughtExceptionHandler(this::uncaughtException);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                dialog.dismiss();
                finish();
        }

        private void uncaughtException(Thread thread, Throwable throwable) {
            if(throwable.getCause() instanceof LoginExistsException){

            }else if(throwable.getCause() instanceof IncorrectPasswordException){

            }else if(throwable.getCause() instanceof Exception){

            }
        }
        });
        //creates the button of cancel
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAnswer("Cancelando");
                dialog.cancel();
            }
        });
        AlertDialog dialog =builder.create();
        dialog.show();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
