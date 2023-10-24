const express = require('express');
const http = require('http');
const socketIo = require('socket.io');
const mysql = require('mysql');
const PORT = 3000;

const app = express();
const server = http.createServer(app);
const io = socketIo(server);

server.listen(PORT, function () {
    console.log("Server running on port " + PORT);
});

app.get('/', function (req, res) {
    res.send("Conectat al server")
})

// Configuració de la conexió a la base de dades
const dbConfig = {
    host: 'dam.inspedralbes.cat',
    user: 'a22tomybanog_Projecte1',
    password: 'Projecte1',
    database: 'a22tomybanog_Projecte1'
};

// Crear la connexió a la base de datos
const connection = mysql.createConnection(dbConfig);

// Conectar a la base de dades
connection.connect((err) => {
    if (err) {
        console.error('Error al conectar a la base de datos: ' + err.stack);
        return;
    }
    console.log('Conexión a la base de datos exitosa');
});

// Ruta per a obtenir dades de la base de dades
app.get('/api/dadesDB', (req, res) => {
    connection.query('SELECT * Usuarios', (error, results, fields) => {
        if (error) throw error;
        res.send(JSON.stringify(result));
    });
});


io.on('connection', (socket) => {
    console.log('Un usuario se ha conectado');

    socket.on('chat message', (msg) => {
        io.emit('chat message', msg);
    });

    socket.on('disconnect', () => {
        console.log('Un usuario se ha desconectado');
    });
});
