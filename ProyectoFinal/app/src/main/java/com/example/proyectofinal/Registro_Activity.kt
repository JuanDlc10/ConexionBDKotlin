package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Registro_Activity : AppCompatActivity() {

    private lateinit var registrar: Button
    private lateinit var cancelar: Button
    private lateinit var nombres: EditText
    private lateinit var apellidoPaterno: EditText
    private lateinit var apellidoMaterno: EditText
    private lateinit var edad: EditText
    private lateinit var fechaNacimiento: EditText
    private lateinit var correo: EditText
    private lateinit var contrasena: EditText
    private lateinit var radioGenero: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        registrar = findViewById(R.id.button_Registrar)
        cancelar = findViewById(R.id.button_cancelar)
        nombres = findViewById(R.id.Nombres)
        apellidoPaterno = findViewById(R.id.apPaterno)
        apellidoMaterno = findViewById(R.id.apMaterno)
        edad = findViewById(R.id.Edad)
        fechaNacimiento = findViewById(R.id.FechaNacimiento)
        correo = findViewById(R.id.Correo)
        contrasena = findViewById(R.id.Contrasena)
        radioGenero = findViewById(R.id.radioGenero)

        registrar.setOnClickListener {

            val nombre = nombres.text.toString()
            val apellidoP = apellidoPaterno.text.toString()
            val apellidoM = apellidoMaterno.text.toString()
            val edadUsuario = edad.text.toString()
            val fechaNac = fechaNacimiento.text.toString()
            val correoUsuario = correo.text.toString()
            val contrasenaUsuario = contrasena.text.toString()
            val generoSeleccionadoId = radioGenero.checkedRadioButtonId

            val generoSeleccionado = when (generoSeleccionadoId) {
                R.id.radioMasculino -> "Masculino"
                R.id.radioFemenino -> "Femenino"
                R.id.radioOtro -> "Otro"
                else -> "No especificado"
            }

            if(nombre.isNotEmpty() && apellidoP.isNotEmpty() && apellidoM.isNotEmpty()
                && edadUsuario.isNotEmpty() && fechaNac.isNotEmpty() && correoUsuario.isNotEmpty()
                && contrasenaUsuario.isNotEmpty()){

                val nuevoUsuario = Usuarios(
                    id_usuario = 0,
                    nombre = nombre,
                    apPaterno = apellidoP,
                    apMaterno = apellidoM,
                    edad = edadUsuario,
                    genero = generoSeleccionado,
                    correo = correoUsuario,
                    contrasena = contrasenaUsuario,
                    fechaNacimiento = fechaNac
                )

                registrarUsuario(nuevoUsuario)
            }else{
                Toast.makeText(this, "Por favor llene los campos", Toast.LENGTH_LONG).show()
            }

        }

        cancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registrarUsuario(usuarios: Usuarios){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitClient.webService.agregarUsuarios(usuarios)
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        Toast.makeText(this@Registro_Activity, "Registro con Exito", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Registro_Activity, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@Registro_Activity, "Fallo el registro", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@Registro_Activity, "Error de conexion: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
