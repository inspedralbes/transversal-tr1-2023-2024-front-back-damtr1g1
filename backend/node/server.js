const express = require("express");
const path = require("path");
const http = require("http");
const mysql = require("mysql");
const fs = require("fs");
const multer = require("multer");
const imatges = multer({ dest: "./img/productes/" });
const PORT = 3003;
const app = express();
const cors = require("cors");
const server = http.createServer(app);
const { spawn } = require("child_process");
const { EnviarJson } = require('./sockets.js');

const { Server } = require("socket.io");
const io = new Server(server, {
  cors: {
    origin: "*",
    methods: ["GET", "POST", "PUT", "DELETE"],
  },
  path: "/node/"
});


app.use(cors());

EnviarJson(io);

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

function selectUserDataByName(nom, callback) {
  con.query('SELECT * FROM Usuaris WHERE nom = ?', [nom], (err, results, fields) => {
    if (err) {
      console.error("Error al realizar la consulta: " + err.message);
      callback(err, null); // Devuelve el error en el callback
      return;
    }
    console.log("Resultados de la consulta:", results); // Verifica los resultados de la consulta

    // Procesa los datos devueltos según la estructura de los resultados
    if (results.length > 0) {
      const UsuariJSON = JSON.stringify(results[0]); // Obtén el primer resultado como JSON
      console.log("Usuario encontrado:", UsuariJSON); // Verifica el usuario encontrado
      callback(null, UsuariJSON); // Devuelve el JSON en el callback
    } else {
      console.log("Usuario no encontrado para el nombre proporcionado");
      callback(null, null); // No se encontraron resultados, devuelve null en el callback
    }
  });
}



// falta fer lo dels fixers d'imatges                           (comprobada)
function crearProducte(imatgeNom, nom, definicio, preu, categoria, quantitat) {
  const nouProducte = {
    imatgeNom: imatgeNom,
    nom: nom,
    definicio: definicio,
    preu: preu,
    categoria_id: categoria,
    quantitat: quantitat,
  };

  // Inserta nou producte en la tabla de Producte
  con.query("INSERT INTO Producte SET ?", nouProducte, (error, results) => {
    if (error) {
      console.error("Error al insertar Producte:", error);
    } else {
      console.log(
        "Producte insertado con éxito. Nom del Producte:",
        nouProducte.nom
      );
    }
  });
}
// function select productes                                    (comprobada)
function selectProducte(callback) {
  con.query("SELECT * FROM Producte", (err, results, fields) => {
    if (err) {
      console.error("Error al realizar la consulta: " + err.message);
      callback(err, null); // Devuelve el error en el callbac
      return;
    }

    const ProductesJSON = JSON.stringify(results); // Convierte el objeto a JSON

    callback(null, ProductesJSON); //
  });
}

// function select producte per id
function selectProducteById(id, callback) {
  con.query(
    `SELECT * FROM Producte WHERE id = ${id}`,
    (err, results, fields) => {
      if (err) {
        console.error("Error al realizar la consulta: " + err.message);
        callback(err, null); // Devuelve el error en el callback
        return;
      }
      const ProducteJSON = JSON.stringify(results[0]); // Convierte el objeto a JSON

      callback(null, ProducteJSON); //
    }
  );
}

