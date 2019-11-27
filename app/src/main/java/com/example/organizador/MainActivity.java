package com.example.organizador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.organizador.models.persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button ingresar,registrarBD1;

    private TextView registrar;

    EditText usuario, contraseña;

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
                Intent intent = new Intent(v.getContext(), dashboard.class);
                startActivityForResult(intent,0);
                break;
            case R.id.registrar:
                Intent intent2 = new Intent(v.getContext(), registro.class);
                startActivityForResult(intent2,0);
                break;
            case R.id.registrarBD1:
                if (user.equals(" ")|| pass.equals(" ")){
                    validate ();
            } else {
                    persona p = new persona();
                    p.setId(UUID.randomUUID().toString());
                    p.setUsuario(user);
                    p.setContraseña(pass);
                    databaseReference.child("Persona").child(p.getId()).setValue(p);

                }

                break;
        }
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
