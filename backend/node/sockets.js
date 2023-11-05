// Funciones de configuración de socket
  function EnviarJson(io) {
  io.on('connection', (socket) => {
    console.log('Un usuario se ha conectado');

    const data = {
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
  });
}

module.exports = { EnviarJson };