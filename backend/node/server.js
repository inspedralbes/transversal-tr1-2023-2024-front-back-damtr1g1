const express = require('express');
const http = require('http');
const socketIo = require('socket.io');
//const mysql = require('mysql');
const PORT = 3000

const app = express();
const server = http.createServer(app);
const io = socketIo(server);

server.listen(PORT, function () {
    console.log("Server running on port " + PORT);
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



