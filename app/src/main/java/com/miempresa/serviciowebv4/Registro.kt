package com.miempresa.serviciowebv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONException
import org.json.JSONObject

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnRegistrarse.setOnClickListener {
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val estado = txtEstado.text.toString()

            // Verificar si los campos están vacíos
            if (usuario.isEmpty() || clave.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Por favor, complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.1.8:3000/usuarios"

            val jsonBody = JSONObject()
            jsonBody.put("usuario", usuario)
            jsonBody.put("clave", clave)
            jsonBody.put("estado", estado)

            val stringRequest = JsonObjectRequest(
                Request.Method.POST, url, jsonBody,
                Response.Listener { response ->
                    try {
                        Toast.makeText(
                            applicationContext,
                            "Registro exitoso",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error en el registro",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Error en el registro",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            queue.add(stringRequest)
        }
        btnRegresar.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}