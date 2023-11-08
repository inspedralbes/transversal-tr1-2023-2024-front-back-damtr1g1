<script setup></script>

<script>
export default {
  // setup() {
  // onActivated(() => {
  //   console.log("Tab 1 Activated");

  //   this.fotoQuantitats =
  //     import.meta.env.VITE_NODE_ROUTE +
  //     "getImatgeEstadistiques/producteCantidad?" +
  //     rand;
  //   this.fotoQuantitatsVenudes =
  //     import.meta.env.VITE_NODE_ROUTE +
  //     "getImatgeEstadistiques/producteMesVenut?" +
  //     rand;
  //   this.fotoHoraComu =
  //     import.meta.env.VITE_NODE_ROUTE +
  //     "getImatgeEstadistiques/HoraMesComu?" +
  //     rand;
  //   this.fotoHoraDiners =
  //     import.meta.env.VITE_NODE_ROUTE +
  //     "getImatgeEstadistiques/HoraMesDiners?" +
  //     rand;
  // });

  // onDeactivated(() => {
  //   console.log("Tab 1 Deactivated");
  // });
  // },
  data() {
    return {
      fotoQuantitats:
        import.meta.env.VITE_NODE_ROUTE +
        "getImatgeEstadistiques/producteCantidad",
      fotoQuantitatsVenudes:
        import.meta.env.VITE_NODE_ROUTE +
        "getImatgeEstadistiques/producteMesVenut",
      fotoHoraComu:
        import.meta.env.VITE_NODE_ROUTE + "getImatgeEstadistiques/HoraMesComu",
      fotoHoraDiners:
        import.meta.env.VITE_NODE_ROUTE +
        "getImatgeEstadistiques/HoraMesDiners",
      fotoDinersComanda:
        import.meta.env.VITE_NODE_ROUTE +
        "getImatgeEstadistiques/DinersComanda",
      dialog1: false,
      dialog2: false,
      dialog3: false,
      dialog4: false,
      dialog5: false,
      tiempoRestante: "00:00:00",
      intervalId: null,
    };
  },
  mounted() {
    var rand = Math.random(100000);
    this.fotoQuantitats =
      import.meta.env.VITE_NODE_ROUTE +
      "getImatgeEstadistiques/producteCantidad?" +
      rand;
    this.fotoQuantitatsVenudes =
      import.meta.env.VITE_NODE_ROUTE +
      "getImatgeEstadistiques/producteMesVenut?" +
      rand;
    this.fotoHoraComu =
      import.meta.env.VITE_NODE_ROUTE +
      "getImatgeEstadistiques/HoraMesComu?" +
      rand;
    this.fotoHoraDiners =
      import.meta.env.VITE_NODE_ROUTE +
      "getImatgeEstadistiques/HoraMesDiners?" +
      rand;
    this.fotoDinersComanda = import.meta.env.VITE_NODE_ROUTE +
      "getImatgeEstadistiques/DinersComanda?" +
      rand;
  },
  methods: {
    executeStatistics() {
      //si peta es per que al server a la funcio callPython() hem de canviar la versio de python (a produccio es python3 i a localhost es python)
      fetch(import.meta.env.VITE_NODE_ROUTE + "executeStatistics")
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          return response.json();
        })
        .then((data) => {
          var rand = Math.random(100000);
          console.log("Response from /api/executeStatistics:", data);

          this.fotoQuantitats =
            import.meta.env.VITE_NODE_ROUTE +
            "getImatgeEstadistiques/producteCantidad?" +
            rand;
          this.fotoQuantitatsVenudes =
            import.meta.env.VITE_NODE_ROUTE +
            "getImatgeEstadistiques/producteMesVenut?" +
            rand;
          this.fotoHoraComu =
            import.meta.env.VITE_NODE_ROUTE +
            "getImatgeEstadistiques/HoraMesComu?" +
            rand;
          this.fotoHoraDiners =
            import.meta.env.VITE_NODE_ROUTE +
            "getImatgeEstadistiques/HoraMesDiners?" +
            rand;
          this.fotoDinersComanda = import.meta.env.VITE_NODE_ROUTE +
            "getImatgeEstadistiques/DinersComanda?" +
            rand;
        })
        .catch((error) => {
          console.error("Error executing statistics:", error);
        });
    },
    calcularTiempoRestante() {
      this.detenerContador(); // Detener el contador anterior si lo hubiera

      this.intervalId = setInterval(() => {
        //var ahora = new Date("December 25, 1995 23:3:0");
        var ahora = new Date();

        var minutosRestantes = 59 - ahora.getMinutes();

        /*if (ahora.getMinutes() === 0 && ahora.getSeconds() === 0) {
          this.executeStatistics();
        }*/

        const segundosRestantes = 59 - ahora.getSeconds();

        this.tiempoRestante = `00:${minutosRestantes}:${segundosRestantes}`;
      }, 1000);
    },
    detenerContador() {
      clearInterval(this.intervalId);
      this.intervalId = null;
    },
  },
  created() {
    this.calcularTiempoRestante();
  },
  beforeDestroy() {
    this.detenerContador();
  },
};
</script>
<style>
.image-card {
  width: auto;
  padding: 0;
}
</style>
<template>
  <v-container class="order-container">
    <v-btn variant="tonal" icon="mdi-arrow-left" class="mt-5" @click="$router.push('/')"></v-btn>
    <h1 class="text-center text-h2 my-10 mb-16 font-weight-bold">
      Estadístiques
    </h1>
    <div>
      <p>Tiempo restante: {{ tiempoRestante }}</p>
    </div>
    <v-container>
      <v-card style="background: linear-gradient(#9094e9, #b0b8f1)" class="text-center" elevation="4" rounded="xl">
        <v-card-title>
          <v-container>
            <v-row>
              <v-col cols="12" sm="4">
                <div class="font-weight-bold text-center text-h4 py-4 text-truncate" style="color: #3d3976">
                  Productes Restants
                </div>
                <v-dialog v-model="dialog1" width="1200">
                  <template v-slot:activator>
                    <v-card class="image-card" style="
                        background: linear-gradient(#b0b8f1, #ced4f7);
                        padding: 0;
                        width: auto;
                      " elevation="10" rounded="xl" @click="dialog1 = true">
                      <v-img height="238" cover :src="this.fotoQuantitats"></v-img>
                      <!-- Puedes agregar un texto o contenido adicional aquí si lo deseas -->
                    </v-card>
                  </template>
                  <v-card rounded="xl">
                    <!-- Agregamos border-radius para hacer las esquinas redondas -->
                    <v-img height="auto" :src="this.fotoQuantitats"></v-img>
                  </v-card>
                </v-dialog>
              </v-col>
              <v-col cols="12" sm="4">
                <div class="font-weight-bold text-center text-h4 py-4 text-truncate" style="color: #3d3976">
                  Unitats Venudes
                </div>
                <v-dialog v-model="dialog2" width="1200">
                  <template v-slot:activator>
                    <v-card class="image-card" style="
                        background: linear-gradient(#b0b8f1, #ced4f7);
                        padding: 0;
                        width: auto;
                      " elevation="10" rounded="xl" @click="dialog2 = true">
                      <v-img cover height="238" :src="this.fotoQuantitatsVenudes"></v-img>
                      <!-- Puedes agregar un texto o contenido adicional aquí si lo deseas -->
                    </v-card>
                  </template>
                  <v-card rounded="xl">
                    <v-img height="auto" :src="this.fotoQuantitatsVenudes"></v-img>
                  </v-card>
                </v-dialog>
              </v-col>
              <v-col cols="12" sm="4">
                <div class="font-weight-bold text-center text-h4 py-4 text-truncate" style="color: #3d3976">
                  Hores Comunes
                </div>
                <v-dialog v-model="dialog3" width="1200">
                  <template v-slot:activator>
                    <v-card class="image-card" style="
                        background: linear-gradient(#b0b8f1, #ced4f7);
                        padding: 0;
                        width: auto;
                      " elevation="10" rounded="xl" @click="dialog3 = true">
                      <v-img height="238" cover :src="this.fotoHoraComu"></v-img>
                      <!-- Puedes agregar un texto o contenido adicional aquí si lo deseas -->
                    </v-card>
                  </template>
                  <v-card rounded="xl">
                    <v-img height="auto" :src="this.fotoHoraComu"></v-img>
                  </v-card>
                </v-dialog>
              </v-col>
              <v-col cols="12" sm="4">
                <div class="font-weight-bold text-center text-h4 py-4 text-truncate" style="color: #3d3976">
                  Hora Mes Diners
                </div>
                <v-dialog v-model="dialog4" width="1200">
                  <template v-slot:activator>
                    <v-card class="image-card" style="
                        background: linear-gradient(#b0b8f1, #ced4f7);
                        padding: 0;
                        width: auto;
                      " elevation="10" rounded="xl" @click="dialog4 = true">
                      <v-img height="238" cover :src="this.fotoHoraDiners"></v-img>
                      <!-- Puedes agregar un texto o contenido adicional aquí si lo deseas -->
                    </v-card>
                  </template>
                  <v-card rounded="xl">
                    <!-- Agregamos border-radius para hacer las esquinas redondas -->
                    <v-img height="auto" :src="this.fotoHoraDiners"></v-img>
                  </v-card>
                </v-dialog>
              </v-col>
              <v-col cols="12" sm="4">
                <div class="font-weight-bold text-center text-h4 py-4 text-truncate" style="color: #3d3976">
                  Diners Comandes
                </div>
                <v-dialog v-model="dialog5" width="1200">
                  <template v-slot:activator>
                    <v-card class="image-card" style="
                        background: linear-gradient(#b0b8f1, #ced4f7);
                        padding: 0;
                        width: auto;
                      " elevation="10" rounded="xl" @click="dialog5 = true">
                      <v-img height="238" cover :src="this.fotoDinersComanda"></v-img>
                      <!-- Puedes agregar un texto o contenido adicional aquí si lo deseas -->
                    </v-card>
                  </template>
                  <v-card rounded="xl">
                    <!-- Agregamos border-radius para hacer las esquinas redondas -->
                    <v-img height="auto" :src="this.fotoDinersComanda"></v-img>
                  </v-card>
                </v-dialog>
              </v-col>
            </v-row>
          </v-container>
          <v-container align="center" justify="center">
            <v-sheet class="ma-2" style="background: transparent">
              <v-btn class="my-4" height="50px" width="170px" rounded style="
                  background: linear-gradient(to left, #e8321a, #ff7a68);
                  color: white;
                " @click="executeStatistics">Actualitza
              </v-btn>
            </v-sheet>
          </v-container>
        </v-card-title>
      </v-card>
    </v-container>
  </v-container>
</template>
