package com.example.appgallinas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Llamarcliente(View view){
        Intent in= new Intent(this,Cliente.class);
        startActivity(in);
    }
    public void Llamarvendedor(View view){
        Intent in= new Intent(this,Vendedor.class);
        startActivity(in);
    }
}