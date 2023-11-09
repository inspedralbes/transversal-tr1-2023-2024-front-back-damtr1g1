// Funciones de configuración de socket
function EnviarJson(io) {
  io.on("connection", (socket) => {
    console.log("Un usuario se ha conectado");

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
              {
                nom: "poma verda",
                unitats: 5,
              },
              {
                nom: "poma verda",
                unitats: 5,
              },
              {
                nom: "poma verda",
                unitats: 5,
              },
              {
                nom: "poma verda",
                unitats: 5,
              },
            ],
            dia: "25/10/2023",
            hora: "13:46",
          },
        },
        {
          comanda: {
            productes: [
              {
                nom: "patata1",
                unitats: 3,
              },
              {
                nom: "pera",
                unitats: 2,
              },
            ],
            dia: "25/10/2023",
            hora: "13:46",
          },
        },
        {
          comanda: {
            productes: [
              {
                nom: "patata2",
                unitats: 3,
              },
              {
                nom: "pera",
                unitats: 2,
              },
            ],
            dia: "25/10/2023",
            hora: "13:46",
          },
        },
        {
          comanda: {
            productes: [
              {
                nom: "patata3",
                unitats: 3,
              },
              {
                nom: "pera",
                unitats: 2,
              },
            ],
            dia: "25/10/2023",
            hora: "13:46",
          },
        },
        {
          comanda: {
            productes: [
              {
                nom: "patata4",
                unitats: 3,
              },
              {
                nom: "pera",
                unitats: 2,
              },
            ],
            dia: "25/10/2023",
            hora: "13:46",
          },
        },
        {
          comanda: {
            productes: [
              {
                nom: "patata5",
                unitats: 3,
              },
              {
                nom: "pera",
                unitats: 2,
              },
            ],
            dia: "25/10/2023",
            hora: "13:46",
          },
        },
      ],
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