// function eliminar productes                                  (comprobada)
function deleteProducte(idProducteEliminar) {
  
  con.query(
    "DELETE FROM Producte WHERE id = ?",
    idProducteEliminar,
    (error, deleteResults) => {
      if (error) {
        console.error("Error al eliminar el producto:", error);
      } else {
        console.log("Producto eliminado con éxito. ID del Producto:", idProducteEliminar);
        // Ahora puedes eliminar la imagen asociada
        
      }
    }
  );
}
//funcion delete imatge productes
function deleteImatgeProducte(idProducteEliminar){
  let imatgeNom = "";

  con.query("SELECT imatgeNom FROM Producte WHERE id=?", [idProducteEliminar], (err, results, fields) => {
    if (err) {
      console.error("Error al realizar la consulta: " + err.message);
      return;
    }

    if (results.length > 0) {
      imatgeNom = results[0].imatgeNom;

      fs.unlink(`./img/productes/${imatgeNom}`, (error) => {
        if (error) {
          console.error("Error al eliminar la imagen:", error);
        } else {
          console.log("Imagen eliminada con éxito.");
        }
      });
    }
  });

  
}
// function update productes                                    (comprobada)
function updateProducte(
  idProducteUpdate,
  imatgeNom,
  nom,
  definicio,
  preu,
  categoria,
  quantitat
) {
  const update = {
    imatgeNom: imatgeNom,
    nom: nom,
    definicio: definicio,
    preu: preu,
    categoria_id: categoria,
    quantitat: quantitat,
  };
  const id = idProducteUpdate;

  con.query(
    `UPDATE Producte SET ? WHERE id = ?`,
    [update, id],
    (error, results) => {
      if (error) {
        console.error("Error al actualizar Producto:", error);
      } else {
        console.log("Producto actualizado con éxito");
      }
    }
  );
}
// function crear categoria                                     (comprobada)
function addCategoria(nomCategoria) {
  con.query(
    "INSERT INTO Categoria SET nom=?",
    nomCategoria,
    (error, results) => {
      if (error) {
        console.error("Error al insertar Categoria:", error);
      } else {
        console.log(
          "Categoria insertada con éxito. Nom de la Categoria:",
          nomCategoria
        );
      }
    }
  );
}
// function delete categoria                                    (comprobada)
function deleteCategoria(idCategoria) {
  con.query(
    "DELETE FROM Categoria WHERE id=?",
    idCategoria,
    (error, results) => {
      if (error) {
        console.error("Error al borrar Categoria:", error);
      } else {
        console.log(
          "Categoria vorrada con éxito. ID de la Categoria:",
          idCategoria
        );
      }
    }
  );
}
// function select categoria                                    (comprobada)
function selectCategoria(callback) {
  con.query("SELECT * FROM Categoria", (err, results, fields) => {
    if (err) {
      console.error("Error al realizar la consulta: " + err.message);
      callback(err, null); // Devuelve el error en el callbac
      return;
    }

    const CategoriesJSON = JSON.stringify(results); // Convierte el objeto a JSON

    callback(null, CategoriesJSON); //
  });
}
// function update categoria                                    (comprobada)
function updateCategoria(idCategoria, nomCategoria) {
  con.query(
    "UPDATE Categoria SET nom=? WHERE id=?",
    [nomCategoria, idCategoria],
    (error, results) => {
      if (error) {
        console.error("Error al actualizar Categoria:", error);
      } else {
        console.log("Categoria actualizada con éxito");
      }
    }
  );
}
// function crear carrito                                       (comprobada)
function crearCarrito(nomUsuari) {
  const nouCarrito = {
    usuari: nomUsuari,
  };
  con.query("INSERT INTO Carret SET ?", nouCarrito, (error, results) => {
    if (error) {
      console.error("Error al insertar Carrito:", error);
    } else {
      console.log("Carrito insertado con éxito");
    }
  });
}
// function select carrito                                      (comprobada)
function selectCarrito(callback) {
  con.query("SELECT * FROM Carret", (err, results, fields) => {
    if (err) {
      console.error("Error al realizar la consulta: " + err.message);
      callback(err, null); // Devuelve el error en el callbac
      return;
    }

    const CarritoJSON = JSON.stringify(results); // Convierte el objeto a JSON

    callback(null, CarritoJSON); //
  });
}
// function delete carrito                                      (comprobada)
function deleteCarrito(idCarrito) {
  con.query("DELETE FROM Carret WHERE id=?", idCarrito, (error, results) => {
    if (error) {
      console.error("Error al eliminar carrito:", error);
    } else {
      console.log("carrito eliminado con éxito. ID del carrito:", idCarrito);
    }
  });
}
// function crear carrito_productes                             (comprobada)
function crearCarritoProducte(quantitat, idCarrito, idProducto) {
  const nouCarritoProducte = {
    quantitat: quantitat,
    id_carret: idCarrito,
    id_producte: idProducto,
  };
  con.query(
    "INSERT INTO Carret_Productes SET ?",
    nouCarritoProducte,
    (error, results) => {
      if (error) {
        console.error("Error al insertar Carrito Producto:", error);
      } else {
        console.log("Carrito Producto insertado con éxito.");
      }
    }
  );
}
// select carrito product                                       (comprobada)
function selectCarritoProducto(callback) {
  con.query("SELECT * FROM Carret_Productes", (err, results, fields) => {
    if (err) {
      console.error("Error al realizar la consulta: " + err.message);
      callback(err, null); // Devuelve el error en el callbac
      return;
    }

    const CarritoProductosJSON = JSON.stringify(results); // Convierte el objeto a JSON

    callback(null, CarritoProductosJSON); //
  });
}
// function borrar carrito_producto                             (comprobada)
function deleteCarritoProducto(idCarritoProductoEliminar) {
  con.query(
    "DELETE FROM Carret_Productes WHERE id_carret_producte = ?",
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
// function crear comanda(no se si servira de algo)             (comprobada)
function crearComanda(id_carret, usuari) {
  const fechaActual = new Date().toISOString().slice(0, 10); // Obtiene la fecha actual en formato "YYYY-MM-DD"
  const comanda = {
    data: fechaActual,
    id_carret: id_carret,
    usuari: usuari,
  };

  con.query("INSERT INTO Comanda SET ?", comanda, (error, results) => {
    if (error) {
      console.error("Error al insertar Comanda:", error);
    } else {
      console.log("Comanda insertada con éxito. Usuario:", comanda.usuari);
    }
  });
}
// function select comandes                                     (comprobada)
function selectComandesFinalitzades(callback) {
  con.query("SELECT * FROM Comanda WHERE finalitzat = 1", (err, results, fields) => {
    if (err) {
      console.error("Error al realizar la consulta: " + err.message);
      callback(err, null); // Devuelve el error en el callbac
      return;
    }

    const ComandaJSON = JSON.stringify(results); // Convierte el objeto a JSON

    callback(null, ComandaJSON); //
  });
}

// function select comandes                                     (comprobada)
function selectComandes(callback) {
  con.query("SELECT * FROM Comanda ", (err, results, fields) => {
    if (err) {
      console.error("Error al realizar la consulta: " + err.message);
      callback(err, null); // Devuelve el error en el callbac
      return;
    }

    const ComandaJSON = JSON.stringify(results); // Convierte el objeto a JSON

    callback(null, ComandaJSON); //
  });
}

// function delete comandes                                     (comprobada)
function deleteComandes(id_Comanda) {
  con.query(
    "DELETE FROM Comanda WHERE id = ?",
    [id_Comanda],
    (error, results) => {
      if (error) {
        console.error("Error al borrar comanda:", error);
      } else {
        console.log(
          "Comanda eliminada con éxito. ID de la comanda:",
          id_Comanda
        );
      }
    }
  );
}
// function update comandes                                     (comprobada)
function updateComandes(id_comanda, finalitzat) {
  con.query(
    "UPDATE Comanda SET finalitzat = ? WHERE id = ?",
    [finalitzat, id_comanda],
    (error, results) => {
      if (error) {
        console.error("Error al actualizar Comanda:", error);
      } else {
        console.log("Comanda actualizada con éxito");
      }
    }
  );
}
app.get("/", function (req, res) {
  res.send("Conectat al server");
});

// Ruta para validar el login
app.get("/api/validateLogin", async (req, res) => {
  const usuarioSolicitado = req.query.nom; // Obtén el usuario del cliente
  const contrasenyaSolicitada = req.query.contrasenya; // Obtén la contraseña del cliente

  try {
    await crearDBConnnection();
    // Consulta la DB para validar el usuario y la contraseña
    con.query("SELECT nom, contrasenya FROM Usuaris", (error, results, fields) => {
      if (error) {
        // Manejar errores
        return res.status(500).json({ error: "Ocurrió un error al consultar la base de datos." });
      }

      // Verifica si hay algún usuario que coincida con la solicitud
      const usuarioEncontrado = results.some(
        (user) =>
          user.nom === usuarioSolicitado &&
          user.contrasenya === contrasenyaSolicitada
      );

      if (usuarioEncontrado === true) {
        const loginResponse = { loginBool: true };
        return res.status(200).json(loginResponse);
      } else {
        // Si el usuario y la contraseña no coinciden, devuelve un mensaje de error
        const loginResponse = { loginBool: false };
        return res.status(401).json(loginResponse);
      }
    });
  } catch (error) {
    console.error("Error: ", error);
    return res.status(500).json({ error: "Ocurrió un error en el servidor." });
  } finally {
    await closeDBconnection();
  }
});

app.get("/api/getUserDataByName", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  await crearDBConnnection(); // Creem la conexió
  await selectUserDataByName(req.query.nom, (err, UsuariJSON) => {
    if (err) {
      console.error("Error: " + err);
    } else {
      res.json(JSON.parse(UsuariJSON));
    }
  });

  await closeDBconnection(); // Tanquem la conexió
});

// Ruta afegir producte                                         (comprobada)
app.post("/api/addProduct", imatges.single("img"), async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");

  fs.rename(
    `./img/productes/${req.file.filename}`,
    `./img/productes/${req.query.imatgeNom}`,
    async function () {
      const imatgeNom = req.query.imatgeNom; // Obté la imatge
      const categoria = req.query.categoria; // Obté la categoria del producte
      const definicio = req.query.definicio; // Obté la definicio del producte
      const nom = req.query.nom; // Obté el nom del producte
      const preu = req.query.preu; // Obté el preu del producte
      const quantitat = req.query.quantitat; // Obté la quantitat del producte
      await crearDBConnnection(); // Creem la conexió
      await crearProducte(
        imatgeNom,
        nom,
        definicio,
        preu,
        categoria,
        quantitat
      ); // Inserta els productes a la DB
      closeDBconnection(); // Tanquem la conexió
      res.send({ message: "Afegit correctament" });
    }
  );
});
// Ruta select productes                                        (comprobada)
app.get("/api/getProducts", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  await crearDBConnnection(); // Creem la conexió
  await selectProducte((err, productesJSON) => {
    if (err) {
      console.error("Error: " + err);
    } else {
      res.json(JSON.parse(productesJSON));
    }
  });

  await closeDBconnection(); // Tanquem la conexió
});

