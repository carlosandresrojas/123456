package com.example.organizador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button ingresar;
    private TextView registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();
    }

    private void initControl() {
        ingresar = ( Button ) findViewById(R.id.ingresar);
        registrar = ( TextView ) findViewById(R.id.registrar);

        ingresar.setOnClickListener(this);
        registrar.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.ingresar:
                Intent intent = new Intent(v.getContext(), dashboard.class);
                startActivityForResult(intent,0);
                break;
            case R.id.registrar:
                Intent intent2 = new Intent(v.getContext(), registro.class);
                startActivityForResult(intent2,0);
                break;
        }
    }
}
