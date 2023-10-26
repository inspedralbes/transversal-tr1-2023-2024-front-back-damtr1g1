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
function crearProducte(
    idproducte,
    imatge_Nom,
    producte_Categoria,
    producte_Definicio,
    producte_Nom,
    producte_Preu,
    producte_Quantitat
) {
    const nouProducte = {
        id_producte: idproducte,
        imatgeNom: imatge_Nom,
        producteCategoria: producte_Categoria,
        producteDefinicio: producte_Definicio,
        producteNom: producte_Nom,
        productePreu: producte_Preu,
        producteQuantitat: producte_Quantitat,
    };

    // Inserta nou producte en la tabla de Producte
    con.query("INSERT INTO Producte SET ?", nouProducte, (error, results) => {
        if (error) {
            console.error("Error al insertar Producte:", error);
        } else {
            console.log(
                "Producte insertado con éxito. ID del Producte:",
                nouProducte.id_producte
            );
        }
    });
}

//function eliminar productes
function deleteProducte(idProducteEliminar) {
    con.query(
        "DELETE FROM Producte WHERE id_producte=?",
        idProducteEliminar,
        (error, results) => {
            if (error) {
                console.error("Error al insertar Producte:", error);
            } else {
                console.log(
                    "Producte eliminado con éxito. ID del Producte:",
                    idProducteEliminar
                );
            }
        }
    );
}

//function crear carrito
function crearCarrito(idCarrito, nomUsuari) {
    const nouCarrito = {
        id_carrito: idCarrito,
        usuario: nomUsuari,
    };
    con.query("INSERT INTO Carrito SET ?", nouCarrito, (error, results) => {
        if (error) {
            console.error("Error al insertar Carrito:", error);
        } else {
            console.log(
                "Carrito insertado con éxito. ID del Carrito:",
                nouCarrito.id_carrito
            );
        }
    });
}

//function crear carrito_productes
function crearCarritoProducte(
    quantitat,
    idCarrito,
    idCarritoProducto,
    idProducto
) {
    const nouCarritoProducte = {
        cantidad: quantitat,
        id_carrito: idCarrito,
        id_carrito_producto: idCarritoProducto,
        id_producte: idProducto,
    };
    con.query(
        "INSERT INTO Carrito_Productos SET ?",
        nouCarritoProducte,
        (error, results) => {
            if (error) {
                console.error("Error al insertar Carrito Producto:", error);
            } else {
                console.log(
                    "Carrito Producto insertado con éxito. ID del Carrito Producto:",
                    nouCarritoProducte.id_carrito_producto
                );
            }
        }
    );
}

//function borrar carrito_producto
function deleteCarritoProducto(idCarritoProductoEliminar) {
    con.query(
        "DELETE FROM Carrito_Productos WHERE id_carrito_producto=?",
        idCarritoProductoEliminar,
        (error, results) => {
            if (error) {
                console.error("Error al borrar carrito_producto:", error);
            } else {
                console.log(
                    "carrito Producto eliminado con éxito. ID del carrito Producto:",
                    idCarritoProductoEliminar
                );
            }
        }
    );
}

app.get("/", function (req, res) {
    res.send("Conectat al server");
});

// Ruta per a validar el login
app.get("/api/validacioLogin", async (req, res) => {
    const usuarioSolicitado = req.query.usuario; // Obté l'usuari del client
    const contrasenyaSolicitada = req.query.contrasenya; // Obté la contrasenya del client

    await crearDBConnnection();
    // Consulta la DB para validar l'usuari i la contrasenya
    con.query("SELECT * FROM Usuarios", (error, results, fields) => {
        if (error) {
            // Errors
            return res
                .status(500)
                .json({ error: "Ocurrió un error al consultar la base de datos." });
        }

        // Verifica si hay algún usuario que coincida con la solicitud
        const usuarioEncontrado = results.find(
            (user) =>
                user.usuario === usuarioSolicitado &&
                user.contrasenya === contrasenyaSolicitada
        );

        if (usuarioEncontrado) {
            // Si el usuario y la contraseña coinciden, devuelve un mensaje de éxito o los datos relevantes
            return res.status(200).json({ Boolean: true });
        } else {
            // Si el usuario y la contraseña no coinciden, devuelve un mensaje de error
            return res.status(401).json({ Boolean: false });
        }
    });
    closeDBconnection();
});

app.get('api/AfegirProductes', async (req, res) => {
    const idproducte = req.query.idproducte; // Obté la id producte del client
    const imatge_Nom = req.query.imatge; // Obté la imatge
    const producte_Categoria = req.query.producte_Categoria; // Obté la categoria del producte
    const producte_Definicio = req.query.producteDefinicio; // Obté la definicio del producte
    const producte_Nom = req.query.producteNom; // Obté el nom del producte
    const producte_Preu = req.query.productePreu; // Obté el preu del producte
    const producte_Quantitat = req.query.producte_Quantitat; // Obté la quantitat del producte
    await crearDBConnnection();
    await crearProducte(idproducte, imatge_Nom, producte_Categoria, producte_Definicio, producte_Nom, producte_Preu, producte_Quantitat);


});

server.listen(PORT, function () {
    console.log("Server running on port " + PORT);
});