// Ruta select producte
app.get("/api/getProducteById", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  await crearDBConnnection(); // Creem la conexió
  await selectProducteById(req.query.id, (err, productesJSON) => {
    if (err) {
      console.error("Error: " + err);
    } else {
      res.json(JSON.parse(productesJSON));
    }
  });

  await closeDBconnection(); // Tanquem la conexió
});
// Ruta borrar producte                                         (comprobada)
app.post("/api/deleteProduct", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const idproducte = req.query.idproducte; // Obté la id producte del client
  console.log(idproducte);
  await crearDBConnnection();
  await deleteImatgeProducte(idproducte);
  await deleteProducte(idproducte);
  closeDBconnection();
  res.json({ message: "Eliminat correctament" });
});
// Ruta update producte                                         (comprobada)
app.post("/api/updateProduct", imatges.single("img"), async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const idProducteUpdate = req.query.idProducteUpdate; // Obté la id producte del client

  fs.rename(
    `./img/productes/${req.file.filename}`,
    `./img/productes/${req.query.imatgeNom}`,
    async function () {
      (imatgeNom = req.query.imatgeNom),
        (nom = req.query.nom),
        (definicio = req.query.definicio),
        (preu = req.query.preu),
        (categoria = req.query.categoria),
        (quantitat = req.query.quantitat),
        console.log(idProducteUpdate);
      await crearDBConnnection();
      await updateProducte(
        idProducteUpdate,
        imatgeNom,
        nom,
        definicio,
        preu,
        categoria,
        quantitat
      );
      closeDBconnection();
      res.json({ message: " Actualitzat" });
    }
  );
});
// Ruta afegir categoria                                        (comprobada)
app.post("/api/addCategoria", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const nomCategoria = req.query.nomCategoria;

  await crearDBConnnection();
  await addCategoria(nomCategoria);
  closeDBconnection();
  res.json({ message: "Creat correctament" });
});
// Ruta borrar categoria                                        (comprobada)
app.post("/api/deleteCategoria", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const idCategoria = req.query.idCategoria;

  await crearDBConnnection();
  await deleteCategoria(idCategoria);
  closeDBconnection();
  res.json({ message: "borrat correctament" });
});
// Ruta select categoria                                        (comprobada)
app.get("/api/getCategoria", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  await crearDBConnnection(); // Creem la conexió
  await selectCategoria((err, CategoriesJSON) => {
    if (err) {
      console.error("Error: " + err);
    } else {
      res.json(JSON.parse(CategoriesJSON));
    }
  });

  await closeDBconnection(); // Tanquem la conexió
});
// Ruta actualizar categoria                                    (comprobada)
app.post("/api/updateCategoria", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const idCategoria = req.query.idCategoria;
  const nomCategoria = req.query.nomCategoria;

  await crearDBConnnection();
  await updateCategoria(idCategoria, nomCategoria);
  closeDBconnection();
  res.json({ message: "actualitzat correctament" });
});
// Ruta crear carrito compra                                    (comprobada)
app.post("/api/addShoppingCart", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const nomUsuari = req.query.nomUsuari;

  await crearDBConnnection();
  await crearCarrito(nomUsuari);
  closeDBconnection();
  res.json({ message: "Creat correctament" });
});
// Ruta select carrito compra                                   (comprobada)
app.get("/api/getCart", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  await crearDBConnnection(); // Creem la conexió
  await selectCarrito((err, CarritoJSON) => {
    if (err) {
      console.error("Error: " + err);
    } else {
      res.json(JSON.parse(CarritoJSON));
    }
  });

  await closeDBconnection(); // Tanquem la conexió
});
// Ruta borrar carrito compra                                   (comprobada)
app.post("/api/deleteCart", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const idCarrito = req.query.idCarrito; // Obté la id producte del client
  await crearDBConnnection();
  await deleteCarrito(idCarrito);
  closeDBconnection();
  res.json({ message: "Eliminat correctament" });
});
// Ruta crear carrito producte                                  (comprobada)
app.post("/api/addShoppingCartProduct", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const quantitat = req.query.quantitat;
  const id_carret = req.query.id_carret;
  const id_producte = req.query.id_producte;

  await crearDBConnnection();
  await crearCarritoProducte(quantitat, id_carret, id_producte);
  closeDBconnection();
  res.json({ message: "Creat correctament" });
});
// Ruta select carrito producte                                 (comprobada)
app.get("/api/getCartProduct", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  await crearDBConnnection(); // Creem la conexió
  await selectCarritoProducto((err, CarritoProducteJSON) => {
    if (err) {
      console.error("Error: " + err);
    } else {
      res.json(JSON.parse(CarritoProducteJSON));
    }
  });

  await closeDBconnection(); // Tanquem la conexió
});
// Ruta borrar carrito producte                                 (comprobada)
app.post("/api/deleteShoppingCartProduct", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const idCarritoProductoEliminar = req.query.idCarritoProductoEliminar;

  await crearDBConnnection();
  await deleteCarritoProducto(idCarritoProductoEliminar);
  closeDBconnection();
  res.json({ message: "Eliminat correctament" });
});

