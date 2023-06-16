package com.miempresa.serviciowebv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_categorias.*

class Categorias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)
        btnUsuarios.setOnClickListener(){
            val intent = Intent(this, listadoUsuarios::class.java)
            startActivity(intent)
        }
        btnPeliculas.setOnClickListener(){
            val intent = Intent(this, listadoPeliculas::class.java)
            startActivity(intent)
        }
    }
}