package com.miempresa.serviciowebv4

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_listado_peliculas.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class listadoPeliculas : AppCompatActivity() {
    private val llenarLista = ArrayList<Elementos>()

    private fun cargarLista(url: String) {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)

        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val nombre =
                                response.getJSONObject(i).getString("nombre")
                            val duracion =
                                response.getJSONObject(i).getString("duracion")
                            val imagen =
                                response.getJSONObject(i).getString("imagen")
                            val categoria =
                                response.getJSONObject(i).getString("categoria")
                            llenarLista.add(Elementos(id.toInt(),imagen,nombre, duracion.toInt(), categoria))
                        }
                        val adapter = AdaptadorElementos(llenarLista)
                        lista.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_peliculas)

        cargarLista(getString(R.string.urlAPI) + "/peliculas")

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val query = newText.trim()
                if (query.isNotEmpty()) {
                    val url = getString(R.string.urlAPI) + "/peliculas"
                    val nombreUrl = "$url?nombre_like=$query"
                    val categoriaUrl = "$url?categoria_like=$query"

                    llenarLista.clear()
                    cargarLista(nombreUrl)
                    cargarLista(categoriaUrl)
                } else {
                    // Si el término de búsqueda está vacío, muestra todas las películas nuevamente.
                    llenarLista.clear()
                    cargarLista(getString(R.string.urlAPI) + "/peliculas")
                }
                return true
            }
        })
        botnSalir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
