package com.miempresa.serviciowebv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        btnIngresar.setOnClickListener(){
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios?"
            url = url + "usuario=" + usuario + "&clave=" + clave

            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        val valor = response.getJSONObject(0)
                        /*Toast.makeText(
                            applicationContext,
                            "Validacion de usuario: " + valor.getString("usuario") +
                                    " con clave: " + valor.getString("clave") +
                                    " con estado: " + valor.getString("estado"),
                            Toast.LENGTH_LONG
                        ).show()*/
                        val llamaractividad = Intent(applicationContext, Categorias::class.java)
                        startActivity(llamaractividad)
                        finish()
                    }catch (e: JSONException){
                        Toast.makeText(
                            applicationContext,
                            "Error en las credenciales proporcionadas",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Compruebe que tiene acceso a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
        btnRegistros.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            finish()
        }
    }
}