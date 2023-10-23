const express = require('express');
const http = require('http');
const socketIo = require('socket.io');
//const mysql = require('mysql');
const PORT = 3000;

const app = express();
const server = http.createServer(app);
const io = socketIo(server);

let users = {
    username: "Ramon",
    password: "123456"
}

server.listen(PORT, function () {
    console.log("Server running on port " + PORT);
});


app.get('/api/Login/:username/:password', function (req, res) {
    const username = req.params.username;
    const password = req.params.password;
    if (username === users.username && password === users.password) {
        res.send("Bienvenido");
    } else if (username === users.username && password ==! users.password) {
        res.send("Error, la contrasenya es incorrecta");
    } else if(username==! users.username && password === users.password){
        res.send("Error, el usuari no es correcte");
    } else if(username ==! users.username && password ==! users.password){
        res.send("Error, les variables user i password son incorrectes")
    }
});

// Configuració de la conexió a la base de dades
/* const dbConfig = {
    host: 'localhost',
    user: 'tu_usuario',
    password: 'tu_contraseña',
    database: 'nombre_de_tu_base_de_datos'
}; */

// Crear la connexió a la base de datos
// const connection = mysql.createConnection(dbConfig);

// Conectar a la base de dades
/*connection.connect((err) => {
    if (err) {
        console.error('Error al conectar a la base de datos: ' + err.stack);
        return;
    }
    console.log('Conexión a la base de datos exitosa');
});*/

// Ruta per a obtenir dades de la base de dades
/*app.get('/api/datos', (req, res) => {
    connection.query('SELECT * FROM nombre_de_tu_tabla', (error, results, fields) => {
        if (error) throw error;
        res.send(results);
    });
});*/


io.on('connection', (socket) => {
    console.log('Un usuario se ha conectado');

    socket.on('chat message', (msg) => {
        io.emit('chat message', msg);
    });

    socket.on('disconnect', () => {
        console.log('Un usuario se ha desconectado');
    });
});



