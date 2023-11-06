<script setup></script>

<script>
export default {
  data() {
    return {
      fotoQuantitats:
        "http://localhost:3001/api/getImatgeEstadistiques/producteCantidad",
      fotoQuantitatsVenudes:
        "http://localhost:3001/api/getImatgeEstadistiques/producteMesVenut",
      fotoHoraComu:
        "http://localhost:3001/api/getImatgeEstadistiques/HoraMesComu?",
      dialog1: false,
      dialog2: false,
      dialog3: false,
    };
  },
  methods: {
    executeStatistics() {
      fetch("http://localhost:3001/api/executeStatistics")
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          return response.json();
        })
        .then((data) => {
          // Handle a successful response here
          var rand = Math.random(100000);
          console.log("Response from /api/executeStatistics:", data);

          this.fotoQuantitats =
            "http://localhost:3001/api/getImatgeEstadistiques/producteCantidad?" +
            rand;
          this.fotoQuantitatsVenudes =
            "http://localhost:3001/api/getImatgeEstadistiques/producteMesVenut?" +
            rand;
          this.fotoHoraComu =
            "http://localhost:3001/api/getImatgeEstadistiques/HoraMesComu?" +
            rand;

          // You can update your UI or perform other actions with the response data.
        })
        .catch((error) => {
          // Handle errors here
          console.error("Error executing statistics:", error);
          // You can show an error message or perform error handling.
        });
    },
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
    <v-btn
      variant="tonal"
      icon="mdi-arrow-left"
      class="mt-5"
      @click="$router.push('/')"
    ></v-btn>
    <h1 class="text-center text-h2 my-16 pt-10 font-weight-bold">
      Estadístiques
    </h1>
    <v-container>
      <v-card
        style="background: linear-gradient(#9094e9, #b0b8f1)"
        class="text-center"
        elevation="4"
        rounded="xl"
      >
        <v-card-title>
          <v-container>
            <v-row>
              <v-col cols="12" sm="4">
                <div
                  class="font-weight-bold text-center text-h4 py-4 text-truncate"
                  style="color: #3d3976"
                >
                  Productes Restants
                </div>
                <v-dialog v-model="dialog1" width="1200">
                  <template v-slot:activator="{ props }">
                    <v-card
                      class="image-card"
                      style="
                        background: linear-gradient(#b0b8f1, #ced4f7);
                        padding: 0;
                        width: auto;
                      "
                      elevation="10"
                      rounded="xl"
                      @click="dialog1 = true"
                    >
                      <v-img
                        height="238"
                        cover
                        :src="this.fotoQuantitats"
                      ></v-img>
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
                <div
                  class="font-weight-bold text-center text-h4 py-4 text-truncate"
                  style="color: #3d3976"
                >
                  Unitats Venudes
                </div>
                <v-dialog v-model="dialog2" width="1200">
                  <template v-slot:activator="{ props }">
                    <v-card
                      class="image-card"
                      style="
                        background: linear-gradient(#b0b8f1, #ced4f7);
                        padding: 0;
                        width: auto;
                      "
                      elevation="10"
                      rounded="xl"
                      @click="dialog2 = true"
                    >
                      <v-img
                        cover
                        height="238"
                        :src="this.fotoQuantitatsVenudes"
                      ></v-img>
                      <!-- Puedes agregar un texto o contenido adicional aquí si lo deseas -->
                    </v-card>
                  </template>
                  <v-card rounded="xl">
                    <v-img
                      height="auto"
                      :src="this.fotoQuantitatsVenudes"
                    ></v-img>
                  </v-card>
                </v-dialog>
              </v-col>
              <v-col cols="12" sm="4">
                <div
                  class="font-weight-bold text-center text-h4 py-4 text-truncate"
                  style="color: #3d3976"
                >
                  Hores Comunes
                </div>
                <v-dialog v-model="dialog3" width="1200">
                  <template v-slot:activator="{ props }">
                    <v-card
                      class="image-card"
                      style="
                        background: linear-gradient(#b0b8f1, #ced4f7);
                        padding: 0;
                        width: auto;
                      "
                      elevation="10"
                      rounded="xl"
                      @click="dialog3 = true"
                    >
                      <v-img
                        height="238"
                        cover
                        :src="this.fotoHoraComu"
                      ></v-img>
                      <!-- Puedes agregar un texto o contenido adicional aquí si lo deseas -->
                    </v-card>
                  </template>
                  <v-card rounded="xl">
                    <v-img height="auto" :src="this.fotoHoraComu"></v-img>
                  </v-card>
                </v-dialog>
              </v-col>
            </v-row>
          </v-container>
          <v-container align="center" justify="center">
            <v-sheet class="ma-2" style="background: transparent">
              <v-btn
                class="my-4"
                height="50px"
                width="170px"
                rounded
                style="
                  background: linear-gradient(to left, #e8321a, #ff7a68);
                  color: white;
                "
                @click="executeStatistics"
                >Actualitza
              </v-btn>
            </v-sheet>
          </v-container>
        </v-card-title>
      </v-card>
    </v-container>
  </v-container>
</template>
