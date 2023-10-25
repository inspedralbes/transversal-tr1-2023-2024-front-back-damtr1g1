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
  const usuarioSolicitado = req.query.usuario; // Obté l'usuari del client
  const contrasenyaSolicitada = req.query.contrasenya; // Obté la contrasenya del client

  crearDBConnnection()
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

function closeDBconnection() {
  con.end((err) => {
    if (err) {
      console.error("Error al cerrar la conexión: " + err.stack);
      return;
    }
    console.log("Conexión cerrada exitosamente.");
  });
}

server.listen(PORT, function () {
  console.log("Server running on port " + PORT);
});
