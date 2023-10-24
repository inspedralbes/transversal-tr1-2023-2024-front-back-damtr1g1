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

// Configuració de la conexió a la base de dades
const dbConfig = {
    host: 'dam.inspedralbes.cat',
    user: 'a22tomybanog_Projecte1',
    password: 'Projecte1',
    database: 'a22tomybanog_Projecte1'
};

// Crear la connexió a la base de datos
const connection = mysql.createConnection(dbConfig);

connection.connect((err) => {
    if (err) {
        console.error('Error al conectar a la base de datos: ' + err.stack);
        return;
    }
    console.log('Conexión a la base de datos exitosa');
});

function closeDBconnection() {
    connection.end((err) => {
        if (err) {
            console.error('Error al cerrar la conexión: ' + err.stack);
            return;
        }
        console.log('Conexión cerrada exitosamente.');
    });
}



app.get('/', function (req, res) {
    res.send("Conectat al server")
})

// Ruta para obtener datos de la base de datos
app.get('/api/validacioLogin', (req, res) => {
    const usuarioSolicitado = req.query.usuario; // Obtén el usuario de la solicitud
    const contrasenyaSolicitada = req.query.contrasenya; // Obtén la contraseña de la solicitud

    // Consulta la base de datos para validar el usuario y la contraseña
    connection.query('SELECT * FROM Usuarios', (error, results, fields) => {
        if (error) {
            // Manejo de errores en caso de que ocurra algún problema con la consulta
            return res.status(500).json({ error: 'Ocurrió un error al consultar la base de datos.' });
        }

        // Verifica si hay algún usuario que coincida con la solicitud
        const usuarioEncontrado = results.find(user => user.usuario === usuarioSolicitado && user.contrasenya === contrasenyaSolicitada);

        if (usuarioEncontrado) {
            // Si el usuario y la contraseña coinciden, devuelve un mensaje de éxito o los datos relevantes
            return res.status(200).json({ message: 'Inicio de sesión exitoso' });
        } else {
            // Si el usuario y la contraseña no coinciden, devuelve un mensaje de error
            return res.status(401).json({ error: 'Credenciales inválidas' });
        }
    });
});

app.get('/api/DB', (req, res) => {
    // Consultar la base de datos para validar el usuario y la contraseña
    connection.query('SELECT * FROM Usuarios', (error, results, fields) => {
        res.status(200).json(results);
    });
})

// Código para cerrar la conexión cuando el servidor se cierre
process.on('SIGINT', () => {
    connection.end();
});
