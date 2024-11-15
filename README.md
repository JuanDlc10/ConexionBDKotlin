# Manual de Conexión a Base de Datos en Kotlin utilizando JavaScript

Este manual te guiará paso a paso para realizar una conexión a una base de datos MySQL usando JavaScript (Node.js y Express) en el backend y cómo integrarlo con un proyecto en Kotlin utilizando Retrofit para realizar solicitudes HTTP.

## Requisitos

- Node.js y npm instalados.
- MySQL instalado y configurado.
- Base de datos `example` con una tabla `t_example`.
- Conocimientos básicos en JavaScript, SQL y Kotlin.

## Instalación de Dependencias en JavaScript

1. Inicializa un proyecto en Node.js (si aún no lo tienes).
   ```bash
   npm init -y

    Instala las dependencias necesarias.

    npm install express mysql body-parser

Código en JavaScript

A continuación, se muestra el código para conectarse a la base de datos y definir algunas rutas básicas de API.

const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());

const PUERTO = 3000;

const conexion = mysql.createConnection({
    host: 'localhost',
    database: 'comics',
    user: 'root',
    password: ''
});

app.listen(PUERTO, () => {
    console.log(`Servidor corriendo en el puerto ${PUERTO}`);
});

conexion.connect(error => {
    if (error) throw error;
    console.log("Conexion Exitosa a la base de datos");
});

app.get('/', (req, res) => {
    res.send('API');
});

1. Obtener Usuarios

Esta ruta permite obtener todos los usuarios de la tabla t_usuarios.

app.get('/appObtenerUsuarios', (req, res) => {
    const query = 'SELECT * FROM t_usuarios';
    conexion.query(query, (error, results) => {
        if (error) return console.error(error.message);
        if (results.length > 0) {
            res.json(results);
        } else {
            res.json("No hay datos");
        }
    });
});

2. Inicio de Sesión

Esta ruta permite verificar las credenciales del usuario y responder con un mensaje de éxito o error.

app.post('/appInicioSesion', (req, res) => {
    const correo = req.body.correo;
    const contrasena = req.body.contrasena;

    const query = 'SELECT * FROM t_usuarios WHERE correo = ? AND contrasena = ?';

    conexion.query(query, [correo, contrasena], (error, results) => {
        if (error) {
            console.error(error);
            return res.status(500).json({ error: "Error del Servidor" });
        }
        if (results.length > 0) {
            res.json({ message: 'Login Exitoso', usuarios: results[0] });
        } else {
            res.status(401).json({ error: 'Credenciales Invalidas' });
        }
    });
});

3. Agregar Usuario

Esta ruta permite agregar un nuevo usuario a la base de datos.

app.post('/appAgregarUsuario', (req, res) => {
    const usuario = {
        nombre: req.body.nombre,
        apPaterno: req.body.apPaterno,
        apMaterno: req.body.apMaterno,
        edad: req.body.edad,
        genero: req.body.genero,
        correo: req.body.correo,
        contrasena: req.body.contrasena,
        fechaNacimiento: req.body.fechaNacimiento
    };

    const query = 'INSERT INTO t_usuarios SET ?';
    conexion.query(query, usuario, (error) => {
        if (error) return console.error(error.message);

        res.json('Se inserto correctamente el usuario');
    });
});

Código en Kotlin para el WebService

Este código Kotlin utiliza Retrofit para conectarse a la API creada en Node.js. Asegúrate de reemplazar BASE_URL con la dirección IP de tu servidor o http://10.0.2.2:3000 si estás usando un emulador de Android.

package com.example.proyectofinal

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object AppConstantes {
    const val BASE_URL = "http://10.0.2.2:3000" // Dirección IP del servidor o IP del emulador
}

interface WebService {
    @POST("/appAgregarUsuario")
    suspend fun agregarUsuarios(
        @Body usuarios: Usuarios
    ): Response<String>

    @POST("/appInicioSesion")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}

object RetrofitClient {
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}

Descripción del Código en Kotlin

    AppConstantes: Define la URL base para la conexión.
    WebService: Contiene las funciones de API agregarUsuarios y login que envían datos en formato JSON.
    RetrofitClient: Crea una instancia única de WebService utilizando Retrofit y Gson para la conversión de datos.

Ejecución

    Inicia el servidor Node.js:

node nombre_del_archivo.js

Configura la IP de BASE_URL en tu proyecto de Kotlin.
Prueba las rutas agregarUsuarios y login desde tu aplicación en Kotlin.
