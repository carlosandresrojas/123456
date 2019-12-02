package com.example.organizador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.organizador.models.registro1;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.UUID;

public class registro extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private Button registrar;

    private TextView ingresar;
    EditText nombre, apellido, correo, nombre_usuario, direccion, contraseña, celular,contraseña2;
    RadioButton f,m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        initControl();
    }

    private void initControl() {

        registrar = ( Button ) findViewById(R.id.registrar);
        ingresar= ( TextView ) findViewById(R.id.ingresar);

        nombre = findViewById(R.id.nombre);
        apellido = findViewById( R.id.apellido);
        correo = findViewById(R.id.correo);
        nombre_usuario = findViewById( R.id.nombre_usuario);
        direccion = findViewById(R.id.direccion);
        contraseña = findViewById( R.id.contraseña);
        celular = findViewById(R.id.celular);
        contraseña2 = findViewById( R.id.contraseña2);

        f = findViewById(R.id.f);
        m = findViewById(R.id.m);

        registrar.setOnClickListener(this);
        ingresar.setOnClickListener(this);
       inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }




    @Override
    public void onClick(View v1) {

        String nombre1 = nombre.getText().toString();
        String apellido1 = apellido.getText().toString();

        String correo1 = correo.getText().toString();
        String nombre_usuario1 = nombre_usuario.getText().toString();

        String direccion1 = direccion.getText().toString();
        String contraseña1 = contraseña.getText().toString();

        String celular1 = celular.getText().toString();
        String contraseña22 = contraseña2.getText().toString();

        String f1 = f.getText().toString();
        String m1 = m.getText().toString();


        switch (v1.getId()) {
            case R.id.ingresar:
                Intent intent = new Intent(v1.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
                break;

            case R.id.registrar:
                if (nombre1.equals(" ")|| apellido1.equals(" ")|| correo1.equals(" ")|| nombre_usuario1.equals(" ")|| direccion1.equals(" ")|| contraseña1.equals(" ")|| celular1.equals(" ")|| contraseña22.equals(" ")){
                    validate ();
                } else {
                    registro1 p = new registro1();
                    p.setId(UUID.randomUUID().toString());
                    p.setNombre(nombre1);
                    p.setApellido(apellido1);
                    p.setCorreo(correo1);

                    p.setNombre_usuario(nombre_usuario1);
                    p.setDirección(direccion1);
                    p.setContraseña(contraseña1);

                    p.setCelular(celular1);
                    p.setContraseña2(contraseña22);

                    p.setF(f1);
                    p.setM(m1);
                    databaseReference.child("registro1").child(p.getId()).setValue(p);
                }

                break;
        }
    }

    private void validate() {
        String nombre1 = nombre.getText().toString();
        String apellido1 = apellido.getText().toString();

        String correo1 = correo.getText().toString();
        String nombre_usuario1 = nombre_usuario.getText().toString();

        String direccion1 = direccion.getText().toString();
        String contraseña1 = contraseña.getText().toString();

        String celular1 = celular.getText().toString();
        String contraseña22 = contraseña2.getText().toString();

        if (nombre1.equals("")){
            nombre.setError("Requerrido");
        } else if (apellido1.equals("")){
            apellido.setError("Requerrido");
        }else if (correo1.equals("")){
            correo.setError("Requerrido");
        }else if (nombre_usuario1.equals("")){
            nombre_usuario.setError("Requerrido");
        }else if (direccion1.equals("")){
            direccion.setError("Requerrido");
        }else if (contraseña1.equals("")){
            contraseña.setError("Requerrido");
        }else if (celular1.equals("")){
            celular.setError("Requerrido");
        }else if (contraseña22.equals("")){
            contraseña2.setError("Requerrido");
        }
    }
}


