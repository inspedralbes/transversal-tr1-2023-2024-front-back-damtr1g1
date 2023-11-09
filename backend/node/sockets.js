// Funciones de configuración de socket
function EnviarJson(io) {
  io.on("connection", (socket) => {
    console.log("Un usuario se ha conectado");

    let data = {
    };

    socket.emit("json", data); // Envia el JSON al cliente cuando se conecta

    // socket.on('enviarComanda', (comanda) => {
    //   data.comandes.push(comanda);
    //   console.log(data);
    //   socket.emit('json', data);
    // })
    socket.on('eliminaComanda', (index) => {
      data.comandes.splice(index, 1);
      socket.emit('json', data);
    })
  });
}

module.exports = { EnviarJson };
