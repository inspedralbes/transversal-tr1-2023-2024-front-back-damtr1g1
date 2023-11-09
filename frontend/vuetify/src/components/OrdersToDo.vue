<script setup>
import io from "socket.io-client";
</script>

<script>
export default {
  props: ["title", "img", "id"],
  data: () => ({
    jsonComandes: {},
    socket: null,
    switchsNotSelected: true,
    responseData: null,
    comandaActual: null,
    noComandes: false,
  }),
  mounted() {
    this.socket = io(import.meta.env.VITE_SOCKETS, {
      cors: {
        origin: "*",
      },
      path: "/node/",
      transports: ["websocket"],
    });

    this.socket.on("json", (data) => {
      this.jsonComandes = data;
      if (Object.keys(data).length === 0) {
        this.noComandes = true;
      }
    });
  },
  methods: {
    enviarMensaje() {
      this.socket.emit("mensaje", "Hola servidor, soy un cliente");
    },
    canviComanda(index) {
      setTimeout(() => {
        this.comandaActual = index;
        this.switchsNotSelected = true;
        document.querySelectorAll(".v-list-item").forEach((item) => {
          item.style =
            "background-color: #7875df; width: 100%; height: 100px; border: 2px solid #473f94; font-weight: bold;";
        });
        document.getElementById("comanda-" + index).style =
          "background-color: #9094e9; width: 100%; height: 100px; border: 4px solid #473f94; font-weight: bold;";
      }, 100);
    },
    switchSelected(nProductes) {
      setTimeout(() => {
        document.querySelectorAll(".text-success").forEach((item, index) => {
          if (index + 1 == nProductes) {
            this.switchsNotSelected = false;
          } else {
            this.switchsNotSelected = true;
          }
        });
      }, 1);
    },
    async acabarComanda() {
      this.socket.emit("eliminaComanda", this.comandaActual);
      if (this.jsonComandes.comandes.length > this.comandaActual + 1) {
        await this.canviComanda(this.comandaActual);
      } else if (this.jsonComandes.comandes.length > 1) {
        await this.canviComanda(0);
      } else {
        this.comandaActual = null;
        this.noComandes = true;
      }
    },
  },
};
</script>

<template>
  <div v-if="jsonComandes">
    <v-row class="pl-3 pt-2">
      <v-col
        id="no-scroll"
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
        <div
          v-if="noComandes"
          class="mt-16"
          style="font-weight: bold; font-size: large"
        >
          No hi han comandes
        </div>
        <v-list-item
          :key="comandes"
          v-for="(comandes, index) in jsonComandes.comandes"
          class="mb-2 pa-3"
          style="
            background-color: #7875df;
            width: 100%;
            height: 100px;
            border: 2px solid #473f94;
            font-weight: bold;
          "
          rounded="xl"
          link
          :id="'comanda-' + index"
          @click="canviComanda(index)"
        >
          <div>
            <p>
              {{ comandes.comanda.productes.length }}
              <v-icon size="x-small" style="color: #ad4234"
                >mdi-food-drumstick</v-icon
              >
            </p>
          </div>
          <p>{{ comandes.comanda.dia }}</p>
          <p>{{ comandes.comanda.hora }}</p>
        </v-list-item>
      </v-col>
      <v-col cols="16" class="pa-0 pr-3">
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
                color: linear-gradient(to left, #e8321a, #ff7a68);
                border-radius: 15px;
              "
              >mdi-arrow-left</v-icon
            >
          </v-card>
        </v-container>

        <v-table
          v-if="comandaActual !== null"
          fixed-header
          height="430px"
          style="background-color: transparent; color: black; font-weight: bold"
        >
          <thead class="text-h4" style="background-color: #9094e9">
            <tr>
              <th>Unitats</th>
              <th>Producte</th>
              <th>Estat</th>
            </tr>
          </thead>
          <tbody class="text-h6">
            <tr
              v-for="producte in jsonComandes.comandes[comandaActual].comanda
                .productes"
              :key="producte"
              align="left"
            >
              <td class="pl-8">
                <div
                  class="text-center mt-1"
                  style="
                    background-color: #7472de;
                    border: 1px solid #3d3976;
                    width: 35px;
                    height: 35px;
                    border-radius: 20px;
                  "
                >
                  {{ producte.unitats }}
                </div>
              </td>
              <td class="pl-8">{{ producte.nom }}</td>
              <td>
                <v-switch
                  @click="
                    switchSelected(
                      jsonComandes.comandes[comandaActual].comanda.productes
                        .length
                    )
                  "
                  class="mt-5 ml-4"
                  color="success"
                  true-value="true"
                  false-value="false"
                ></v-switch>
              </td>
            </tr>
          </tbody>
        </v-table>
        <v-btn
          v-if="comandaActual !== null"
          class="my-4"
          height="50px"
          width="300px"
          rounded
          :disabled="switchsNotSelected"
          @click="acabarComanda(comandaActual)"
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
