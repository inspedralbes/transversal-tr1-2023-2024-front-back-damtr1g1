const fs = require("fs");
const path = require("path");

// Directori on es guardaran les imatges
const directoriImatges = path.join(__dirname, "imatges_productes");

// Funció per eliminar una imatge
function eliminarImatge(nomFitxer) {
  const rutaFitxer = path.join(directoriImatges, nomFitxer);

  // Comprovar si el fitxer existeix abans d'eliminar-lo
  if (fs.existsSync(rutaFitxer)) {
    fs.unlinkSync(rutaFitxer);
    console.log("Imatge eliminada correctament.");
  } else {
    console.log("El fitxer no existeix.");
  }
}

module.exports = { eliminarImatge };
