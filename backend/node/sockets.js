// Funciones de configuración de socket
  function EnviarJson(io) {
  io.on('connection', (socket) => {
    console.log('Un usuario se ha conectado');

    let data = {
      comandes: [
        {
          comanda: {
            productes: [
              {
                nom: "patata",
                unitats: 3,
              },
              {
                nom: "pera",
                unitats: 2,
              },
              {
                nom: "poma verda",
                unitats: 5,
              },
            ],
            hora: "13:46",
          },
        },
      ],
    };

    socket.emit('json', data); // Envia el JSON al cliente cuando se conecta
    
    // socket.on('enviarComanda', (comanda) => {
    //   data.comandes.push(comanda);
    //   console.log(data);
    //   socket.emit('json', data);
    // })
  });
}

module.exports = { EnviarJson };