const express = require('express');
//const socketIo = require('socket.io');
const mysql = require('mysql');
const PORT = 3000;

const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());

//const io = socketIo(server);

app.listen(PORT, function () {
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

// Ruta per a validar el login 
app.get('/api/validacioLogin', (req, res) => {
    const usuarioSolicitado = req.query.usuario; // Obté l'usuari del client
    const contrasenyaSolicitada = req.query.contrasenya; // Obté la contrasenya del client

    // Consulta la DB para validar l'usuari i la contrasenya
    connection.query('SELECT * FROM Usuarios', (error, results, fields) => {
        if (error) {
            // Errors 
            return res.status(500).json({ error: 'Ocurrió un error al consultar la base de datos.' });
        }

        // Verifica si hay algún usuario que coincida con la solicitud
        const usuarioEncontrado = results.find(user => user.usuario === usuarioSolicitado && user.contrasenya === contrasenyaSolicitada);

        if (usuarioEncontrado) {
            // Si el usuario y la contraseña coinciden, devuelve un mensaje de éxito o los datos relevantes
            return res.status(200).json({ Boolean: true });
        } else {
            // Si el usuario y la contraseña no coinciden, devuelve un mensaje de error
            return res.status(401).json({ Boolean: false + "Error" });
        }
    });
});

// Enviar els poductes de la DB al client 
app.post('/api/ShoppingCartData', (req, res) => {
    connection.query('SELECT * FROM Usuarios', (error, results, fields) => {
        if (error) {
            // Errors 
            return res.status(500).json({ error: 'Ocurrió un error al consultar la base de datos.' });
        } else {
            return res.json(results)
        }
    })
})


// Eliminar productes de la DB y eniviar els canvis
app.post('/api/EliminarData', (req, res) => {
    const productId = req.body.id; // El client ens envia el id del producte

    const query = 'DELETE FROM Productos WHERE id = ?'; // Borrem el producte que ens han dit
    connection.query(query, [productId], (error, results, fields) => {
        if (error) {
            res.status(500).json({ message: 'Error al eliminar el producto.' });
        } else {
            res.status(200).json({ message: `Producto con ID ${productId} eliminado correctamente.` });
        }
    });
});

// Código para cerrar la conexión cuando el servidor se cierre
process.on('SIGINT', () => {
    connection.end();
});
