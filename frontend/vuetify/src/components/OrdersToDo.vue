<script setup>
import io from "socket.io-client";
</script>

<script>
export default {
  props: ["title", "img", "id"],
  data: () => ({
    socket: null,
    responseData: null,
    comandaActual: null,
    json: {},
  }),
  mounted() {
    this.socket = io("http://localhost:3001"); // Reemplaza con la URL de tu servidor de sockets

    this.socket.on("json", (data) => {
      this.json = data;
    });
  },
  methods: {
    enviarMensaje() {
      this.socket.emit("mensaje", "Hola servidor, soy un cliente");
    },
    canviComanda(index) {
      this.comandaActual = index;
      document.querySelectorAll(".v-list-item").forEach((item) => {
        item.style =
          "background-color: #7875df; width: 100%; height: 100px; border: 2px solid #473f94;";
      });
      document.getElementById("comanda-" + index).style =
        "background-color: #b0b8f1; width: 100%; height: 100px; border: 4px solid #473f94;";
    },
    acabarComanda() {
      this.socket.emit("enviarComanda", {
        comanda: {
          productes: [
            {
              nom: "poma vermella",
              unitats: 1,
            },
            {
              nom: "tronja",
              unitats: 4,
            },
          ],
          hora: "13:50",
        },
      });
    },
  },
};
</script>

<template>
  <div>
    <v-row class="pl-3 pt-2">
      <v-col
        cols="2"
        align="center"
        class="pa-2"
        style="
          background-color: #b0b8f1;
          max-height: 500px;
          max-width: 120px;
          overflow-y: auto;
          border-right: solid 1px rgba(90, 90, 90, 0.466);
        "
      >
        <v-list-item
          :key="comandes"
          v-for="(comandes, index) in this.json.comandes"
          class="mb-2"
          style="
            background-color: #7875df;
            width: 100%;
            height: 100px;
            border: 2px solid #473f94;
          "
          rounded="xl"
          link
          :id="'comanda-' + index"
          @click="canviComanda(index)"
        >
          <v-row align="center">
            <v-col cols="2">
              {{ index + 1 }}
            </v-col>
            <v-divider vertical class="border-opacity-100"></v-divider>
            <v-col>
              <div>
                <p>
                  {{ comandes.comanda.productes.length }}
                  <v-icon size="x-small">mdi-food-drumstick</v-icon>
                </p>
              </div>
              <p>{{ comandes.comanda.hora }}</p>
            </v-col>
          </v-row>
        </v-list-item>
      </v-col>
      <v-col cols="16" class="pa-0">
        <v-container v-if="comandaActual === null" align="center">
          <v-card
            rounded="xl"
            class="pa-5 text-center my-10"
            style="background-color: #ced4f7; width: 400px"
          >
            <h1>Sel·lecciona una comanda</h1>
            <v-icon
              class="mt-5 text-h3"
              style="
                background: linear-gradient(to left, #e8321a, #ff7a68);
                border-radius: 15px;
              "
              >mdi-arrow-left</v-icon
            >
          </v-card>
        </v-container>

        <v-table
          v-if="comandaActual !== null"
          fixed-header
          height="500px"
          style="background-color: transparent; color: black; font-weight: bold"
        >
          <thead class="text-h6">
            <tr>
              <th>Unitats</th>
              <th>Producte</th>
              <th>Estat</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="producte in this.json.comandes[comandaActual].comanda
                .productes"
              :key="producte"
              align="left"
            >
              <td>{{ producte.unitats }}</td>
              <td>{{ producte.nom }}</td>
              <td>
                <v-switch v-model="show"></v-switch>
              </td>
            </tr>
          </tbody>
        </v-table>
        <v-btn
          v-if="comandaActual !== null"
          class="my-4"
          height="50px"
          width="170px"
          rounded
          @click="acabarComanda"
          style="
            background: linear-gradient(to left, #e8321a, #ff7a68);
            color: white;
          "
          >Acabar comanda
        </v-btn>
      </v-col>
    </v-row>
  </div>
</template>
