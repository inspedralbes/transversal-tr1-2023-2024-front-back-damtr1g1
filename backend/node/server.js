const express = require("express");
const http = require("http");
const mysql = require("mysql");
const PORT = 3000;
const app = express();
const server = http.createServer(app);

const { Server } = require("socket.io");
const io = new Server(server);

// Configuració de la conexió a la base de dades
var con = null;

function crearDBConnnection() {
    con = mysql.createConnection({
        host: "dam.inspedralbes.cat",
        user: "a22tomybanog_Projecte1",
        password: "Projecte1",
        database: "a22tomybanog_Projecte1",
    });

    con.connect((err) => {
        if (err) {
            console.error("Error al conectar a la base de datos: " + err.stack);
            return;
        }
        console.log("Conexión a la base de datos exitosa");
    });
}

app.get("/", function (req, res) {
    res.send("Conectat al server");
});

// Ruta per a validar el login
app.get("/api/validacioLogin", (req, res) => {
    const usuarioSolicitado = req.query.usuario;
    const contrasenyaSolicitada = req.query.contrasenya;

    crearDBConnnection().then((con) => {
        con.query("SELECT * FROM Usuarios", (error, results, fields) => {
            if (error) {
                return res.status(500).json({ error: "Ocurrió un error al consultar la base de datos." });
            }
            const usuarioEncontrado = results.find(
                (user) => user.usuario === usuarioSolicitado && user.contrasenya === contrasenyaSolicitada
            );

            if (usuarioEncontrado) {
                closeDBconnection(con);
                return res.status(200).json({ Boolean: true });
            } else {
                closeDBconnection(con);
                return res.status(401).json({ Boolean: false });
            }
        });
    }).catch((error) => {
        console.error(error);
        return res.status(500).json({ error: "Ocurrió un error al conectar con la base de datos." });
    });
});

function closeDBconnection() {
    con.end((err) => {
        if (err) {
            console.error("Error al cerrar la conexión: " + err.stack);
            return;
        }
        console.log("Conexión cerrada exitosamente.");
    });
}

//falta fer lo dels fixers d'imatges
function crearProducte(idproducte, imatge_Nom, producte_Categoria, producte_Definicio, producte_Nom, producte_Preu, producte_Quantitat) {
    const nouProducte = {
        id_producte: idproducte,
        imatgeNom: imatge_Nom,
        producteCategoria: producte_Categoria,
        producteDefinicio: producte_Definicio,
        producteNom: producte_Nom,
        productePreu: producte_Preu,
        producteQuantitat: producte_Quantitat

    };

    // Inserta nou producte en la tabla de Producte
    con.query('INSERT INTO Producte SET ?', nouProducte, (error, results) => {
        if (error) {
            console.error('Error al insertar Producte:', error);
        } else {
            console.log('Producte insertado con éxito. ID del Producte:', nouProducte.id_producte);
        }


    });
}
//function eliminar productes
function deleteProducte(idProducteEliminar) {
    con.query('DELETE FROM Producte WHERE id_producte=?', idProducteEliminar, (error, results) => {
        if (error) {
            console.error('Error al insertar Producte:', error);
        } else {
            console.log('Producte eliminado con éxito. ID del Producte:', idProducteEliminar);
        }

    });
}
//function crear carrito
function crearCarrito(idCarrito, nomUsuari) {
    const nouCarrito = {
        id_carrito: idCarrito,
        usuario: nomUsuari
    }
    con.query('INSERT INTO Carrito SET ?', nouCarrito, (error, results) => {
        if (error) {
            console.error('Error al insertar Carrito:', error);
        } else {
            console.log('Carrito insertado con éxito. ID del Carrito:', nouCarrito.i);
        }


    });
}

server.listen(PORT, function () {
    console.log("Server running on port " + PORT);
});