// Ruta afegir comanda                                          (comprobada)
app.post("/api/addComanda", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  id_carret = req.query.id_carret;
  usuari = req.query.usuari;
  await crearDBConnnection();
  await crearComanda(id_carret, usuari);
  closeDBconnection();
  res.json({ message: "afegit correctament" });
});
// Ruta select comanda                                          (comprobada)
app.get("/api/getComanda", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  await crearDBConnnection(); // Creem la conexió
  await selectComandes((err, comandesJSON) => {
    if (err) {
      console.error("Error: " + err);
    } else {
      res.json(JSON.parse(comandesJSON));
    }
  });

  await closeDBconnection(); // Tanquem la conexió
});

// Ruta select comanda                                          (comprobada)
app.get("/api/getComandesFinalitzades", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  await crearDBConnnection(); // Creem la conexió
  await selectComandesFinalitzades((err, comandesJSON) => {
    if (err) {
      console.error("Error: " + err);
    } else {
      res.json(JSON.parse(comandesJSON));
    }
  });

  await closeDBconnection(); // Tanquem la conexió
});
// Ruta delete comanda                                          (comprobada)
app.post("/api/deleteComanda", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const id_comanda = req.query.id_comanda;

  await crearDBConnnection();
  await deleteComandes(id_comanda);
  closeDBconnection();
  res.json({ message: "Eliminat correctament" });
});
// Ruta update comanda                                          (comprobada)
app.post("/api/updateComanda", async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  id_comanda = req.query.id_comanda;
  finalitzat = req.query.finalitzat;
  await crearDBConnnection();
  await updateComandes(id_comanda, finalitzat);
  closeDBconnection();
  res.json({ message: "afegit correctament" });
});

