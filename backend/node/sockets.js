
// Comandes:
const ComandaState = "en curs";

io.on('connection', (socket) => {
  console.log('Un cliente se ha conectado.');

  socket.on('comandaStatus', (status) => {
    if (status === "Enviada") {
      console.log("Ha arribat una comanda del client"); // Resposta per al servidor
      socket.emit('comandaResponse', "La comanda s'ha enviat"); // Resposta per al client
      io.emit('comandaEnviada', "Ha arribat una comanda"); // Resposta per al amdin
    } else {
      console.log("L'estat de la comanda no es valid.");
      socket.emit('comandaResponse', "L'Estat de la comanda no es valid.");
    }
  })

  socket.on('comandaAcceptada', (status) => {
    if (status === "Acceptada") {
    console.log('La comanda ha sigut acceptada'); // Resposta per al servidor
    io.emit('comandaAcceptada', 'La comanda ha sigut acceptada'); // Resposta per al client
    } else {
      console.log("L'estat de la comanda no es valid.");
      socket.emit('comandaAcceptada', "L'Estat de la comanda no es valid.");
    }
  })

  socket.on('comandaInPogress', (status) =>{
    if(status === "En curs"){
      console.log('La comanda està en curs'); // Resposta per al servidor
      io.emit('comandaInPogress', 'La teva comanda està en curs'); // Resposta per al client
    } else{
      console.log("L'estat de la comanda no es valid.");
      socket.emit('comandaInPogress', "L'estat de la comanda no es valid.");
    }
  });

  socket.on('comandaPreparada', (status) =>{
    if(status === "Preparada"){
      console.log('La comanda està preparada'); // Resposta per al servidor
      io.emit('comandaPreparada', 'La comanda està preparada'); // Resposta per al client
    } else{
      console.log("L'estat de la comanda no es valid.");
      socket.emit('comandaPreparada', "L'estat de la comanda no es valid.");
    }
  })

  
  // Manejo de desconexión de sockets
  socket.on('disconnect', () => {
    console.log('Un cliente se ha desconectado.');
  });
});

module.exports = { funcion1, funcion2 };