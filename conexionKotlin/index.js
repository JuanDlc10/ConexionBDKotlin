const express = require('express')
const mysql = require('mysql')
const bodyParse = require('body-parser')

const app = express()

app.use(bodyParse.json())

const PUERTO = 3000

const conexion = mysql.createConnection(
    {
        host: 'localhost',
        database: 'Example database',
        user: 'use',
        password: 'password'
    }
)

app.listen(PUERTO, ()=>{
    console.log(`Servidor corriendo en el puerto ${PUERTO}`)
})

conexion.connect(error => {
    if(error) throw error 
    console.log("Conexion Exitosa a la base de datos")
})

app.get('/', (req,res)=>{
    res.send('API')
})

app.get('/appObtenerUsuarios', (req,res) => {
    const query = 'SELECT * FROM t_usuarios'
    conexion.query(query,(error,results) => {
        if (error) return console.error(error.message)
        if (results.length > 0) {
            res.json(results)
        }else{
            res.json("No hay datos")
        }
    })
})

app.post('/appInicioSesion', (req,res)=>{
    const correo = req.body.correo
    const contrasena = req.body.contrasena

    const query = 'SELECT * FROM t_usuarios WHERE correo = ? AND contrasena = ?';

    conexion.query(query,[correo, contrasena],(error, results)=>{
        if(error){
            console.error(error)
            return res.status(500).json({error: "Error del Servidor"})
        }
        if(results.length > 0){
            res.json({message: 'Login Exitoso', usuarios:results[0]})
        }else{
            res.status(401).json({error: 'Credenciales Invalidas'})
        }
    })
})

app.post('/appAgregarUsuario', (req, res)=>{
    const usuario = {
        nombre: req.body.nombre,
        apPaterno: req.body.apPaterno,
        apMaterno: req.body.apMaterno,
        edad: req.body.edad,
        genero: req.body.genero,
        correo: req.body.correo,
        contrasena: req.body.contrasena,
        fechaNacimiento: req.body.fechaNacimiento
    }
    const query = 'INSERT INTO t_usuarios SET ?'
    conexion.query(query, usuario, (error)=>{
        if(error) return console.error(error.message)
        
        res.json('Se inserto correctamente el usuario')
    })
})