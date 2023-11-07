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
    });
  },
  methods: {
    enviarMensaje() {
      this.socket.emit("mensaje", "Hola servidor, soy un cliente");
    },
    canviComanda(index) {
      this.comandaActual = index;
      this.switchsNotSelected = true;
      document.querySelectorAll(".v-list-item").forEach((item) => {
        item.style =
          "background-color: #7875df; width: 100%; height: 100px; border: 2px solid #473f94;";
      });
      document.getElementById("comanda-" + index).style =
        "background-color: #9094e9; width: 100%; height: 100px; border: 4px solid #473f94;";
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
    acabarComanda() {
      this.socket.emit("eliminaComanda", this.comandaActual);
      this.canviComanda(this.comandaActual + 1);
    },
  },
};
</script>

<template>
  <div>
    <v-table
      fixed-header
      height="500px"
      style="background-color: transparent; color: black; font-weight: bold"
    >
      <thead class="text-h4">
        <tr>
          <th class="text-center">Unitats</th>
          <th class="text-center">Producte</th>
          <th class="text-center">Hora de recollida</th>
        </tr>
      </thead>
      <tbody class="text-h6">
        <tr
          align="center"
        >
          <td>
            <div
              class="text-center"
              style="
                background-color: #7472de;
                border: 1px solid #3d3976;
                width: 35px;
                height: 35px;
                border-radius: 20px;
              "
            >
              2
            </div>
          </td>
          <td>awa</td>
          <td>
            13:45
          </td>
        </tr>
      </tbody>
    </v-table>
  </div>
</template>
