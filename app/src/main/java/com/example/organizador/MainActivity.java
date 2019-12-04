package com.example.organizador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizador.models.persona;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button ingresar,registrarBD1;

    private TextView registrar;

    EditText usuario, contraseña;

    private FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();
    }

    private void initControl() {
        ingresar = ( Button ) findViewById(R.id.ingresar);
        registrar = ( TextView ) findViewById(R.id.registrar);
        registrarBD1 = ( Button ) findViewById(R.id.registrarBD1);

        usuario = findViewById(R.id.usuario);
        contraseña = findViewById( R.id.contraseña2);

        ingresar.setOnClickListener(this);
        registrar.setOnClickListener(this);
        registrarBD1.setOnClickListener(this);

        inicializarFirebase();
        mAuth = FirebaseAuth.getInstance();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference();
       }

    public void onClick(View v){

        String user = usuario.getText().toString();
        String pass = contraseña.getText().toString();


        switch (v.getId()) {
            case R.id.ingresar:
//                Intent intent = new Intent(v.getContext(), dashboard.class);
//                startActivityForResult(intent,0);
                if (user.equals("") || pass.equals("")) {

                    validate();
                }else {
                    auth();
                }
                break;
            case R.id.registrar:
                Intent intent2 = new Intent(v.getContext(), registro.class);
                startActivityForResult(intent2,0);
                break;

            case R.id.registrarBD1:
                if (user.equals("") || pass.equals("")) {

                    validate();
                }else {
                    register();;
                }
                break;
        }
    }

    private void auth() {
        final String user = usuario.getText().toString();
        String pass = contraseña.getText().toString();

        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    int pos = user.indexOf("@"); // para separar los caracateres donde halla @. crea dos zonas 1 y 2
                    String user_name = user.substring(0, pos);
                    Toast.makeText(getApplicationContext(), "Bienbenido", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplication(), dashboard.class);
                    intent.putExtra("Usuario", user_name);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Credenciar Incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void register() {
        String user = usuario.getText().toString();
        String pass = contraseña.getText().toString();

        mAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_SHORT).show();
                    clear();
                    auth();
                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "Este Usuario ya Existe", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "No se Creo el Usuario", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void clear () {
        usuario.setText("");
        contraseña.setText("");
    }

    private void validate() {
        String user = usuario.getText().toString();
        String pass = contraseña.getText().toString();

        if (user.equals("")){
            usuario.setError("Requerrido");
        } else if (pass.equals("")){
            contraseña.setError("Requerrido");
        }
    }
}