server.listen(PORT, function () {
  console.log("Server running on port " + PORT);
});

// Recibir la imagen del nombre pedido
app.get("/api/getImage/:img", (req, res) => {
  res.sendFile(path.resolve(`./img/productes/${req.params.img}`));
});

//Recibir la imagen de la estadistica
app.get("/api/getImatgeEstadistiques/producteCantidad", (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.sendFile(path.resolve("img_estadistiques/producteCantidad.png"));
});

app.get("/api/getImatgeEstadistiques/producteMesVenut", (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.sendFile(path.resolve("img_estadistiques/producteMesVenut.png"));
});

app.get("/api/getImatgeEstadistiques/HoraMesComu", (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.sendFile(path.resolve("img_estadistiques/HoraMesComu.png"));
});

app.get("/api/getImatgeEstadistiques/HoraMesDiners", (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.sendFile(path.resolve("img_estadistiques/HoraMesDiners.png"));
});

app.get("/api/getImatgeEstadistiques/DinersComanda", (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.sendFile(path.resolve("img_estadistiques/DinersComanda.png"));
});

app.get("/api/getImatgeEstadistiques/TempsPreparacio", (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.sendFile(path.resolve("img_estadistiques/TempsPreparacio.png"));
});

//Ejecutar archivo python
app.get("/api/executeStatistics", async (req, res) => {

   ret = await callPython();
   res.header("Access-Control-Allow-Origin", "*");
   //res.send(`Salida del script Python: ${data}`);
   res.send(JSON.stringify(ret));
} );

function callPython( ) {
  const pythonProcess = spawn("python3", ["./estadistiques.py"]);
  var obj = {};

  var prom = new Promise( (resolve, fail) => {
    pythonProcess.stdout.on("data", (data) => {
      // Manejar la salida del proceso Python
  
     
      obj.value = data;
      
    });
  
    pythonProcess.stderr.on("data", (data) => {
      // Manejar errores del proceso Python
      obj.error = data;
  
      // res.header("Access-Control-Allow-Origin", "*");
      // res.status(500).send(`Error del script Python: ${JSON.stringify(obj)}`);
    });
  
    pythonProcess.on("close", (code) => {
      // Manejar el cierre del proceso Python
      resolve(obj);
    });


  });

 
  return prom;
}
function calculateTimeToNextHour() {
  callPython()
  const now = new Date();
  const currentMinute = now.getMinutes();
  const minutesToNextHour = 60 - currentMinute;
  const secondsToNextHour = minutesToNextHour * 60;
  const millisecondsToNextHour = secondsToNextHour * 1000;
  
  setTimeout(calculateTimeToNextHour, millisecondsToNextHour);
}

calculateTimeToNextHour()







