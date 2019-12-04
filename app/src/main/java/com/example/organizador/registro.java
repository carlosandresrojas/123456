package com.example.organizador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizador.models.registro1;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class registro extends AppCompatActivity implements View.OnClickListener{

    private List<registro1> lista = new ArrayList<registro1>();
    ArrayAdapter<registro1> personaArrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private Button registrar, modificar, eliminar;
    private ListView lista_persona;


    private TextView ingresar;
    EditText nombre, apellido, correo, nombre_usuario, direccion, contraseña, celular,contraseña2;
    RadioButton f,m;

    registro1 personaSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        initControl();
    }

    private void initControl() {

        registrar = ( Button ) findViewById(R.id.registrar);
        ingresar= ( TextView ) findViewById(R.id.ingresar);
        modificar = ( Button ) findViewById(R.id.modificar);
        eliminar = ( Button ) findViewById(R.id.eliminar);

        lista_persona = findViewById(R.id.lista);

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
        modificar.setOnClickListener(this);
        eliminar.setOnClickListener(this);
       inicializarFirebase();
       listarDatos ();

       lista_persona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               personaSeleccionada = (registro1) adapterView.getItemAtPosition(i);
               nombre.setText(personaSeleccionada.getNombre());
               apellido.setText(personaSeleccionada.getApellido());
               correo.setText(personaSeleccionada.getCorreo());
               nombre_usuario.setText(personaSeleccionada.getNombre_usuario());
               direccion.setText(personaSeleccionada.getDirección());
               contraseña.setText(personaSeleccionada.getContraseña());
               celular.setText(personaSeleccionada.getCelular());
               contraseña2.setText(personaSeleccionada.getContraseña2());
           }
       });
    }

    private void listarDatos() {
        databaseReference.child("registro1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    registro1 p = objSnapshot.getValue(registro1.class);
                    lista.add(p);

                    personaArrayAdapter = new ArrayAdapter<registro1>(registro.this, android.R.layout.simple_list_item_1, lista);
                    lista_persona.setAdapter(personaArrayAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
            case R.id.modificar:
                registro1 r = new registro1();
                r.setId(personaSeleccionada.getId());

                r.setNombre(nombre1);
                r.setApellido(apellido1);
                r.setNombre_usuario(nombre_usuario1);
                r.setDirección(direccion1);
                r.setCorreo(correo1);
                r.setContraseña(contraseña1);
                r.setCelular(celular1);
                r.setContraseña2(contraseña22);
                databaseReference.child("registro1").child(r.getId()).setValue(r);
                Toast.makeText(this, "Actualizado", Toast.LENGTH_LONG).show();
                clear ();
                break;
            case R.id.eliminar:
                registro1 re = new registro1();
                re.setId(personaSeleccionada.getId());
                databaseReference.child("registro1").child(re.getId()).removeValue();
                Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG).show();
                clear();
                break;
        }
    }

    private void clear() {
        nombre.setText("");
        apellido.setText("");
        direccion.setText("");
        contraseña.setText("");
        contraseña2.setText("");
        nombre_usuario.setText("");
        correo.setText("");
        celular.setText("");

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


