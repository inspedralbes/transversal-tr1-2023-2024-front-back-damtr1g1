const express = require("express");
const http = require("http");
const mysql = require("mysql");
const PORT = 3001;
const app = express();
const server = http.createServer(app);

const { Server } = require("socket.io");
const io = new Server(server);
const { desarImatge, eliminarImatge } = require('./gestio_imatges');

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
    imatge_Nom,
    producte_Categoria,
    producte_Definicio,
    producte_Nom,
    producte_Preu,
    producte_Quantitat
) {
    const nouProducte = {
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
                nouProducte.producteNom
            );
        }
    });
}
//function select productes
function selectProducte(callback){
    

    con.query('SELECT * FROM Producte', (err, results, fields) => {
        if (err) {
            console.error('Error al realizar la consulta: ' + err.message);
            callback(err, null); // Devuelve el error en el callbac
            return;
        }
        
        const ProductesJSON = JSON.stringify(results); // Convierte el objeto a JSON

        callback(null, ProductesJSON); //
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
function crearCarrito(nomUsuari) {
    const nouCarrito = {
        usuario: nomUsuari,
    };
    con.query("INSERT INTO Carrito SET ?", nouCarrito, (error, results) => {
        if (error) {
            console.error("Error al insertar Carrito:", error);
        } else {
            console.log(
                "Carrito insertado con éxito"
            );
        }
    });
}
// borrar carrito y select carrito
function selectCarrito(callback){
    con.query('SELECT * FROM Carrito', (err, results, fields) => {
        if (err) {
            console.error('Error al realizar la consulta: ' + err.message);
            callback(err, null); // Devuelve el error en el callbac
            return;
        }
        
        const CarritoJSON = JSON.stringify(results); // Convierte el objeto a JSON

        callback(null, CarritoJSON); //
    });
}
function deleteCarrito(idCarrito){
    con.query("DELETE FROM Carrito WHERE id_carrito=?",idCarrito,(error, results) => {
        if (error) {
            console.error("Error al eliminar carrito:", error);
        } else {
            console.log(
                "carrito eliminado con éxito. ID del carrito:",
                idCarrito
            );
        }
    })
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
//select carrito product

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

app.post('/api/AddProduct', async (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    const imatge_Nom = req.query.imatge; // Obté la imatge
    const producte_Categoria = req.query.producteCategoria; // Obté la categoria del producte
    const producte_Definicio = req.query.producteDefinicio; // Obté la definicio del producte
    const producte_Nom = req.query.producteNom; // Obté el nom del producte
    const producte_Preu = req.query.productePreu; // Obté el preu del producte
    const producte_Quantitat = req.query.producteQuantitat; // Obté la quantitat del producte
    await crearDBConnnection(); // Creem la conexió
    await crearProducte(imatge_Nom, producte_Categoria, producte_Definicio, producte_Nom, producte_Preu, producte_Quantitat); // Inserta els productes a la DB
    await desarImatge(producte_Nom ,imatge_Nom) // On imate_Nom serie el url 
    closeDBconnection(); // Tanquem la conexió 
    res.send({message: 'Afegit correctament'})
});

app.get('/api/selectProducte', async (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    await crearDBConnnection(); // Creem la conexió
    await selectProducte((err, productesJSON) => {
        if (err) {
            console.error('Error: ' + err);
        } else {
            res.json(JSON.parse(productesJSON))
        }
    });
    
    await closeDBconnection(); // Tanquem la conexió 
});

app.post('/api/DeleteProduct', async (req,res) =>{
    res.header("Access-Control-Allow-Origin", "*");
    const idproducte = req.query.idproducte; // Obté la id producte del client
    await crearDBConnnection();
    await deleteProducte(idproducte);
    closeDBconnection();
    res.json({ message: 'Eliminat correctament' })
});

app.post('/api/UpdateProduct', async (req,res) =>{
    res.header("Access-Control-Allow-Origin", "*");
});

app.post('/api/CreateShoppingCart', async (req,res) =>{
    res.header("Access-Control-Allow-Origin", "*");
    const nomUsuari = req.query.nomUsuari;

    await crearDBConnnection();
    await crearCarrito(nomUsuari);
    closeDBconnection();
    res.json({ message: 'Creat correctament' });
});
app.get('/api/selectCarrito', async (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    await crearDBConnnection(); // Creem la conexió
    await selectCarrito((err, CarritoJSON) => {
        if (err) {
            console.error('Error: ' + err);
        } else {
            res.json(JSON.parse(CarritoJSON))
        }
    });
    
    await closeDBconnection(); // Tanquem la conexió 
});

app.post('/api/DeleteCarrito', async (req,res) =>{
    res.header("Access-Control-Allow-Origin", "*");
    const idCarrito = req.query.idCarrito; // Obté la id producte del client
    await crearDBConnnection();
    await deleteCarrito(idCarrito);
    closeDBconnection();
    res.json({ message: 'Eliminat correctament' })
});

app.post('/api/createShoppingCartProduct', async (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    const quantitat = req.query.quantitat;
    const idCarrito = req.query.idCarrito;
    const idCarritoProducto = req.query.idCarritoProducto
    const idProducto = req.query.idProducto

    await crearDBConnnection();
    await crearCarritoProducte(quantitat, idCarrito, idCarritoProducto, idProducto);
    closeDBconnection();
    res.json({ message: 'Creat correctament' });
});

app.post('/api/deleteShoppingCartProduct', async (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    const idProducto = req.query.idProduct

    await crearDBConnnection();
    await deleteCarritoProducto(idProducto);
    closeDBconnection();
    res.json({ message: 'Eliminat correctament' })
});

app.post('/api/SaveImages', async (req, res) =>{
    res.header("Access-Control-Allow-Origin", "*");
    const nomFitxer = req.query.nomFitxer
    const dadesImatge = req.query.dadesImatge

    await crearDBConnnection();
    await desarImatge(nomFitxer, dadesImatge);
    closeDBconnection();
});

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



