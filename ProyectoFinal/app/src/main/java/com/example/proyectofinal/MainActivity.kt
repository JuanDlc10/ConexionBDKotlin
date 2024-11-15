package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response

class MainActivity : AppCompatActivity() {
    private lateinit var loguear : Button
    private lateinit var registro : Button
    private lateinit var correo : EditText
    private lateinit var contrasena: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            loguear = findViewById(R.id.button_login)
            registro = findViewById(R.id.button_registro)
            correo = findViewById(R.id.Correo)
            contrasena = findViewById(R.id.Contrasena)

            registro.setOnClickListener{
                val intent = Intent(this, Registro_Activity::class.java)
                startActivity(intent)
            }
            insets
        }
    }
}